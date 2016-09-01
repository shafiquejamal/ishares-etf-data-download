package com.eigenroute

import java.io.File

import com.eigenroute.db.DevProdDBConfig
import com.eigenroute.etfdata.{ETFDAO, Reader}
import com.eigenroute.util.{UUIDProviderImpl, TimeProviderImpl}

object Main {

  def main(args: Array[String]): Unit = {

    val filesDirectory = new File(args(0))
    val files = filesDirectory.listFiles.filter(_.isFile).filter(_.getAbsolutePath.endsWith(".csv")).toList

    val dBConfig = new DevProdDBConfig()
    dBConfig.setUpAllDB()
    val eTFDAO = new ETFDAO(TimeProviderImpl, UUIDProviderImpl, dBConfig)

    files.foreach { file =>
      Importer.importData(Reader.readETFData(file), eTFDAO)
    }

    dBConfig.closeAll()
  }
}
