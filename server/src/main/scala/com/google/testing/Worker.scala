package com.google.testing

import org.slf4j.LoggerFactory
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global // default

import scala.sys.process._

object Worker {

  val logger = LoggerFactory.getLogger(getClass)

  def work(t: TestState): Future[TestState] = Future {
    logger.info("Running command")
    Seq("/bin/sleep", "5").!
    val result = Seq("/bin/echo", "done").!!
    logger.info("Getting result {}", result)
    t.copy(state="COMPLETED", log=result)
  }
  

  def workOn(t: TestState) {
    logger.info("Work on {}", t)
    val newT = t.copy(state="WORKING")
    Store.updateTest(newT)

    val result = work(newT)
    result.onSuccess {
      case (t:TestState) => Store.updateTest(t) 
    }
  }


  def run {
    Store.nextReady match {
      case Some(t) => workOn(t)
      case None => ()
    }
  }

}
