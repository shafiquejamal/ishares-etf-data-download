package com.eigenroute.etfdata

import java.io.File

import scala.util.Try

trait FileReader {

  def readETFData(file: File): Seq[ETFData]

  def convertStringColumnToDouble(cellValue:String):Double = Try(cellValue.toDouble).toOption.getOrElse(0)

}
