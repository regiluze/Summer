package org.summer.view

import com.sampullara.mustache.Mustache
import com.sampullara.mustache.Scope

class View {
  String name
  String template
  Mustache mustache

  String render(Map model) {
    def writer = new StringWriter()
    mustache.execute writer, new Scope(model)
    writer as String
  }
}
