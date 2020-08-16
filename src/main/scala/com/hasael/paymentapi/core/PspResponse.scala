package com.hasael.paymentapi.core

case class PspResponse(resultCode: String, resultDescription: String)

object PspResponse {

  implicit class PspResponseOps(self: PspResponse) {
    def toAuthorizationResponse(): PaymentResponse = {
      if (self.resultCode != 20)
        FailResponse(self.resultDescription)
      else SuccessResponse()
    }
  }

}