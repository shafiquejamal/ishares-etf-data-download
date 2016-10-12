package com.eigenroute.etfdata

import java.io.File

import org.joda.time.DateTime
import org.scalatest.{FlatSpecLike, ShouldMatchers}

class ISharesReaderUTest extends FlatSpecLike with ShouldMatchers {

  "the reader" should "convert the historical nav data to a collection of eft data - 4 columns" in {

    val data =
      ISharesReader.readETFData(
      new File("src/test/scala/com/eigenroute/etfdata/XEM_239636_ishares-msci-emerging-markets-index-etf.csv"))

    data.length shouldEqual 1810
    data.head shouldEqual ETFData(new DateTime(2016, 8, 30, 0, 0, 0), "XEM",
                                  "ishares-msci-emerging-markets-index-etf", "239636", 166.99041, 27.248022, 0)
    data(52) shouldEqual ETFData(new DateTime(2016, 6, 15, 0, 0, 0), "XEM",
                                 "ishares-msci-emerging-markets-index-etf", "239636", 146.693515, 24.077652, 0.06536)
    data.last shouldEqual ETFData(new DateTime(2009, 6, 18, 0, 0, 0), "XEM",
                                  "ishares-msci-emerging-markets-index-etf", "239636", 100d, 20, 0)
  }


  it should "convert the historical nav data to a collection of eft data - 3 columns" in {

    val data =
      ISharesReader.readETFData(
        new File("src/test/scala/com/eigenroute/etfdata/CHB_239441_ishares-advantaged-us-high-yield-bond-index-etf-cadhedged-fund.csv"))

    data.length shouldEqual 1667
    data.head shouldEqual ETFData(new DateTime(2016, 8, 30, 0, 0, 0), "CHB",
                                  "ishares-advantaged-us-high-yield-bond-index-etf-cadhedged-fund", "239441", 0d, 19.150501, 0d)
    data(4) shouldEqual ETFData(new DateTime(2016, 8, 24, 0, 0, 0), "CHB",
                                 "ishares-advantaged-us-high-yield-bond-index-etf-cadhedged-fund", "239441", 0d, 19.128415, 0.09298)
    data.last shouldEqual ETFData(new DateTime(2010, 1, 11, 0, 0, 0), "CHB",
                                  "ishares-advantaged-us-high-yield-bond-index-etf-cadhedged-fund", "239441", 0d, 19.908334, 0)

  }
}
