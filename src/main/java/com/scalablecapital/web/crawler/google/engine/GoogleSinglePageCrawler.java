package com.scalablecapital.web.crawler.google.engine;

import java.io.IOException;
import java.util.List;

/**
 * An interface that represents a crawler over a single Google page.
 *
 * @author wissil
 */
public interface GoogleSinglePageCrawler {

	/**
	 * Crawls over a single <b>Google</b> page at number <code>pageNumber</code>,
	 * and extracts the main URLs from it.
	 * 
	 * @param searchTerm Term that <b>Google</b> was queried for.
	 * @param pageNumber <b>Google</b> page number.
	 * 
	 * @return List of {@link GoogleSearchResult} from the <b>Google</b> page 
	 * numbered <code>pageNumber</code> for the <code>searchTerm</code>.
	 * If no results were found on the given <code>pageNumber</code>, an empty list is returned.
	 * 
	 * @throws IOException 
	 */
	List<String> crawl(String searchTerm, int pageNumber) throws IOException;
	
}
