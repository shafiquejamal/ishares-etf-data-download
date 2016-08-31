package com.eigenroute.util

import java.util.UUID

class TestUUIDProviderImpl extends UUIDProvider {

  var index: Int = 200

  override def randomUUID(): UUID = {
    index += 1
    UUID.fromString("00000000-0000-0000-0000-" + f"$index%012d")
  }

}

object TestUUIDProviderImpl {

  def apply() = new TestUUIDProviderImpl()

}