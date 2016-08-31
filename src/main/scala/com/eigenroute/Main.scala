package com.eigenroute

import java.io.File

object Main {

  def main(args: Array[String]): Unit = {

    val filesDirectory = new File(args(0))

    val files = if (filesDirectory.exists && filesDirectory.isDirectory) {
      filesDirectory.listFiles.filter(_.isFile).filter(_.getAbsolutePath.endsWith(".xls")).toList
    } else if (filesDirectory.isFile) {
      List(filesDirectory)
    }



  }
}
