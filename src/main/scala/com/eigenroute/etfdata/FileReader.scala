package com.eigenroute.etfdata

import java.io.File

trait FileReader {

  def readETFData(file: File): Seq[ETFData]

}
