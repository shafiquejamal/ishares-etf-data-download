package com.eigenroute.etfdata

import java.io.File

import org.joda.time.format.DateTimeFormat

import scala.util.Try

object ISharesReader extends FileReader {

  override def readETFData(file: File): Seq[ETFData] = {
    val dateFormatter = DateTimeFormat.forPattern("MMM dd, yyy")
    val allRawData = io.Source.fromFile(file).getLines.toList
    val rawData = allRawData.tail
    val header = allRawData.head
    val code = file.getName.take(3)
    val brand = file.getName.drop(11).dropRight(4)
    val xnumber = file.getName.slice(4,10)

    val numberOfHeadings = header.split(""",(?=([^\"]*\"[^\"]*\")*[^\"]*$)""").length

    for (line <- rawData) yield {
      // https://stackoverflow.com/questions/13335651/scala-split-string-by-commnas-ignoring-commas-between-quotes
      val cols = line.split(""",(?=([^\"]*\"[^\"]*\")*[^\"]*$)""").map(_.trim)
      val unparsedDate =  cols(0)

      val (indexReturn:Double, nav:Double, exDividend:Double) = if (numberOfHeadings == 3) {
        (0d, convertStringColumnToDouble(cols(1)), convertStringColumnToDouble(cols(2)))
      } else {
        (convertStringColumnToDouble(cols(1)), convertStringColumnToDouble(cols(2)), convertStringColumnToDouble(cols(3)))
      }

      ETFData(
        dateFormatter.parseDateTime(unparsedDate.replaceAllLiterally(""""""", "")),
        code,
        brand,
        xnumber,
        indexReturn,
        nav,
        exDividend
      )
    }
  }.toSeq

  private def convertStringColumnToDouble(cellValue:String):Double = Try(cellValue.toDouble).toOption.getOrElse(0)

}
