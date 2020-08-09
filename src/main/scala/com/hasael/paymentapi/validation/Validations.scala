package com.hasael.paymentapi.validation

import io.circe.HCursor

object Validations {
  def nonEmptyString(parameterName: String)(cursor: HCursor): Boolean = {
    cursor.downField(parameterName).as[String].exists(_.nonEmpty)
  }

  def minLengthString(parameterName: String, min: Int)(cursor: HCursor): Boolean = {
    cursor.downField(parameterName).as[String].exists(s => s.nonEmpty && s.length >= min)
  }

  def nonEmptyList(parameterName: String)(cursor: HCursor): Boolean = {
    cursor.downField(parameterName).as[Seq[String]].exists(_.nonEmpty)
  }
}
