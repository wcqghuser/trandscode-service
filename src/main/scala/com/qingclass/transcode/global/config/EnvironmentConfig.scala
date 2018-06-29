package com.qingclass.transcode.global.config

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object EnvironmentConfig {

  /**
    * 地球开发环境-Local
    */
  val DEVELOPMENT = "development"

  /**
    * 生产环境
    */
  val PRODUCTION = "production"

  var env: String = _

  def setEnvironment(environment: String) = {
    this.env = environment
  }

  def isProMode: Boolean = env == PRODUCTION

  def getString(attr: String) = ConfigFactory.load().getString(env + "." + attr)

  def getInt(attr: String) = ConfigFactory.load().getInt(env + "." + attr)

  def getLong(attr: String): Long = ConfigFactory.load().getLong(env + "." + attr)

  def getBoolean(attr: String) = ConfigFactory.load().getBoolean(env + "." + attr)

  def getStringList(attr: String) = ConfigFactory.load().getStringList(env + "." + attr).asScala.toList

}