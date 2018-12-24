package com.scalablecapital.web.crawler.engine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.scalablecapital.web.crawler.model.GoogleSearchResult;

/**
 * Crawler over Google pages.
 * 
 * @author wissil
 *
 */
public interface GoogleCrawler {

	/**
	 * Crawls over all <b>Google</b> pages for a given <code>searchTerm</code>,
	 * and retrieves the {@link GoogleSearchResult} objects from it.
	 * 
	 * @param searchTerm Term that <b>Google</b> was queried for.
	 * 
	 * @return List of {@link GoogleSearchResult} from the <b>Google</b> page 
	 * for the <code>searchTerm</code>.
	 * If no results were found, an empty list is returned.
	 * 
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	List<GoogleSearchResult> crawl(String searchTerm) throws IOException, InterruptedException, ExecutionException;
}
