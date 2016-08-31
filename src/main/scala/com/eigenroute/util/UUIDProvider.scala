package com.eigenroute.util

import java.util.UUID

trait UUIDProvider {

  def randomUUID(): UUID

}