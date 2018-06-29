package com.qingclass.transcode.global.exception

import com.google.inject.{Inject, Singleton}
import com.qingclass.transcode.global.config.ServerConfig
import com.qingclass.transcode.global.controller.common.ApiResponse
import com.qingclass.transcode.global.util.SysLogging
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

@Singleton
class MalformedAPIExceptionMapper @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[MalformedAPIException] with SysLogging {

  override def toResponse(request: Request, exception: MalformedAPIException): Response = {
    error("MalformedAPIException", exception.getMessage)
    response.internalServerError(ApiResponse.http5xx(ServerConfig.SERVICE_NAME.concat(s"(${exception.getMessage})")))
  }
}

class MalformedAPIException(msg: String="malformed api parameter") extends Exception(msg)