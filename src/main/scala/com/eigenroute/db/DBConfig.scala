package com.eigenroute.db

import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

trait DBConfig {

  def setUpAllDB(): Unit

  def closeAll(): Unit

  def dBName:String
  def driver:String
  def url:String
  def username:String
  def password:String

  protected def setUp(prefix:String, configParams:Map[String, String]) = {
    val cpSettings = ConnectionPoolSettings(
      initialSize = configParams.get(s"$prefix.poolInitialSize").map(_.toInt).getOrElse(10),
      maxSize = configParams.get(s"$prefix.poolMaxSize").map(_.toInt).getOrElse(20),
      connectionTimeoutMillis = configParams.get(s"$prefix.poolMaxSize").map(_.toLong).getOrElse(1000),
      validationQuery = configParams.getOrElse(s"$prefix.poolValidationQuery", "select 1 as one"),
      connectionPoolFactoryName = configParams.getOrElse(s"$prefix.connectionPoolFactoryName", ""),
      driverName = driver,
      warmUpTime = configParams.get(s"$prefix.poolWarmUpTimeMillis").map(_.toLong).getOrElse(1000),
      timeZone = configParams.get(s"$prefix.timeZone").orNull[String]
    )

    Class.forName(driver)
    ConnectionPool.add(Symbol(dBName), url, username, password, cpSettings)
  }

}