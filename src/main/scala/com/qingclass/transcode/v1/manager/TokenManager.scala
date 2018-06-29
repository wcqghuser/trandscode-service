package com.qingclass.transcode.v1.manager

import com.qingclass.transcode.global.util.{CommonUtil, RestClient}
import com.qingclass.transcode.v1.domain.AccessToken

object TokenManager {

  def getAccessToken(appId: String) = {
    val url = tokenServerBaseURL.format(appId)
    val response = RestClient.getAny(url)
    val data = CommonUtil.transData2Json(response)._2.asInstanceOf[Map[String, Any]]
    val map = data.getOrElse("access_token", Map()).asInstanceOf[Map[String, Any]]
    AccessToken(
      map.getOrElse("token", "").toString,
      CommonUtil.anyToLong(map.getOrElse("ttl", 0))
    )
  }

  val tokenServerBaseURL = "http://service/saas/token/v1/access-token?appId=%1$s"
}
