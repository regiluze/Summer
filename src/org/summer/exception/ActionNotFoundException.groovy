package org.summer.exception

import org.summer.http.HttpMethod

class ActionNotFoundException extends Exception {
  ActionNotFoundException(HttpMethod method, String path) {
    super("Action ${method}:${path} not defined")
  }
}
