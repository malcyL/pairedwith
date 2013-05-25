package uk.me.landonsonline.embeddedjetty;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;

public class HttpServer {

    private static final transient Logger LOG = LoggerFactory.getLogger(HttpServer.class);

    private Server server;

    public void start(int port, final Injector injector) throws Exception{
	LOG.info("Starting http server on port {}", port);
	server = new Server();
	Connector connector = new SelectChannelConnector();
	connector.setPort(port);
	server.setConnectors(new Connector[]{connector});

	Context context = new Context(server, "/");
	context.addServlet(DefaultServlet.class, "/");
	context.addFilter(GuiceFilter.class, "/*", 0);
	context.addEventListener(new GuiceServletContextListener() {
	    @Override
	    protected Injector getInjector() {
		return injector;
	    }
	});
	try {
	    server.start();
	} catch (Exception e) {
	    LOG.error("Error starting HTTP Server" , e);
	    throw new Exception("Unable to start HTTP Server", e);
	}
    }

    public boolean isRunning() {
	if (server != null) {
	    return server.isRunning();
	} else {
	    return false;
	}
    }

    public void stop() throws Exception {
	if (server != null) {
	    server.stop();
	}
    }

    public void waitForShutdown() throws InterruptedException {
	if (server != null) {
	    server.join();
	}
    }
}
