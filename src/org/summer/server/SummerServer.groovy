package org.summer.server

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext
import org.summer.Summer
import org.summer.servlet.SummerServlet

class SummerServer extends Server {
  public SummerServer(int port) {
    super(port);
  }

  public static SummerServer factory(Summer app) {
    SummerServer server = new SummerServer(8090);
    WebAppContext context = new WebAppContext();
    context.addServlet new ServletHolder(new SummerServlet(app)), '/*';
    context.setResourceBase('/Users/guillermo/src/Summer/web');
    context.setContextPath('/');
    context.setParentLoaderPriority(true);
    server.setHandler(context);
    return server
  }

  void run() {
    super.start()
    super.join()
  }
}
