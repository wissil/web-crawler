package com.scalablecapital.web.crawler.google.engine;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.inject.Inject;
import com.scalablecapital.web.crawler.config.WebCrawlerTestBase;
import com.scalablecapital.web.crawler.google.engine.GoogleSinglePageCrawler;
import com.scalablecapital.web.crawler.google.model.GoogleSearchResult;

public class GoogleSinglePageCrawlerTest extends WebCrawlerTestBase {

	@Inject
	private GoogleSinglePageCrawler crawler;
	
	@Test
	public void crawlSinglePageTest() throws IOException {
		final String searchTerm = "iphone x cpu";
		final int pageNumber = 0;
		
		final List<GoogleSearchResult> results = 
				crawler.crawl(searchTerm, pageNumber);
				
		// expect results
		assertFalse(results.isEmpty());
	}
}
