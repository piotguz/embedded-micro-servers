package org.embedded.containers.tomcat;

import org.embedded.containers.ServerConfigurationParams;

public enum TomcatServerParams implements ServerConfigurationParams {
	SERVER_PORT("server.port"),
	SERVER_PORT_UBOUND("server.port.ubound"),
	SERVER_PORT_LBOUND("server.port.lbound"),
	SERVER_AUTO_DEPLOY("server.auto.deploy");
	
	final String paramName;
	
	private TomcatServerParams(String param) {
		this.paramName = param;
	}

	public String getParamName() {
		return paramName;
	}
	
	
}
