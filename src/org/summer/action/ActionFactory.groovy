package org.summer.action

import org.summer.http.HttpMethod
import org.summer.route.Route

class ActionFactory {
  static create = { HttpMethod method, String path, Closure code ->
    new Action(method: method, route: Route.create(path), code: code)
  }
  static createGet = create.curry(HttpMethod.GET)
  static createPost = create.curry(HttpMethod.POST)
  static createAny = create.curry(HttpMethod.ANY)
}
