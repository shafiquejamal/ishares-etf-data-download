package com.eigenroute.etfdata

import org.joda.time.DateTime

case class ETFData(date:DateTime, indexReturn:Double, nAVPerShare:Double, exDividends:Double) {
  require(nAVPerShare >= 0)
  require(exDividends >= 0)
  require(Option(date).isDefined)
}
