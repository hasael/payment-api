package com.hasael.paymentapi.repository

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDBAsync, AmazonDynamoDBAsyncClientBuilder}

class DynamoClientProvider {
  lazy val client: AmazonDynamoDBAsync =
    AmazonDynamoDBAsyncClientBuilder
      .standard()
      .withRegion(Regions.EU_CENTRAL_1)
      .build()
}
