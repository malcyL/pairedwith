package uk.me.landonsonline.pairedwith.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public TestObject get(){
	return new TestObject("bob");
    }

}
