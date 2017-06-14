package org.embedded.containers.tomcat;

import static org.embedded.containers.tomcat.TomcatServerParams.SERVER_PORT;
import static org.embedded.containers.tomcat.TomcatServerParams.SERVER_PORT_LBOUND;
import static org.embedded.containers.tomcat.TomcatServerParams.SERVER_PORT_UBOUND;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.embedded.containers.EmbeddedServletContainer;
import org.embedded.containers.EmbeddedServletContainerException;
import org.embedded.containers.ServerConfigurationParams;

/**
 * Implementation of embedded tomcat container
 * 
 * @author gup8wz
 *
 */
public class TomcatContainer extends EmbeddedServletContainer {
	Tomcat tomcat =  new Tomcat();

	@Override
	public void start() throws EmbeddedServletContainerException {

		int from = Integer.parseInt(configuration.getProperty(SERVER_PORT_LBOUND.paramName, "30000"));
		int to = Integer.parseInt(configuration.getProperty(SERVER_PORT_UBOUND.paramName, "40000"));

		if (configuration.containsKey(SERVER_PORT.paramName)) {
			serverPort = Integer.parseInt(configuration.getProperty(SERVER_PORT.paramName));
			if (!isPortAvailable(serverPort))
				throw new EmbeddedServletContainerException("Port %d is already in use!", serverPort);
		} else {
			serverPort = nextFreePort(from, to);
		}

		tomcat.setPort(serverPort);

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			throw new EmbeddedServletContainerException(e);
		}

	}

	@Override
	public void startAndWait() throws EmbeddedServletContainerException {
		start();
		tomcat.getServer().await();

	}

	@Override
	public void stop() throws EmbeddedServletContainerException {
		if (tomcat == null || tomcat.getServer() == null) {
			return;
		}
		if (tomcat.getServer().getState() != LifecycleState.DESTROYED) {
			if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
				try {
					tomcat.stop();
				} catch (LifecycleException e) {
					throw new EmbeddedServletContainerException(e);
				}
			}
			try {
				tomcat.destroy();
			} catch (LifecycleException e) {
				throw new EmbeddedServletContainerException(e);
			}
		}

	}

	@Override
	public void configure(ServerConfigurationParams key, String value) throws EmbeddedServletContainerException {
		if (key instanceof TomcatServerParams) {
			TomcatServerParams param = (TomcatServerParams) key;
			configuration.setProperty(param.getParamName(), value);
		} else {
			throw new EmbeddedServletContainerException("Enum %s is not applicable for %s!",
					key.getClass().getSimpleName(), getClass().getName());
		}

	}

	public Tomcat getTomcat() {
		return tomcat;
	}

	public void deployWebApp(String webappDirLocation) throws EmbeddedServletContainerException {
		try {			
			StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
			
			File additionWebInfClasses = new File("target/classes");
			WebResourceRoot resources = new StandardRoot(ctx);
			resources.addPreResources(
					new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
			ctx.setResources(resources);
		} catch (Exception e) {
			throw new EmbeddedServletContainerException(e);
		}
	}

}
