package com.eigenroute.etfdata

import java.io.File

import org.joda.time.DateTime
import org.scalatest.{FlatSpecLike, ShouldMatchers}

class VanguardReaderUTest extends FlatSpecLike with ShouldMatchers {

  "The reader" should "convert the raw data into a collection of ETF data items" in {

    val expected: Seq[ETFData] =
      Seq(
        ETFData(new DateTime(2016, 10, 11, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.5252, 0),
        ETFData(new DateTime(2016, 10, 7, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.5462, 0),
        ETFData(new DateTime(2016, 10, 6, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.6137, 0),
        ETFData(new DateTime(2016, 10, 5, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.6396, 0),
        ETFData(new DateTime(2016, 10, 4, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.4360, 0),
        ETFData(new DateTime(2016, 10, 3, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.6722, 0),
        ETFData(new DateTime(2016, 9, 30, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.7421, 0),
        ETFData(new DateTime(2016, 9, 29, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.7629, 0),
        ETFData(new DateTime(2016, 9, 28, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.7169, 0),
        ETFData(new DateTime(2016, 9, 27, 0, 0, 0), "VCE", "FTSE+Canada+Index+ETF", "9554", 0d, 30.3462, 0)
      )

    val eTFData =
      VanguardReader
      .readETFData(new File("src/test/scala/com/eigenroute/etfdata/vanguard_VCE_9554_FTSE+Canada+Index+ETF.csv"))

    eTFData should contain theSameElementsAs expected

  }

}
