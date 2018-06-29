package com.qingclass.transcode.global.filter

import com.twitter.finagle.http.{HeaderMap, Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

class CorsFilter[R <: Request] extends SimpleFilter[Request, Response]{
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {

    service.apply(request).onSuccess(resp => addCORSHeaders(resp.headerMap))
  }
  private def addCORSHeaders(headerMap: HeaderMap): HeaderMap = {
    headerMap.add("Access-Control-Allow-Origin","*")
      .add("Access-Control-Allow-Methods","GET, POST, OPTIONS")
      .add("Access-Control-Max-Age","86400")
      .add("Access-Control-Allow-Headers","Content-Type, Accept, X-Session-Id, x-version-key")
      .add("Access-Control-Allow-Credentials","true")
  }
}