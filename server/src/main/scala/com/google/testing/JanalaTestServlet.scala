package com.google.testing

import org.scalatra._
import scalate.ScalateSupport

class JanalaTestServlet extends MyTestServerStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

}
