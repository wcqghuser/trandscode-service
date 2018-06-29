package com.qingclass.transcode.v1.service

import java.io.File
import java.net.URL
import java.util.UUID

import com.qingclass.transcode.global.config.RedisConfig
import com.qingclass.transcode.global.db.RedisCache
import com.qingclass.transcode.global.util.{ImageHandleHelper, SysLogging}
import com.qingclass.transcode.global.util.JsonUtil._
import com.qingclass.transcode.v1.cache.TokenCache
import com.qingclass.transcode.v1.domain.{Animation, Element}
import com.qingclass.transcode.v1.manager.ExercisesManager

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import sys.process._

object TranscodeService extends SysLogging {

  def init(): Unit = {
    for ( _ ← 0 until 3) {
      Future {
        RedisCache.lpop(RedisConfig.TRANS_CODE_KEY) match {
          case Some(voiceRecord) ⇒
            val map = voiceRecord.toJsonMap[Any]
            val id = map("id").toString
            val mediaId = map("media_id").toString
            val appId = map("app_id").toString
            val service = map.getOrElse("service", "exercises").toString
            transcode(id, mediaId, appId, service)
          case None ⇒ debug("has no data")
        }
      }
    }
  }

  def transcode(id: String, mediaId: String, appId: String, service: String): Unit = {
    val sourceName = UUID.randomUUID.toString + ".amr"
    val targetName = UUID.randomUUID.toString + ".mp3"
    info(s"transcode voice file voiceRecoredId=$id & mediaId=$mediaId")
    val source = new File(sourceName)
    val target = new File(targetName)
    val accessToken = TokenCache.getAccessToken(appId).token
    val url = GET_MATERIAL_URL.format(accessToken, mediaId)
    //获取文件并存储
    new URL(url) #> source !!

    //利用ffmpeg对amr文件进行转码
    s"ffmpeg -i $sourceName -ac 2 $targetName" !!

    val targetUrl = OssService.uploadFile(targetName, target)
    source.delete()
    target.delete()

    ExercisesManager.updateVoice(id, targetUrl, service)
  }

  def transVideo(urls: List[String]): Map[String, Animation] = {
    var i = 0
    val eles = urls.map { url ⇒
      if (url.isEmpty) null
      else {
        val randomString = UUID.randomUUID().toString
        val sourceName = s"$randomString.mov"
        val source = new File(sourceName)
        new URL(url) #> source !

        s"ffmpeg -y -v warning -i $sourceName -f image2 -vf fps=fps=10,scale=$BASIC_WIDTH:-1 $randomString%5d.png" !

        //查找目前路径下所有randomString开头的png文件,并进行拼接处理
        val dir = new File(".")
        val pngFiles = dir.listFiles(f ⇒ f.getName.contains(randomString) && f.getName.contains("png"))
        val pngFileNames = pngFiles.map(p ⇒ p.getName)

        val targetName = UUID.randomUUID().toString + ".png"
        val target = new File(targetName)
        ImageHandleHelper.mergeImage(pngFileNames, 1, targetName, target)

        source.delete()
        pngFiles.foreach(_.delete())
        val ele = Element(targetName, url, pngFiles.length, BASIC_WIDTH, BASIC_HEIGHT, 0, i * BASIC_HEIGHT)
        i = i + 1
        ele
      }
    }

    val resultName = UUID.randomUUID().toString + ".png"
    val result = new File(resultName)
    ImageHandleHelper.mergeImage(eles.filter(e ⇒ e!= null).map(_.fileName).toArray, 2, resultName, result)

    val ossUrl = OssService.uploadFile(resultName, result)
    eles.filter(e ⇒ e!= null).foreach(e ⇒ new File(e.fileName).delete())
    result.delete()

    Map("animation" → Animation(1, ossUrl, eles))
  }

  private val BASIC_WIDTH = 360
  private val BASIC_HEIGHT = 360

  private val GET_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%1$s&media_id=%2$s"
}
