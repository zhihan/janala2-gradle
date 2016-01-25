package com.google.testing

import org.slf4j.LoggerFactory
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global // default

import scala.sys.env
import scala.sys.process._

import java.nio.file.Paths
import java.nio.file.Files

object Worker {

  val logger = LoggerFactory.getLogger(getClass)

  def work(t: TestState): Future[TestState] = Future {
    logger.info("Running command")

    val tmpDir = Paths.get("/tmp")
    val workingDir = Files.createTempDirectory(tmpDir,
      "working_" + t.ID)
    logger.info("Created working directory {}", workingDir.toString())
    val t1 = t.copy(dir=workingDir.toString())

    Seq("sleep", "5").!
    val result = Seq("echo", "done").!!
    logger.info("Getting result {}", result)
    t1.copy(state="COMPLETED", log=result)
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
