package com.eigenroute.etfdata

import java.io.File

import org.joda.time.DateTime
import org.scalatest.{ShouldMatchers, FlatSpec}

class ReaderTest extends FlatSpec with ShouldMatchers {

  "the reader" should "convert the historical nav data to a collection of eft data" in {

    val data =
      Reader.readETFData(
      new File("src/test/scala/com/eigenroute/etfdata/XEM_239636_ishares-msci-emerging-markets-index-etf.csv"))

    data.length shouldEqual 1810
    data.head shouldEqual ETFData(new DateTime(2016, 8, 30, 0, 0, 0), "XEM",
                                  "ishares-msci-emerging-markets-index-etf", 166.99041, 27.248022, 0)
    data(52) shouldEqual ETFData(new DateTime(2016, 6, 15, 0, 0, 0), "XEM",
                                 "ishares-msci-emerging-markets-index-etf", 146.693515, 24.077652, 0.06536)
    data.last shouldEqual ETFData(new DateTime(2009, 6, 18, 0, 0, 0), "XEM",
                                  "ishares-msci-emerging-markets-index-etf", 100d, 20, 0)
  }

}
