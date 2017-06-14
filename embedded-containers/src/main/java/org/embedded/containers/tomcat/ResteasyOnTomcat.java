package org.embedded.containers.tomcat;

import org.embedded.containers.EmbeddedServletContainerException;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

public class ResteasyOnTomcat extends TomcatContainer {

	@Override
	public void start() throws EmbeddedServletContainerException {
		tomcat.addServlet("/", "resteasy-bootstrap", ResteasyBootstrap.class.getName());
		tomcat.addServlet("/", "resteasy", HttpServletDispatcher.class.getName());
		super.start();
		
	}
	

}
