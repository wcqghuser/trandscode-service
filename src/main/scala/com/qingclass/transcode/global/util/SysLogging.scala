package com.qingclass.transcode.global.util

import com.qingclass.transcode.global.config.ServerConfig
import com.twitter.inject.Logging
import org.joda.time.DateTime

trait SysLogging extends Logging {

  def debug(action: String, msg: String): Unit = super.debug(s"{ID=${IdWorker.nextId()}, DataTime=${DateTime.now.toString(DateUtil.FMT_MILLISECOND)}, ServiceName=${ServerConfig.SERVICE_NAME}, Action=$action, Msg=$msg}")

  def info(action: String, msg: String): Unit = super.info(s"{ID=${IdWorker.nextId()}, DataTime=${DateTime.now.toString(DateUtil.FMT_MILLISECOND)}, ServiceName=${ServerConfig.SERVICE_NAME}, Action=$action, Msg=$msg}")

  def warn(action: String, msg: String): Unit = super.warn(s"{ID=${IdWorker.nextId()}, DataTime=${DateTime.now.toString(DateUtil.FMT_MILLISECOND)}, ServiceName=${ServerConfig.SERVICE_NAME}, Action=$action, Msg=$msg}")

  def error(action: String, msg: String): Unit = super.error(s"{ID=${IdWorker.nextId()}, DataTime=${DateTime.now.toString(DateUtil.FMT_MILLISECOND)}, ServiceName=${ServerConfig.SERVICE_NAME}, Action=$action, Msg=$msg}")

  override protected def debug(msg: => Any): Unit = this.debug("NotDefined", msg.toString)

  override protected def info(msg: => Any): Unit = this.info("NotDefined", msg.toString)

  override protected def warn(msg: => Any): Unit = this.warn("NotDefined", msg.toString)

  override protected def error(msg: => Any): Unit = this.error("NotDefined", msg.toString)
}