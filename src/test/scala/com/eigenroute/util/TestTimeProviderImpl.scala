package com.eigenroute.util

import org.joda.time.DateTime

class TestTimeProviderImpl extends TimeProvider {

  private var dateTime: DateTime = new DateTime(2016, 12, 30, 13, 14, 15)

  def setNow(newDateTime: DateTime): DateTime = {
    dateTime = newDateTime
    dateTime
  }

  override def now(): DateTime = dateTime

}

object TestTimeProviderImpl {

  def apply: TestTimeProviderImpl = new TestTimeProviderImpl()
}
