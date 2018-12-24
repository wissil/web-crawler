package com.scalablecapital.web.crawler.engine;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.inject.Inject;
import com.scalablecapital.web.crawler.config.WebCrawlerTestBase;
import com.scalablecapital.web.crawler.model.GoogleSearchResult;

public class GoogleCrawlerTest extends WebCrawlerTestBase {

	@Inject
	private GoogleCrawler crawler;
	
	@Test
	public void crawlAllResultsTest() throws IOException, InterruptedException, ExecutionException {
		final String searchTerm =  "iphone x cpu";		
		final List<GoogleSearchResult> results = crawler.crawl(searchTerm);
				
		// should containt non-empty results
		assertFalse(results.isEmpty());
	}
}
