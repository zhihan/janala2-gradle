package com.google.testing

import org.scalatra.ScalatraServlet

class JanalaTestServlet extends ScalatraServlet {

  get("/") {
    <html>
      <meta charset="utf=8" />
      <title>Janala Test Service</title>
      <link rel="stylesheet" href="css/bootstrap.css" />
      <body>
        <h1>No test is running</h1>
      </body>
    </html>
  }

}
