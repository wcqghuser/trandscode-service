package com.qingclass.transcode.global.controller

import com.google.inject.Inject
import com.qingclass.transcode.global.controller.common.ApiResponse
import com.qingclass.transcode.global.service.InternalService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class InternalController @Inject()(internalService: InternalService) extends Controller {

  get("/internal/health") { request: Request =>
    val result = internalService.internalHealth
    ApiResponse.success(result)
  }

}
