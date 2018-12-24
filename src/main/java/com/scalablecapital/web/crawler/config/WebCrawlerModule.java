package com.scalablecapital.web.crawler.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.scalablecapital.web.crawler.engine.GoogleCrawler;
import com.scalablecapital.web.crawler.engine.GoogleCrawlerImpl;
import com.scalablecapital.web.crawler.engine.GoogleSinglePageCrawler;
import com.scalablecapital.web.crawler.engine.GoogleSinglePageCrawlerImpl;

public class WebCrawlerModule extends AbstractModule {

	private static final int N_THREADS = 50;

	@Provides
	@Singleton
	ExecutorService executorService() {
		return Executors.newFixedThreadPool(N_THREADS);
	}
	
	@Provides
	@Singleton
	GoogleSinglePageCrawler singlePageCrawler() {
		return new GoogleSinglePageCrawlerImpl();
	}
	
	@Inject
	@Provides
	@Singleton
	GoogleCrawler crawler(
			ExecutorService executor, 
			GoogleSinglePageCrawler pageCrawler) {
		return new GoogleCrawlerImpl(executor, pageCrawler);
	}
}
