package org.summer.action.context

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.summer.Summer
import org.summer.view.View

class Context {
  @Delegate Map storage = [:]

  static Context factory(Summer app, Map params, HttpServletRequest request, HttpServletResponse response) {
    Context c = new Context()
    c.app = app
    c.request = request
    c.response = response
    c.params = params
    c.views = app.views
    return c
  }

  String render(View view) {
    view.render(storage)
  }
}
