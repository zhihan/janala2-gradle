package com.google.testing

import org.slf4j.LoggerFactory

import scala.collection.mutable.Map 

case class Test(val ID: Int, val url: String, val state: String)

class TestState(val ID: Int,
  val url: String,
  val dir: String,
  val state: String,
  val log: String) {


  def toTest:Test = Test(ID, url, state)
}

object TestState {
  def fromTest(t: Test): TestState = new TestState(t.ID,
    t.url, "", t.state, "")
}


object Store {

  val logger = LoggerFactory.getLogger(getClass)

  val testStates = Map[Int, TestState]()

  def getTests: List[Test] = testStates.values.toList.map{ _.toTest }

  def addTest(t: Test) {
    testStates += (t.ID -> TestState.fromTest(t))
    logger.info("Store are {}", testStates)
  }

}
