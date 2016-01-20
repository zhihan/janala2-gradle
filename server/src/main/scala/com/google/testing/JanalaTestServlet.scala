package com.google.testing

import org.scalatra.ScalatraServlet

class JanalaTestServlet extends ScalatraServlet {

  get("/") {
    contentType = "text/html"

    """<html ng-app="testApp">
      <head>
      <title>Janala Test Service</title>
      <link rel="stylesheet" href="css/bootstrap.css"></link>
      <script src="js/angular.min.js"> </script>
      <script src="js/controller.js"></script>
      </head>
      <body>
        <h1>{{ "No tests" }} is running</h1>
        <div ng-controller="mainController">
        </div>
      </body>
    </html>"""
  }

}
