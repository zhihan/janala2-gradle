package com.google.testing

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}
import org.slf4j.{Logger, LoggerFactory}

// JSON handling support from Scalatra
import org.scalatra.json._

import org.scalatra._

case class Test(url: String, result: String)

class JsonEndpoints extends ScalatraServlet with JacksonJsonSupport {
  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }
  get("/tests/:id") {
    logger.info("Gettting a test for id %s", params("id"))
    Test("source", "pass")
  }

  post("/tests") {
    logger.info("Getting all tests")
  }
}
