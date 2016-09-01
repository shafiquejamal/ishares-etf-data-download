package com.eigenroute.etfdata

import com.eigenroute.db.{ScalikeJDBCTestDBConfig, InitialMigration}
import com.eigenroute.util.{TestUUIDProviderImpl, TestTimeProviderImpl}
import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import scalikejdbc.NamedAutoSession
import org.scalatest.TryValues._

class ETFDAOUTest
  extends FlatSpec
  with ShouldMatchers
  with ETFDataFixture
  with InitialMigration
  with BeforeAndAfterEach {

  val dbConfig = new ScalikeJDBCTestDBConfig()

  override def beforeEach(): Unit = {
    implicit val session = NamedAutoSession(Symbol(dbConfig.dBName))
    dbConfig.setUpAllDB()
    migrate(dbConfig)
    eTFDataToAdd.foreach(_.update().apply())
    session.close()
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    dbConfig.closeAll()
    super.afterEach()
  }

  val eTFDAO = new ETFDAO(new TestTimeProviderImpl, new TestUUIDProviderImpl, dbConfig)

  "adding etf data" should "succeed if the data is not already in the db" in {
    val eTFData = ETFData(new DateTime(2016, 10, 2, 0, 0, 0), "XIU", "iSharesblahblahblah", "123456", 100d, 54d, 12d)
    eTFDAO.save(eTFData).success.value shouldEqual eTFData
    eTFDAO.by(eTFData) should contain(eTFData)
  }

  it should "fail if the data is already in the db" in {
    eTFDAO.save(eTFDataEarlier).failure.exception shouldBe a[RuntimeException]
  }

  "fetching by ETFData" should "succeed when the data matches what is in the db" in {
    eTFDAO.by(eTFDataEarlier) should contain(eTFDataEarlier)
  }

  it should "fail when the data does not match" in {
    eTFDAO.by(ETFData(date1, "XEP", "iSharesblahblahblah", "246800", 110d, 25d, 7d)) shouldBe empty
    eTFDAO.by(ETFData(date1, "XEM", "iSharesblahblahblah", "246802", 110d, 25d, 7d)) shouldBe empty
    eTFDAO.by(ETFData(date1, "XEM", "iSharesblahblahblah", "246800", 115d, 25d, 7d)) shouldBe empty
    eTFDAO.by(ETFData(date1, "XEM", "iSharesblahblahblah", "246800", 110d, 5d, 7d)) shouldBe empty
    eTFDAO.by(ETFData(date1, "XEM", "iSharesblahblahblah", "246800", 110d, 25d, 8d)) shouldBe empty
  }

  "fetching the lastest ETFData for a security by code and name" should "yield the latest (by as of date) data for " +
  "that security" in {
    eTFDAO.latestBy("XEM", "246800") should contain(eTFDataLater)
  }

  it should "return nothing if there is no matching security in the database" in {
    eTFDAO.latestBy("XEM", "246801") shouldBe empty
    eTFDAO.latestBy("XEP", "246800") shouldBe empty
  }

}
