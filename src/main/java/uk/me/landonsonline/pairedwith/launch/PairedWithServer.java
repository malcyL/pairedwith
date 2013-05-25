package uk.me.landonsonline.pairedwith.launch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.me.landonsonline.embeddedjetty.EmbeddedJettyModule;
import uk.me.landonsonline.embeddedjetty.HttpServer;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class PairedWithServer {

    public static final int DEFAULT_HTTP_PORT = 8000;

    private static final Logger LOG = LoggerFactory.getLogger(PairedWithServer.class);

    public static Injector initInjector(){
	Module[] modules = {
		new EmbeddedJettyModule("uk.me.landonsonline.pairedwith.http")
	};
	return Guice.createInjector(modules);
    }

    public static void main(String[] args) throws Exception {
	try {
	    Injector injector = PairedWithServer.initInjector();
	    PairedWithServer server = injector.getInstance(PairedWithServer.class);
	    server.injector = injector;
	    String portEnvironmentProperty = System.getenv("PORT");
	    Integer port = (portEnvironmentProperty != null) ? Integer.valueOf(portEnvironmentProperty) : DEFAULT_HTTP_PORT;
	    server.startWebserver(port);
	} catch(Exception e) {
	    LOG.error("Unexpected Exception in main method", e);
	    throw e;
	}
    }

    private final HttpServer webserver;

    private Injector injector;

    @Inject
    public PairedWithServer(final HttpServer httpServer)  {
	webserver = httpServer;
    }

    public Injector getInjector() {
	return injector;
    }

    public void startWebserver(int port) throws Exception{
	LOG.info(String.format("Starting server on port %s", port));
	webserver.start(port, injector);
    }
}
