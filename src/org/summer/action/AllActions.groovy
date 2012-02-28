package org.summer.action

import org.summer.http.HttpMethod

class AllActions {
  @Delegate Collection actions = []

  Action oneMatching(HttpMethod method, String path) {
    actions.find { Action it -> it.with { isServing(method) && matches(path)} }
  }
}
