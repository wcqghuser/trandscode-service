package com.qingclass.transcode.v1.controller

import com.google.inject.Inject
import com.qingclass.transcode.global.controller.common.ApiResponse
import com.qingclass.transcode.v1.service.TranscodeService
import com.twitter.finagle.http.{Request, RequestProxy}
import com.twitter.finatra.http.Controller

/**
  * Created by wangchaoqun on 18/4/19
  */
class TranscodeController extends Controller {

  options("/:*") { _: Request ⇒
    ApiResponse.success
  }

  prefix("/v1/internal") {
    post("/trans-video") { request: VideoRequest ⇒
      val animation = TranscodeService.transVideo(request.urls)
      ApiResponse.success(animation)
    }
  }

}

case class VideoRequest(
                         urls: List[String],
                         @Inject request: Request
                       ) extends RequestProxy