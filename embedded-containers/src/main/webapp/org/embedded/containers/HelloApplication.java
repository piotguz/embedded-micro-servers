package org.embedded.containers;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class HelloApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public HelloApplication() {
		System.out.println("Application init!!!!");
		singletons.add(new HelloResource());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
