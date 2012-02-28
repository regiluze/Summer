package org.summer.view

import com.sampullara.mustache.MustacheBuilder

class ViewContainer {
  @Delegate
  Map<String, View> views = [:]
  static final MustacheBuilder builder = new MustacheBuilder();

  void add(String name, String template) {
    View view = new View()
    view.name = name
    view.template = template
    view.mustache = builder.build new StringReader(template), name
    views[name] = view
  }
}
