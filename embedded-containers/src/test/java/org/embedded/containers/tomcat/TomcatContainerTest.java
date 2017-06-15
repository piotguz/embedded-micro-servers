package org.embedded.containers.tomcat;

import static org.testng.Assert.assertFalse;

import org.embedded.containers.EmbeddedServletContainerException;
import org.testng.annotations.Test;

public class TomcatContainerTest {

	@Test
	public void startTest() throws EmbeddedServletContainerException {
		PlainTomcat container = new PlainTomcat();
		container.start();
		container.stop();
	}

	@Test
	public void stopNotStartedTest() throws EmbeddedServletContainerException {
		PlainTomcat container = new PlainTomcat();
		container.stop();
	}

	@Test
	public void startWithCustomPort() throws EmbeddedServletContainerException {
		PlainTomcat container = new PlainTomcat();
		container.configure(PlainTomcat.SERVER_PORT, "30000");

		container.start();

		assertFalse(container.isPortAvailable(8080));

		container.stop();
	}

	@Test(expectedExceptionsMessageRegExp = "Port .* is already in use!", expectedExceptions = EmbeddedServletContainerException.class)
	public void failWithLockedPort() throws EmbeddedServletContainerException {
		PlainTomcat container1 = new PlainTomcat();
		container1.configure(PlainTomcat.SERVER_PORT, "8080");

		container1.start();

		PlainTomcat container2 = new PlainTomcat();
		container2.configure(PlainTomcat.SERVER_PORT, "8080");

		container2.start();

		container2.stop();
		container1.stop();
	}

	@Test(expectedExceptionsMessageRegExp = "No free ports found.*", expectedExceptions = EmbeddedServletContainerException.class)
	public void failWithNoFreePorts() throws EmbeddedServletContainerException {
		PlainTomcat container = new PlainTomcat();
		container.configure(PlainTomcat.SERVER_PORT_LBOUND, "10");
		container.configure(PlainTomcat.SERVER_PORT_UBOUND, "8");

		container.start();
		container.stop();
	}


}
