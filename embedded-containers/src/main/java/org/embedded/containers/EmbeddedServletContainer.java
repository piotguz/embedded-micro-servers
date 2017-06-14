package org.embedded.containers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public abstract class EmbeddedServletContainer {
	
	protected int serverPort;
	protected Properties configuration = new Properties();
	protected Properties servlets = new Properties();

	public abstract void start() throws EmbeddedServletContainerException;

	public abstract void startAndWait() throws EmbeddedServletContainerException;

	public abstract void stop() throws EmbeddedServletContainerException;
	
	public abstract void configure(ServerConfigurationParams key, String value) throws EmbeddedServletContainerException;

	public int nextFreePort(int from, int to) throws EmbeddedServletContainerException {

		for(int port=from; port <= to; port++) {			
			if (isPortAvailable(port))
				return port;
		}
		
		throw new EmbeddedServletContainerException(String.format("No free ports found in given range: <%d;%d>!", from, to));
	}

	public boolean isPortAvailable(int port) {
		try {
			new ServerSocket(port).close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
