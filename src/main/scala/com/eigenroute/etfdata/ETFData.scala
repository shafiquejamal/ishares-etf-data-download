package com.eigenroute.etfdata

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

case class ETFData(asOfDate:DateTime, code:String, brand:String, xnumber:String, indexReturn:Double, nAV:Double, exDividends:Double) {
  require(nAV >= 0)
  require(exDividends >= 0)
  require(Option(asOfDate).isDefined)
  require(code.length == 3)
}

object ETFData {
  def converter(rs:WrappedResultSet):ETFData = ETFData(
    rs.jodaDateTime("asofdate"), rs.string("code"), rs.string("brand"), rs.string("xnumber"), rs.double("indexreturn"), rs.double("nav"),
    rs.double("exdividend")
  )
}