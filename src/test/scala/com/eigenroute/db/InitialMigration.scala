package com.eigenroute.db

import org.flywaydb.core.Flyway
import scalikejdbc.DBSession

trait InitialMigration {

  def migrate(dBConfig: DBConfig)(implicit session: DBSession): Unit = {
    val flyway = new Flyway()
    flyway.setDataSource(dBConfig.url, dBConfig.username, dBConfig.password)
    flyway.clean()
    flyway.repair()
    flyway.migrate()
  }

}