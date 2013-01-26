package com.riambsoft.console;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.riambsoft.framework.FrameworkConstants;

public class BundlesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4804965819597520207L;

	private ServletConfig servletConfig;
	
	private ServletContext servletContext;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		servletConfig = config;
		servletContext = config.getServletContext();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		BundleContext bundleContext = (BundleContext)servletContext.getAttribute(FrameworkConstants.RIAMBSOFT_FRAMEWORK_BUNDLE_CONTEXT);
		
		Bundle[] bundles = bundleContext.getBundles();
		
		Writer writer = resp.getWriter();
		
		for(Bundle bundle : bundles){
			writer.append(bundle.getSymbolicName());
			writer.append("     ");
		}
		
		writer.flush();
		writer.close();
	}
}
