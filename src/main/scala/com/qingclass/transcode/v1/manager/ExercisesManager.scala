package com.qingclass.transcode.v1.manager

import com.qingclass.transcode.global.util.JsonUtil._
import com.qingclass.transcode.global.util.{RestClient, SysLogging}

/**
  * Created by wangchaoqun on 18/4/19
  */
object ExercisesManager extends SysLogging {

  def updateVoice(id: String, url: String, service: String) = {
    val body = Map("id" → id, "url" → url).toJsonObjectString
    val header = Some(Seq(("Content-Type", "application/json;charset=UTF-8")))
    val result = RestClient.postAndOutResponse(updateVoiceUrl.format(service), body, header)
    if (result.isError) error(s"update voice error url=$updateVoiceUrl & response=${result.body.toString}")
  }

  private val updateVoiceUrl = "http://service/saas/%1$s/v1/internal/update/voice"
}
