package com.qingclass.transcode.global.util

object CommonUtil {

  def getMapObject(key: String, jsObj: Option[Any]): Map[String, Any] = {
    jsObj match {
      case Some(v) => Map(key -> v)
      case None => Map()
    }
  }

  def getObjectId(map: Map[String, Any], key: String): String = {
    map.get(key).get.asInstanceOf[Map[String, String]].get("$oid").get
  }

  def ObjectId(id: String) = Map("$oid" -> id)

  def getNumberLong(value: Option[Any]) = {
    val map = value.get.asInstanceOf[Map[String, Long]]
    map("$numberLong").toString.toLong
  }

  def getNumberLongIfExist(value: Option[Any]) = {
    value match {
      case Some(v) =>
        v match {
          case map: Map[String, Any] => map("$numberLong").toString.toLong
          case _ => anyToLong(v)
        }
      case None => 0L
    }
  }

  /**
    * 处理account-service返回
    */
  def transMap2Json(resMap: Map[String, Any], key: String, msg: String = "获取数据失败"): (Boolean, Any) = {
    resMap.get("status") match {
      case None => (false, List(msg))
      case Some(status) =>
        status.asInstanceOf[Int] match {
          case 0 =>
            resMap("data") match {
              case p: Map[String, Any] => (true, p.get(key).get)
              case _ => (false, List(msg))
            }
          case _ => (false, List(msg))
        }
    }
  }

  def transData2Json(resMap: Map[String, Any], msg: String = "获取数据失败"): (Boolean, Any) = {
    resMap.get("status") match {
      case None => (false, List(msg))
      case Some(status) =>
        status.asInstanceOf[Int] match {
          case 0 =>
            resMap("data") match {
              case p => (true, p)
              case _ => (false, List(msg))
            }
          case _ => (false, List(msg))
        }
    }
  }

  def trans2Option(param: String): Option[Int] = if (param == null) None else Some(param.toInt)

  def anyToInt(param: Any): Int = {
    param match {
      case s: Int => s
      case s: Double => Math.round(s).toInt
      case s: Long => s.toInt
      case _ => param.toString.toInt
    }
  }

  def anyToLong(param: Any): Long = {
    param match {
      case s: Int => s.toLong
      case s: Double => Math.round(s)
      case s: Long => s
      case _ => param.toString.toLong
    }
  }

}