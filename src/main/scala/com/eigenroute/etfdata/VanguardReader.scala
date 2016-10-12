package com.eigenroute.etfdata

import java.io.File

import org.joda.time.format.DateTimeFormat

object VanguardReader extends FileReader {

  override def readETFData(file: File): Seq[ETFData] = {
    val dateFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
    val rawData = io.Source.fromFile(file).getLines.toList
    val code = file.getName.slice(9, 12)
    val brand = file.getName.drop(18).dropRight(4)
    val xnumber = file.getName.slice(13,17)

    for (
      line <- rawData
      if line.trim.nonEmpty
    ) yield {
      // https://stackoverflow.com/questions/13335651/scala-split-string-by-commnas-ignoring-commas-between-quotes
      val cols = line.split(""",(?=([^\"]*\"[^\"]*\")*[^\"]*$)""").map(_.trim.replaceAllLiterally(""""""", ""))
      val (unparsedDate: String, nAV: Double) = (cols(0), convertStringColumnToDouble(cols(2)))
      ETFData(dateFormatter.parseDateTime(unparsedDate), code, brand, xnumber, 0d, nAV, 0d)
    }

  }



}
