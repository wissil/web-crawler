package com.scalablecapital.web.crawler.google.engine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Crawler over Google pages.
 * 
 * @author wissil
 *
 */
public interface GoogleCrawler {

	/**
	 * Crawls over all <b>Google</b> pages for a given <code>searchTerm</code>,
	 * and retrieves the main URLs from it.
	 * 
	 * @param searchTerm Term that <b>Google</b> was queried for.
	 * 
	 * @return List of main URLs from the <b>Google</b> page 
	 * for the <code>searchTerm</code>.
	 * If no results were found, an empty list is returned.
	 * 
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	List<String> crawl(String searchTerm, long timeout, TimeUnit unit) throws IOException, InterruptedException, ExecutionException;
}
