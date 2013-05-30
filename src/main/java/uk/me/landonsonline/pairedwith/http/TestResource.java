package uk.me.landonsonline.pairedwith.http;

import java.io.StringWriter;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.me.landonsonline.pairedwith.TestObject;

@Path("/test")
public class TestResource {

    private static final Logger LOG = LoggerFactory.getLogger(TestResource.class);
    
    public TestResource() {
	LOG.info("Test Resource Created.");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TestObject get() {
	return new TestObject("bob");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty("file.resource.loader.path", "src/main/webapp/templates");
        ve.init(p);
        
        /*  next, get the Template  */
        Template t = ve.getTemplate( "helloworld.vm" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("name", "bob");
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
	return writer.toString();
    }
}
