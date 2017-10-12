package com.feishidai.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (ApplicationUtil.applicationContext == null) {
			ApplicationUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() throws BeansException {
		return ApplicationUtil.applicationContext;
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}
}
