package org.embedded.containers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloServlet", urlPatterns = { "/hello" })
public class HelloServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7882153686926520798L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
        ServletOutputStream out = resp.getOutputStream();
        out.write("Hello, World!".getBytes());
        out.flush();
        out.close();
	}
	
}
