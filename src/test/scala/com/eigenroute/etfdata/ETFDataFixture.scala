package com.eigenroute.etfdata

import java.util.UUID

import scalikejdbc._

import com.eigenroute.util.TestTimeProviderImpl
import org.joda.time.DateTime

trait ETFDataFixture {

  val id1 = UUID.fromString("00000000-0000-0000-0000-000000000001")
  val id2 = UUID.fromString("00000000-0000-0000-0000-000000000002")
  val date1 = new DateTime(2016, 3, 21, 0, 0, 0)
  val dateLater = new DateTime(2016, 3, 26, 0, 0, 0)

  val now = new TestTimeProviderImpl().now

  val eTFDataEarlier = ETFData(date1, "XEM", "iSharesblahblahblah", "246800", 110d, 25d, 7d)
  val eTFDataLater = ETFData(dateLater, "XEM", "iSharesblahblahblah", "246800", 119d, 24d, 7d)

  val eTFDataToAdd = Seq(
    sql"""INSERT INTO historical (id, code, brand, xnumber, indexreturn, nav, asofdate, exdividend, createdat) values
         (${id1}, 'XEM', 'iSharesblahblahblah', '246800', 110, 25, ${date1}, 7, ${now})
       """,
    sql"""INSERT INTO historical (id, code, brand, xnumber, indexreturn, nav, asofdate, exdividend, createdat) values
         (${id2}, 'XEM', 'iSharesblahblahblah', '246800', 119, 24, ${dateLater}, 7, ${now})
       """
  )
}
