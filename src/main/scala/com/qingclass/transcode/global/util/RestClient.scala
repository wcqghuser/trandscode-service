package com.qingclass.transcode.global.util

import com.qingclass.transcode.global.config.ServerConfig

import scalaj.http._
import com.qingclass.transcode.global.util.JsonUtil._

object RestClient {
  def get(url: String): Map[String, String] = getAndOutResponse(url).body.toJsonMap[String]

  def getAny(url: String): Map[String, Any] = getAndOutResponse(url).body.toJsonMap[Any]

  def getAndOutResponse(url: String): HttpResponse[String] = Http(url).options(HttpOptions.connTimeout(ServerConfig.SERVICE_CONNECT_TIMEOUT), HttpOptions.readTimeout(ServerConfig.SERVICE_SO_TIMEOUT)).asString

  def post(url: String, body: String, headers: Option[Seq[(String, String)]] = None): Map[String, String] = postAndOutResponse(url, body, headers).body.toJsonMap[String]

  def postAny(url: String, body: String, headers: Option[Seq[(String, String)]] = None): Map[String, Any] = postAndOutResponse(url, body, headers).body.toJsonMap[Any]

  def postAndOutResponse(url: String, body: String, headers: Option[Seq[(String, String)]] = None): HttpResponse[String] = {
    val httpRequest: HttpRequest = Http(url).postData(body).options(HttpOptions.connTimeout(ServerConfig.SERVICE_CONNECT_TIMEOUT), HttpOptions.readTimeout(ServerConfig.SERVICE_SO_TIMEOUT))
    headers match {
      case Some(headerSeq) => httpRequest.headers(headerSeq).asString
      case _ => httpRequest.asString
    }
  }
}