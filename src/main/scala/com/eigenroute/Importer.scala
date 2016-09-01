package com.eigenroute

import com.eigenroute.etfdata.{ETFDAO, ETFData}

object Importer {

  def importData(securitiesData:Seq[ETFData], eTFDAO:ETFDAO) = {
    val maybeLatestSecurityData = securitiesData.sortWith(_.asOfDate.getMillis > _.asOfDate.getMillis).headOption
    maybeLatestSecurityData foreach { data =>
      val code = data.code
      val xnumber = data.xnumber

      val maybeExistingETFData = eTFDAO.latestBy(code, xnumber)
      val maybeLatestData = maybeExistingETFData.map(_.asOfDate)

      val securitiesDataToImport = maybeLatestData.fold(securitiesData) {
        latestDate => securitiesData.filter(_.asOfDate.getMillis > latestDate.getMillis)
      }

      securitiesDataToImport.foreach { eTFDATA => eTFDAO.save(eTFDATA) }
    }
  }
}
