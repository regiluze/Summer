package org.summer

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.summer.action.Action
import org.summer.action.ActionFactory
import org.summer.controller.Controller
import org.summer.http.HttpMethod
import org.summer.view.ViewContainer

class Summer {
  public Controller controller
  public ViewContainer views

  static Summer create() {
    Summer app = new Summer()
    app.controller = new Controller()
    app.views = new ViewContainer()
    app.arm(new ConfigSlurper().parse(new File('config.groovy').toURL()))
    return app
  }

  def match = { HttpMethod method, path, action ->
    controller << ActionFactory.create(method, path, action)
    this
  }

  def get = match.curry(HttpMethod.GET)
  def post = match.curry(HttpMethod.POST)

  String serve(HttpServletRequest request, HttpServletResponse response) {
    def path = request.pathInfo
    def method = request.method as HttpMethod
    Action action = controller.getActionServing(method, path)
    action.execute(this, request, response) as String
  }

  Summer arm(config) {
    if (isTemplatesPathConfigured(config))
      loadTemplates(config)
    this
  }

  private isTemplatesPathConfigured(config) {
    config?.views?.path
  }

  private loadTemplates(config) {
    def dir = new File(config.views.path as String)
    dir.eachFile { File file ->
      if (file.isFile() && isHtmlFile(file))
        views.add stripExtensionFrom(file.name), file.text
    }
  }

  private boolean isHtmlFile(File file) {
    file.name.endsWith('html')
  }

  private String stripExtensionFrom(String filename) {
    filename[0..-1 * (filename.lastIndexOf('.') + 1)]
  }
}
