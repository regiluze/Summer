package org.summer.controller

import org.summer.action.Action
import org.summer.action.AllActions
import org.summer.exception.ActionNotFoundException
import org.summer.http.HttpMethod

class Controller {
  AllActions actions = new AllActions()

  Controller leftShift(Action action) {
    actions << action
    this
  }

  Action getActionServing(HttpMethod method, path) {
    def action = actions.oneMatching(method, path)
    if (null == action)
      throw new ActionNotFoundException(method, path)
    action
  }
}
