package com.qingclass.transcode.v1.work

import java.util.{Timer, TimerTask}

import com.qingclass.transcode.global.util.SysLogging
import com.qingclass.transcode.v1.service.TranscodeService

object TranscodeJob extends SysLogging {

  def init(): Unit = {
    try {
      val timer = new Timer()
      val activateMongoTask = new TimerTask {
        override def run(): Unit = {
          TranscodeService.init()
        }
      }
      val delay = 1 * 1000
      val period = 1 * 1000
      info(s"transcode job begin, delay=$delay millisec & period=$period millisec")
      timer.scheduleAtFixedRate(activateMongoTask, delay, period)
    } catch {
      case e: Exception => error("transcode job fail", e)
    }
  }

}
