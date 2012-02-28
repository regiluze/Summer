package org.summer.servlet

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.summer.Summer
import org.summer.exception.ActionNotFoundException
import org.summer.http.HttpStatus

class SummerServlet extends HttpServlet {
  Summer summer

  SummerServlet(Summer summer) {
    this.summer = summer
  }

  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      def document = summer.serve request, response
      writeResponse response, document
    } catch (ActionNotFoundException e) {
      response.status = HttpStatus.NOT_FOUND.code
      writeResponse response, e.getMessage()
    }
  }

  private writeResponse(HttpServletResponse response, document) {
    response.writer.print(document)
  }
}
