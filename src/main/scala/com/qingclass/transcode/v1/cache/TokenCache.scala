package com.qingclass.transcode.v1.cache

import com.qingclass.transcode.global.config.{CacheName, QingClassCache}
import com.qingclass.transcode.v1.domain.AccessToken
import com.qingclass.transcode.v1.manager.TokenManager

import scala.concurrent.duration._
import scala.language.postfixOps
import scalacache._

object TokenCache extends QingClassCache {

  def getAccessToken(appId: String) = {
    val key = CacheName.ACCESS_TOKEN.format(appId)
    val accessToken = sync.get(key).asInstanceOf[Option[AccessToken]]
    accessToken match {
      case Some(token) => token
      case _ => loadAccessToken(appId)
    }
  }

  def loadAccessToken(appId: String) = {
    val key = CacheName.ACCESS_TOKEN.format(appId)
    val accessToken = TokenManager.getAccessToken(appId)
    if (accessToken.ttl > minExpire) put(key)(accessToken, Some(5 minutes))
    else put(key)(accessToken, Some(accessToken.ttl seconds))
    accessToken
  }

  private val minExpire = 300

}

