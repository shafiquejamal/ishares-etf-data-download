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
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    dbConfig.closeAll()
    super.afterEach()
  }

  val eTFDAO = new ETFDAO(new TestTimeProviderImpl, new TestUUIDProviderImpl, dbConfig)

  "adding etf data" should "succeed if the data is not already in the db" in {
    val eTFData = ETFData(new DateTime(2016, 10, 2, 0, 0, 0), "XIU", "iSharesblahblahblah", 100d, 54d, 12d)
    eTFDAO.save(eTFData).success.value shouldEqual eTFData
  }

  it should "fail if the data is already in the db" in {
    val eTFData = ETFData(date1, "XEM", "iSharesblahblahblah", 110d, 25d, 7d)
    eTFDAO.save(eTFData).failure.exception shouldBe a[RuntimeException]
  }

}
