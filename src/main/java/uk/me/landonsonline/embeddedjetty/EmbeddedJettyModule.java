package uk.me.landonsonline.embeddedjetty;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogManager;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class EmbeddedJettyModule extends ServletModule{

    private final String propertyPackage;

    static {
	// Jersey uses java.util.logging, so here we bridge to slf4 
	// This is a static initialiser because we don't want to do this multiple times.
	java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");  
	Handler[] handlers = rootLogger.getHandlers();  
	for (int i = 0; i < handlers.length; i++) {  
	    rootLogger.removeHandler(handlers[i]);  
	}  
	SLF4JBridgeHandler.install();  
    }

    public EmbeddedJettyModule(String  propertyPackage){
	this.propertyPackage = propertyPackage;
    }

    @Override
    protected void configureServlets() {
	final Map<String, String> params = new HashMap<String, String>();
	params.put(PackagesResourceConfig.PROPERTY_PACKAGES, propertyPackage);
	params.put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE.toString());

	serve("/*").with(GuiceContainer.class, params);
    }
}
