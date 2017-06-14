package org.embedded.containers.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.embedded.containers.EmbeddedServletContainerException;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

public class ResteasyOnTomcat extends PlainTomcat {

	@Override
	public void start() throws EmbeddedServletContainerException {
		Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
		
		tomcat.addServlet("", "resteasy-servlet", HttpServletDispatcher.class.getName());
		
		ctx.addParameter("resteasy.scan", "true");
		ctx.addParameter("resteasy.servlet.mapping.prefix", "/rest");
		ctx.addApplicationListener(ResteasyBootstrap.class.getName());
		ctx.addServletMappingDecoded( "/rest/*", "resteasy-servlet" );
		
		
		super.start();
		
	}
	

}
