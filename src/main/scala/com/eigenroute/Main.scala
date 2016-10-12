package com.eigenroute

import java.io.File

import com.eigenroute.db.DevProdDBConfig
import com.eigenroute.etfdata.{ETFDAO, ISharesReader}
import com.eigenroute.util.{UUIDProviderImpl, TimeProviderImpl}

object Main {

  def main(args: Array[String]): Unit = {

    val filesDirectory = new File(args(0))
    val iSharesFiles =
      filesDirectory.listFiles.filter(_.isFile)
      .filter(_.getAbsolutePath.endsWith(".csv"))
      .filter(_.getAbsolutePath.contains("ishares")).toList

    val dBConfig = new DevProdDBConfig()
    dBConfig.setUpAllDB()
    val eTFDAO = new ETFDAO(TimeProviderImpl, UUIDProviderImpl, dBConfig)

    iSharesFiles.foreach { file =>
      Importer.importData(ISharesReader.readETFData(file), eTFDAO)
    }

    dBConfig.closeAll()
  }
}
