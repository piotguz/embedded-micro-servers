package org.embedded.containers.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.embedded.containers.EmbeddedServletContainerException;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

public class ResteasyOnTomcat extends PlainTomcat {
	public static final String JAVAX_WS_RS_APPLICATION="javax.ws.rs.Application";

	private String jaxrsApplication;

	@Override
	public void start() throws EmbeddedServletContainerException {
		Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
		
		Wrapper servlet = tomcat.addServlet("", "resteasy-servlet", HttpServletDispatcher.class.getName());
		
		jaxrsApplication = configuration.getProperty(JAVAX_WS_RS_APPLICATION);
		
		if (jaxrsApplication != null)
			servlet.addInitParameter(JAVAX_WS_RS_APPLICATION, jaxrsApplication);
		
		
		ctx.addParameter("resteasy.scan", "true");
		ctx.addParameter("resteasy.servlet.mapping.prefix", "/rest");
		ctx.addApplicationListener(ResteasyBootstrap.class.getName());
		ctx.addServletMappingDecoded( "/rest/*", "resteasy-servlet" );
		
		
		super.start();
		
	}
	

}
