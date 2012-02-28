package org.summer.runner

import org.summer.Summer
import org.summer.server.SummerServer

class SummerRunner {
  static void main(args) {
    Summer s = Summer.create()
    s.get '/', { render views.hello }
    SummerServer.factory(s).run();
  }
}

