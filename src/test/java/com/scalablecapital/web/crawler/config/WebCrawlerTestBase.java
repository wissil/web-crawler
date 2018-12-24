package com.scalablecapital.web.crawler.config;

import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WebCrawlerTestBase {

	private final Injector injector = 
			Guice.createInjector(new WebCrawlerModule());
	
	@Before
	public void prepareGuice() {
		injector.injectMembers(this);
	}
}
