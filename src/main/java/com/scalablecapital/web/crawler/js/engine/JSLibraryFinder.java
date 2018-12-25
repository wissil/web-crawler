package com.scalablecapital.web.crawler.js.engine;

import java.io.IOException;
import java.util.Set;

import com.scalablecapital.web.crawler.js.model.JsLib;

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
	Set<JsLib> findLibraries(String url) throws IOException;
}
