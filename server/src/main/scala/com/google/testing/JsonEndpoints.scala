package com.google.testing

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}
import org.slf4j.{Logger, LoggerFactory}

// JSON handling support from Scalatra
import org.scalatra.json._

import org.scalatra._


class JsonEndpoints extends ScalatraServlet with JacksonJsonSupport {
  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  before() {
    contentType = formats("json")
  }

  get("/test") {
    Worker.run

    Store.getTests
  }

  get("/test/:id") {
    logger.info("Gettting a test for id {}", params("id"))
    Test(params("id").toInt, "source", "pass")
  }

  post("/test/:id") {
    logger.info("Saving a test for id {}: {}", params("id"))
    Store.addTest(parsedBody.extract[Test])
    Worker.run
  }

  delete("/test/:id") {
    logger.info("Deleting a test for id {}", params("id"))
  }

}
