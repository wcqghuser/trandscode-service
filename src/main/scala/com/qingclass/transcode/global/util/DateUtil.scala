package com.qingclass.transcode.global.util

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

object DateUtil {

  val FMT_MILLISECOND: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS")

  val FMT_SECOND: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  val FMT_MINUTE: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

  val FMT_DATE: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
}
