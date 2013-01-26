package com.riambsoft.framework;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;

import com.riambsoft.framework.impl.DefaultFrameworkLauncher;

public class FrameworkListener implements ServletContextListener {

	private Log logger = LogFactory.getLog(FrameworkListener.class);

	private FrameworkLauncher frameworkLauncher;

	public void contextDestroyed(ServletContextEvent sce) {

		try {
			frameworkLauncher.stop();
			frameworkLauncher.destroy();
		} catch (FrameworkException e) {
			logger.error("Riambsoft Framework filed to stop", e);
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext servletContext = sce.getServletContext();

		String frameworkLauncherClassParameter = servletContext
				.getInitParameter(FrameworkConstants.CONFIG_FRAMWWORK_LAUNCHER_CLASS);

		if (frameworkLauncherClassParameter != null)

			try {

				Class<?> frameworkLauncherClass = getClass().getClassLoader()
						.loadClass(frameworkLauncherClassParameter);

				frameworkLauncher = ((FrameworkLauncher) frameworkLauncherClass
						.newInstance());

			} catch (Exception e) {
				logger.error("Riambsoft Framework filed to start", e);
			}

		else {
			frameworkLauncher = new DefaultFrameworkLauncher();
		}
		servletContext.setAttribute(FrameworkConstants.RIAMBSOFT_FRAMEWORK_LAUNCHER,
				frameworkLauncher);
		try {
			frameworkLauncher.init(servletContext);
			frameworkLauncher.deploy();
			
			BundleContext buncleContext = frameworkLauncher.start();
			servletContext.setAttribute(FrameworkConstants.RIAMBSOFT_FRAMEWORK_BUNDLE_CONTEXT, buncleContext);
		} catch (FrameworkException e) {
			logger.error("Riambsoft Framework filed to start", e);
		}
	}

}
