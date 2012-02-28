package org.summer.action

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.summer.Summer
import org.summer.action.context.Context
import org.summer.http.HttpMethod
import org.summer.route.Route

class Action {
  @Delegate Closure code
  Route route
  HttpMethod method = HttpMethod.ANY

  def execute(Summer app, HttpServletRequest request, HttpServletResponse response) {
    def params = route.parseParamsFrom(request.pathInfo)
    code.delegate = Context.factory(app, params, request, response)
    code.call()
  }

  boolean matches(String path) {
    route.matches path
  }

  boolean isServingAnyMethod() {
    this.method == HttpMethod.ANY
  }

  boolean isServing(HttpMethod method) {
    servingAnyMethod || this.method == method
  }
}
