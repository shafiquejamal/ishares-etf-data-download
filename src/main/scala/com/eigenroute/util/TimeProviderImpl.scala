package com.eigenroute.util

import org.joda.time.DateTime

object TimeProviderImpl extends TimeProvider {

  override def now(): DateTime = DateTime.now()

}