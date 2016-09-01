package com.eigenroute.db

import com.typesafe.config.ConfigFactory

class DevProdDBConfig extends DBConfig {

  val conf = ConfigFactory.load

  override val driver = conf.getString("db.default.driver")
  override val url = conf.getString("db.default.url")
  override val dBName = url.substring(url.lastIndexOf("/") + 1)
  override val username = conf.getString("db.default.username")
  override val password = conf.getString("db.default.password")

  val configParams = Map[String, String]()

  override def setUpAllDB(): Unit = setUp("db.default", configParams)

  override def closeAll(): Unit = {}

}
