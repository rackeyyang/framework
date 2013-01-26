package com.riambsoft.framework;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;

public interface FrameworkLauncher {

	public void init(ServletContext servletContext) throws FrameworkException;

	public void deploy() throws FrameworkException;

	public void undeploy() throws FrameworkException;

	public BundleContext start() throws FrameworkException;

	public void stop() throws FrameworkException;

	public void destroy() throws FrameworkException;
}
