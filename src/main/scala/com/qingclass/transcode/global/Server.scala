package com.qingclass.transcode.global

import com.qingclass.transcode.global.config.{EnvironmentConfig, ServerConfig}
import com.qingclass.transcode.global.controller.InternalController
import com.qingclass.transcode.global.exception.{Http4xxExceptionMapper, MalformedAPIExceptionMapper, ServiceExceptionMapper}
import com.qingclass.transcode.global.filter.CorsFilter
import com.qingclass.transcode.v1.controller.TranscodeController
import com.qingclass.transcode.v1.work.TranscodeJob
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

class Server extends HttpServer {

  private val environment = System.getenv("RUNNING_MODE") match {
    case EnvironmentConfig.PRODUCTION => EnvironmentConfig.PRODUCTION
    case _ => EnvironmentConfig.DEVELOPMENT
  }

  EnvironmentConfig.setEnvironment(environment)

  System.setProperty("scala.concurrent.context.minThreads", 2048.toString)
  System.setProperty("scala.concurrent.context.maxThreads", 4096.toString)

  override protected def defaultFinatraHttpPort: String = ":" + ServerConfig.SERVER_PORT
  override def defaultAdminPort: Int = ServerConfig.SERVER_PROT_ADMIN
  override protected def disableAdminHttpServer: Boolean = true

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[CorsFilter[Request]]
      .add[InternalController]
      .add[TranscodeController]
      .exceptionMapper[MalformedAPIExceptionMapper]
      .exceptionMapper[Http4xxExceptionMapper]
      .exceptionMapper[ServiceExceptionMapper]
  }
  //初始化处理跟读数据
  TranscodeJob.init()
}

object MainServer extends Server
