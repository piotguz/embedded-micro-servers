package org.embedded.containers.tomcat;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.embedded.containers.EmbeddedServletContainerException;
import org.embedded.containers.HelloApplication;
import org.testng.annotations.Test;
//http://www.artificialworlds.net/blog/2015/02/05/programmatic-equivalents-of-web-xml-sections-for-tomcat/
public class TomcatContainerDeploymentTest {
	@Test
	public void deploySimpleServlet()
			throws EmbeddedServletContainerException, ServletException, MalformedURLException {
		PlainTomcat container = new PlainTomcat();

		container.deployWebApp("src/main/webapp");
		container.start();

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(String.format("http://localhost:%d/hello", container.getServerPort()));

		Response response = target.request().get();

		String entity = response.readEntity(String.class);

		assertEquals(entity, "Hello, World!");
		
		container.stop();

	}
	
	
	@Test
	public void deployRestService() throws EmbeddedServletContainerException {
		PlainTomcat container = new ResteasyOnTomcat();
		
		container.configure(ResteasyOnTomcat.JAVAX_WS_RS_APPLICATION, HelloApplication.class.getName());
		
		container.start();
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(String.format("http://localhost:%d/rest/hello", container.getServerPort()));

		Response response = target.request().get();

		String entity = response.readEntity(String.class);

		assertEquals(entity, "Well hello from reateasy!");

		container.stop();
	}
}
