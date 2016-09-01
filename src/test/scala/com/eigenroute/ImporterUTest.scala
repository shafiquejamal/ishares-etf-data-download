package com.eigenroute

import com.eigenroute.etfdata.{ETFData, ETFDAO, ETFDataFixture}
import com.eigenroute.util.{TestUUIDProviderImpl, TestTimeProviderImpl}
import org.scalatest.{FlatSpec, ShouldMatchers}
import org.scalamock.scalatest.MockFactory

import scala.util.Success

class ImporterUTest extends FlatSpec with ShouldMatchers with ETFDataFixture with MockFactory {

  val eTFDataLatest = ETFData(dateLater.plusDays(1), "XEM", "iSharesblahblahblah", "246800", 200d, 50d, 0d)
  val securitiesData = Seq(eTFDataEarlier, eTFDataLater, eTFDataLatest)

  "Importing the securities data" should "import only new data" in {
    class MockableETFDAO extends ETFDAO(new TestTimeProviderImpl, new TestUUIDProviderImpl, null)
    val eTFDAO = mock[MockableETFDAO]
    (eTFDAO.latestBy _).expects("XEM", "246800").returning(Some(eTFDataLater))
    (eTFDAO.save _).expects(eTFDataLatest).returning(Success(eTFDataLatest))
    Importer.importData(securitiesData, eTFDAO)
  }

}
