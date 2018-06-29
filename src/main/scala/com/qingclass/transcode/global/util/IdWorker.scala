package com.qingclass.transcode.global.util

import com.qingclass.transcode.global.config.ServerConfig
import com.twitter.inject.Logging
import org.joda.time.DateTime

object IdWorker extends Logging {

  private val twepoch = 1483200000000L

  private val workerId = 0

  private val dataCenterId = 0

  private var sequence = 0L
  //机器标识位数
  private[this] val workerIdBits = 5L
  //数据中心标识位
  private[this] val dataCenterIdBits = 5L
  //机器ID最大值
//  private[this] val maxWorkerId = -1L ^ (-1L << workerIdBits)
  //数据中心ID最大值
//  private[this] val maxDataCenterId = -1L ^ (-1L << dataCenterIdBits)
  //毫秒内自增位
  private[this] val sequenceBits = 12L
  //机器ID偏左移12位
  private[this] val workerIdShift = sequenceBits
  //数据中心ID左移17位
  private[this] val dataCenterIdShift = sequenceBits + workerIdBits
  //时间毫秒左移22位
  private[this] val timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits
  //4095
  private[this] val sequenceMask = -1L ^ (-1L << sequenceBits)

  private[this] var lastTimestamp = -1L

  // sanity check for workerId
//  if (workerId > maxWorkerId || workerId < 0) {
//    throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0".format(maxWorkerId))
//  }

//  if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
//    throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0".format(maxDataCenterId))
//  }

  private val msg = s"worker starting. timestamp left shift $timestampLeftShift, dataCenter id bits $dataCenterIdBits, worker id bits $workerIdBits, sequence bits $sequenceBits, workerId $workerId"

  info(s"{ID=000000000000000000, DataTime=${DateTime.now.toString(DateUtil.FMT_MILLISECOND)}, ServiceName=${ServerConfig.SERVICE_NAME}, Action=IdWorker, Msg=$msg")


  def nextId(): Long = synchronized {
    var timestamp = timeGen()

    //时间错误
    if (timestamp < lastTimestamp) {
//      error("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
      throw new RuntimeException("Clock moved backwards.  Refusing to generate id for %d milliseconds".format(
        lastTimestamp - timestamp))
    }

    if (lastTimestamp == timestamp) {
      //当前毫秒内，则+1
      sequence = (sequence + 1) & sequenceMask
      if (sequence == 0) {
        //当前毫秒内计数满了，则等待下一秒
        timestamp = tilNextMillis(lastTimestamp)
      }
    } else {
      //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
      sequence = 0
    }

    lastTimestamp = timestamp
    //ID偏移组合生成最终的ID，并返回ID
    // 000000000000000000000000000000000000000000  00000            00000       000000000000
    // time                                        datacenterId   workerId    sequence
    ((timestamp - twepoch) << timestampLeftShift) |
      (dataCenterId << dataCenterIdShift) |
      (workerId << workerIdShift) |
      sequence
  }

  //等待下一个毫秒的到来
  protected def tilNextMillis(lastTimestamp: Long): Long = {
    var timestamp = timeGen()
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen()
    }
    timestamp
  }

  protected def timeGen(): Long = System.currentTimeMillis()
}