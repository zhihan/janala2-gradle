package com.google.testing

import org.slf4j.{Logger, LoggerFactory}

import org.scalatra.ScalatraServlet

class JanalaTestServlet extends ScalatraServlet {
  val logger = LoggerFactory.getLogger(getClass)

  get("/") {
    contentType = "text/html"

    """<html ng-app="testApp">
      <head>
      <title>Janala Test Service</title>
      <link rel="stylesheet" href="css/bootstrap.css"></link>
      <script src="js/angular.min.js"> </script>
      <script src="js/angular-resource.min.js"></script>
      <script src="js/controller.js"></script>
      </head>
      <body>
        <h1>Testing service</h1>
        <div ng-controller="mainController">
        <div> 
          <input type="text" ng-model="url"></input>
          <button ng-click="addTest()">Add</button>
        </div>

        <div ng-model="tests">
          <div ng-repeat="test in tests track by test.ID">
            {{ test }}
          </div>
        </div>


        </div>
      </body>
    </html>"""
  }

}
