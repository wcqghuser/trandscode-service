package com.qingclass.transcode.v1.domain

/**
  * Created by wangchaoqun on 18/4/21
  */
case class Animation(version: Int, src: String, meta: List[Element])

case class Element(fileName: String, sourceVideo: String, frames: Int, width: Int, height: Int, positionX: Int, positionY: Int)