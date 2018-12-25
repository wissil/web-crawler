package com.scalablecapital.web.crawler.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.scalablecapital.web.crawler.google.engine.GoogleCrawler;
import com.scalablecapital.web.crawler.google.engine.GoogleCrawlerImpl;
import com.scalablecapital.web.crawler.google.engine.GoogleSinglePageCrawler;
import com.scalablecapital.web.crawler.google.engine.GoogleSinglePageCrawlerImpl;
import com.scalablecapital.web.crawler.js.engine.JSLibraryFinder;
import com.scalablecapital.web.crawler.js.engine.JSLibraryFinderImpl;

public class WebCrawlerModule extends AbstractModule {
	
	/** Google Chrome user agent. */
	private static final String USER_AGENT = 
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) "
					+ "Chrome/70.0.3538.77 Safari/537.36";

	@Provides
	@Singleton
	ExecutorService executorService() {
		final int N_THREADS = 50;
		return Executors.newFixedThreadPool(N_THREADS);
	}
	
	@Provides
	@Singleton
	GoogleSinglePageCrawler singlePageCrawler() {
		return new GoogleSinglePageCrawlerImpl(USER_AGENT);
	}
	
	@Inject
	@Provides
	@Singleton
	GoogleCrawler crawler(
			ExecutorService executor, 
			GoogleSinglePageCrawler pageCrawler) {
		return new GoogleCrawlerImpl(executor, pageCrawler);
	}
	
	@Provides
	@Singleton
	JSLibraryFinder jsLibFinder() {
		return new JSLibraryFinderImpl(USER_AGENT);
	}
}
