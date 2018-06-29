package com.qingclass.transcode.global.service.Common

/**
  * DTO基类
  * @param status 0-正常 非0-其他
  * @param message status非0时，message非空有后续操作
  * @param data status为0时，可能含数据，无数据显示null
  */
case class BaseDTO(status: Int, message: String, data: Any = None)