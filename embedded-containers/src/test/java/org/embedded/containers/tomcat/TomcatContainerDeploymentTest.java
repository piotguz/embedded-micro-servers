package org.embedded.containers.tomcat;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.embedded.containers.EmbeddedServletContainerException;
import org.testng.annotations.Test;
//http://www.artificialworlds.net/blog/2015/02/05/programmatic-equivalents-of-web-xml-sections-for-tomcat/
public class TomcatContainerDeploymentTest {
	@Test
	public void deploySimpleServlet()
			throws EmbeddedServletContainerException, ServletException, MalformedURLException {
		TomcatContainer container = new TomcatContainer();

		container.configure(TomcatServerParams.SERVER_PORT, "8080");

		container.deployWebApp("src/main/webapp");
		container.start();

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/hello");

		Response response = target.request().get();

		String entity = response.readEntity(String.class);

		assertEquals(entity, "Hello, World!");
		
		container.stop();

	}
	
	@Test
	public void deployExternalService() throws EmbeddedServletContainerException, ServletException {
		TomcatContainer container = new TomcatContainer();

		container.configure(TomcatServerParams.SERVER_PORT, "8080");

		container.deployWebApp("d:/temp/webapp");
		container.start();

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/hello");

		Response response = target.request().get();

		String entity = response.readEntity(String.class);

		assertEquals(entity, "Hello, World!");
		
		container.stop();
		
	}
	
	@Test
	public void deployRestService() throws EmbeddedServletContainerException {
		TomcatContainer container = new ResteasyOnTomcat();
		
		container.start();
	}
}
