package com.qingclass.transcode.global.util

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

package object JsonUtil {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

  implicit class MapToJsonHelper(val value: Map[_, _]) {
    def toJsonString = mapper.writeValueAsString(value map { case (k,v) => k -> v})
  }

  implicit class JsonToMap(json: String) {
    def toJsonMap[V](implicit m: Manifest[V]) = fromJson[Map[String,V]](json)
    def toJsonObject[T](implicit m : Manifest[T]): T = mapper.readValue[T](json)
  }

  implicit class JsonObjectMapper(value: Any) {
    def toJsonObjectString = mapper.writeValueAsString(value)
  }

  private def fromJson[T](json: String)(implicit m : Manifest[T]): T = mapper.readValue[T](json)

}
