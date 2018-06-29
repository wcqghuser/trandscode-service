package com.qingclass.transcode.global.db

object RedisCache {

  def set(key: String, value: String) = {
    RedisManager.withRedisClient(client => {
      client.set(key, value)
    })
  }

  def set(key: String, value: String, expire: Int) = {
    RedisManager.withRedisClient(client => {
      client.set(key, value)
      client.expire(key, expire)
    })
  }

  def llen(key: String) = {
    RedisManager.withRedisClient(client ⇒
      client.llen(key)
    )
  }

  def lpop(key: String) = {
    RedisManager.withRedisClient(client ⇒
      client.lpop(key)
    )
  }

  def lpush(key: String, values: List[Any]) = {
    RedisManager.withRedisClient(client => {
      values.foreach(client.lpush(key, _))
    })
  }

  def rpush(key: String, value: Any) = {
    RedisManager.withRedisClient( client ⇒
      client.rpush(key, value)
    )
  }

  def lgetall(key: String): Option[List[Any]] = {
    RedisManager.withRedisClient(client => {
      val result = client.llen(key)
      var length = 0
      if (!result.isEmpty) {
        length = result.get.toInt
      }
      client.lrange(key, 0, length)
    })
  }

  def hset(key: String, values: Map[String, Any]) = {
    RedisManager.withRedisClient(client => {
      values.foreach(o => {
        client.hset(key, o._1, o._2)
      })
    })
  }

  def hset(key: String, values: Map[String, Any], expire: Int) = {
    RedisManager.withRedisClient(client => {
      values.foreach(o => {
        client.hset(key, o._1, o._2)
      })
      client.expire(key, expire)
    })
  }

  def hget(key: String, field: String): Option[Any] = {
    RedisManager.withRedisClient(client => {
      client.hget(key, field)
    })
  }

  def hgetall(key: String): Option[Map[String, Any]] = {
    RedisManager.withRedisClient(client => {
      client.hgetall1(key)
    })
  }

  def get(key: String): Option[String] = {
    RedisManager.withRedisClient(client => client.get(key))
  }

  def ttl(key: String): Long = {
    RedisManager.withRedisClient(client => client.ttl(key).getOrElse(-1L))
  }

  def del(key: String) = {
    RedisManager.withRedisClient(client => client.del(key))
  }

  def expire(key: String, expire: Int) = {
    RedisManager.withRedisClient(client => client.expire(key, expire))
  }

  def exists(key: String) = {
    RedisManager.withRedisClient(client => client.exists(key))
  }

  def setnx(key: String): Boolean = {
    RedisManager.withRedisClient(client => {
      client.setnx(key, true)
    })
  }

  def getset(key: String): Option[String] = {
    RedisManager.withRedisClient(client => {
      client.getset(key, true)
    })
  }

}
