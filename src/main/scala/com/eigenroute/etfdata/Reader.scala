package com.eigenroute.etfdata

import java.io.File

import org.joda.time.format.DateTimeFormat

import scala.util.Try

object Reader {

  def readETFData(file:File):Seq[ETFData] = {
    val dateFormatter = DateTimeFormat.forPattern("MMM dd, yyy")
    val rawData = io.Source.fromFile(file).getLines.toList.tail
    val code = file.getName.take(3)
    val brand = file.getName.drop(11).dropRight(4)

    for (line <- rawData) yield {
      // https://stackoverflow.com/questions/13335651/scala-split-string-by-commnas-ignoring-commas-between-quotes
      val cols = line.split(""",(?=([^\"]*\"[^\"]*\")*[^\"]*$)""").map(_.trim)
      ETFData(
        dateFormatter.parseDateTime(cols(0).replaceAllLiterally(""""""", "")),
        code,
        brand,
        convertStringColumnToDouble(cols(1)),
        convertStringColumnToDouble(cols(2)),
        convertStringColumnToDouble(cols(3))
      )
    }
  }.toSeq

  private def convertStringColumnToDouble(cellValue:String):Double = Try(cellValue.toDouble).toOption.getOrElse(0)

}
