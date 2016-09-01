package com.eigenroute.etfdata

import java.util.UUID

import scalikejdbc._

import com.eigenroute.util.TestTimeProviderImpl
import org.joda.time.DateTime

trait ETFDataFixture {

  val id1 = UUID.fromString("00000000-0000-0000-0000-000000000001")
  val date1 = new DateTime(2016, 3, 21, 0, 0, 0)

  val now = new TestTimeProviderImpl().now

  val eTFDataToAdd = Seq(
    sql"""INSERT INTO historical (id, code, brand, xnumber, indexreturn, nav, asofdate, exdividend, createdat) values
         (${id1}, 'XEM', '246800', 'iSharesblahblahblah', 110, 25, ${date1}, 7, ${now})
       """
  )
}
