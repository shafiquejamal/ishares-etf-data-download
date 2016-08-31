package com.eigenroute.db

import com.typesafe.config.ConfigFactory

class ScalikeJDBCTestDBConfig extends DBConfig {

  val conf = ConfigFactory.load

  override val driver = conf.getString("db.test.driver")
  override val url = conf.getString("db.test.url")
  override val dBName = url.substring(url.lastIndexOf("/") + 1)
  override val username = conf.getString("db.test.username")
  override val password = conf.getString("db.test.password")

  val configParams = Map[String, String]()

  override def setUpAllDB(): Unit = setUp("db.test", configParams)

  override def closeAll(): Unit = {}

}