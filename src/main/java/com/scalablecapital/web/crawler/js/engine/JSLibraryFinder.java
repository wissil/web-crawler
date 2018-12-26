package com.scalablecapital.web.crawler.js.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public interface JSLibraryFinder {

	/**
	 * Finds all the JavaScript libraries used on the website
	 * under the given <code>url</code>.
	 * 
	 * @param url URL of the website of interest.
	 * @return List of JavaScript libraries used on the website under the given
	 * <code>url</code>.
	 * @throws IOException 
	 */
	Set<String> findLibraries(String url) throws IOException;
	
	/**
	 * Finds all the JavaScript libraries used on websites
	 * under the given <code>urls</code>.
	 * 
	 * @param urls URLs of the websites of interest.
	 * @return Map of JavaScript libraries used on websites under the given
	 * <code>urls</code> mapped to the number of their occurrences.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	Map<String, Integer> findLibraries(List<String> urls, long timeout, TimeUnit unit) throws IOException, InterruptedException;
}
