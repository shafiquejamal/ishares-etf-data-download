package com.eigenroute.etfdata

import com.eigenroute.db.DBConfig
import com.eigenroute.util.{UUIDProvider, TimeProvider}
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class ETFDAO(timeProvider:TimeProvider, uUIDProvider: UUIDProvider, dBConfig: DBConfig) {

  private def queryETFData(eTFData: ETFData)(implicit session:DBSession):Option[ETFData] =
    sql"""SELECT code, brand, xnumber, indexreturn, nav, asofdate, exdividend FROM historical where
          code = ${eTFData.code} AND
          xnumber = ${eTFData.xnumber} AND
          indexreturn = ${eTFData.indexReturn} AND
          nav = ${eTFData.nAV} AND
          asofdate = ${eTFData.asOfDate} AND
          exdividend = ${eTFData.exDividends}
         """.map(ETFData.converter).single().apply()

  def latestBy(code:String, xnumber:String):Option[ETFData] =
    NamedDB(Symbol(dBConfig.dBName)) readOnly { implicit dBSession =>
      sql"""SELECT code, brand, xnumber, indexreturn, nav, asofdate, exdividend FROM historical where
            code = ${code} AND
            xnumber = ${xnumber} ORDER BY asofdate DESC LIMIT 1
           """.map(ETFData.converter).single().apply()
    }

  def by(eTFData: ETFData):Option[ETFData] =
    NamedDB(Symbol(dBConfig.dBName)) readOnly { implicit dBSession => queryETFData(eTFData) }

  def save(eTFData: ETFData):Try[ETFData] = {
    NamedDB(Symbol(dBConfig.dBName)) localTx { implicit dBSession =>

      val eTFDataAlreadyInDB = queryETFData(eTFData)

      if (eTFDataAlreadyInDB.isEmpty) {
        val now = timeProvider.now()
        val uuid = uUIDProvider.randomUUID()
        val insert = sql"""INSERT INTO historical (id, code, brand, xnumber, indexreturn, nav, asofdate, exdividend, createdat) values (${uuid}, ${eTFData.code}, ${eTFData.brand}, ${eTFData.xnumber}, ${eTFData.indexReturn}, ${eTFData.nAV}, ${eTFData.asOfDate}, ${eTFData.exDividends}, ${now} )""".update().apply
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
