package com.eigenroute.etfdata

import com.eigenroute.db.DBConfig
import com.eigenroute.util.{UUIDProvider, TimeProvider}
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class ETFDAO(timeProvider:TimeProvider, uUIDProvider: UUIDProvider, dBConfig: DBConfig) {

  val namedDB = NamedDB(Symbol(dBConfig.dBName))

  def save(eTFData: ETFData):Try[ETFData] = {
    namedDB localTx { implicit dBSession =>

      val now = timeProvider.now()
      val uuid = uUIDProvider.randomUUID()

      val eTFDataAlreadyInDB = sql"""SELECT code, brand, indexreturn, nav, asofdate, exdividend FROM historical where
          code = ${eTFData.code} AND
          indexreturn = ${eTFData.indexReturn} AND
          nav = ${eTFData.nAV} AND
          asofdate = ${eTFData.asOfDate} AND
          exdividend = ${eTFData.exDividends}
         """.map(ETFData.converter).single().apply()

      if (eTFDataAlreadyInDB.isEmpty) {
        val insert = sql"""INSERT INTO historical (id, code, brand, indexreturn, nav, asofdate, exdividend, createdat) values (${uuid}, ${eTFData.code}, 'iShares', ${eTFData.indexReturn}, ${eTFData.nAV}, ${eTFData.asOfDate}, ${eTFData.exDividends}, ${now} )""".update().apply
        if (insert == 0) {
          Failure[ETFData](new RuntimeException("Could not insert data"))
        } else {
          Success(eTFData)
        }
      } else {
        Failure[ETFData](new RuntimeException("Data already exists"))
      }

    }
  }

}
