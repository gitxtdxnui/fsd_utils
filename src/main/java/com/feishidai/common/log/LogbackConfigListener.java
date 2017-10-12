package com.feishidai.common.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackConfigListener implements ServletContextListener {
	private static final String CONFIG_LOCATION = "logbackConfigLocation";

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	}

	private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener.class);

	public static void load(String logpath) {
		logger.info("loaded Logback configure file from {}", logpath);
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			loggerContext.reset();
			JoranConfigurator joranConfigurator = new JoranConfigurator();
			joranConfigurator.setContext(loggerContext);
			joranConfigurator.doConfigure(logpath);
		} catch (JoranException e) {
			e.printStackTrace();
			logger.error("can not loading Logback configure file from " + logpath, e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.debug("Destory Logback");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String logbackConfigLocation = event.getServletContext().getInitParameter(CONFIG_LOCATION);
		String fn = event.getServletContext().getRealPath(logbackConfigLocation);
		LogbackConfigListener.load(fn);
	}
}
