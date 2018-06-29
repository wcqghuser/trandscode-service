package com.qingclass.transcode.global.resource

object StatusCodeEnum extends Enumeration {

  type StatusCodeEnum = Value

  val SUCCESS: Value = Value(0, "success")

  val HTTP_4XX: Value = Value(400, "")

  val HTTP_5XX: Value = Value(500, "")

}
