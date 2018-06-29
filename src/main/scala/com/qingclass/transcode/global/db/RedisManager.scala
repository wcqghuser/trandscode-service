package com.qingclass.transcode.global.db

import com.qingclass.transcode.global.config.EnvironmentConfig
import com.qingclass.transcode.global.util.SysLogging
import com.redis.{RedisClient, RedisClientPool}

class RedisManager extends SysLogging {

  private val server = EnvironmentConfig.getString("redis.server")
  private val port = EnvironmentConfig.getInt("redis.port")
  private val maxIdle = EnvironmentConfig.getInt("redis.maxIdle")
  val database = EnvironmentConfig.getInt("redis.database")
  private val secret = EnvironmentConfig.getString("redis.secret")
  private val setSecret = if (secret.isEmpty) None else Some(secret)

  val clients: RedisClientPool = new RedisClientPool(server, port, maxIdle, database, setSecret)

  def withRedisClient[T](body: RedisClient => T): T = clients.withClient(body)
}

object RedisManager extends RedisManager