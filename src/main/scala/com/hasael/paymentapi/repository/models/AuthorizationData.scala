package com.hasael.paymentapi.repository.models

import com.hasael.paymentapi.core.{AuthorizationRequest, PaymentResponse}

case class AuthorizationData(data: String, transactionId: String, response: String)

object AuthorizationData {
  def from(authorizationRequest: AuthorizationRequest, paymentResponse: PaymentResponse): AuthorizationData = new AuthorizationData(authorizationRequest.data, authorizationRequest.trxId, paymentResponse.response)
}
