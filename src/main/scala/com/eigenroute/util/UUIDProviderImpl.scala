package com.eigenroute.util

import java.util.UUID

object UUIDProviderImpl extends UUIDProvider {

  override def randomUUID(): UUID = UUID.randomUUID()

}