package org.summer.exception

class ViewNotFoundException extends Exception {
  ViewNotFoundException(String name) {
    super("View ${name} not found")
  }
}
