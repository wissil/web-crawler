package com.scalablecapital.web.crawler.js.engine;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.google.inject.Inject;
import com.scalablecapital.web.crawler.config.WebCrawlerTestBase;
import com.scalablecapital.web.crawler.js.model.JsLib;

import static com.scalablecapital.web.crawler.js.model.JsLib.HOTJAR;
import static com.scalablecapital.web.crawler.js.model.JsLib.JQUERY;
import static com.scalablecapital.web.crawler.js.model.JsLib.VUE;


public class JSLibraryFinderTest extends WebCrawlerTestBase {

	@Inject
	private JSLibraryFinder libFinder;
	
	@Test
	public void test() throws IOException {
		final String url = 
				"https://helloinsight.org/?ref=madewithvuejs.com";
		
		final Set<JsLib> libs = libFinder.findLibraries(url);
		
		assertTrue(containsOnly(libs, HOTJAR, JQUERY, VUE));
	}
	
	private static boolean containsOnly(Set<JsLib> actual, JsLib ...expected) {
		if (actual.size() != expected.length) return false;
		
		for (JsLib expectedLib : expected) {
			if (!actual.contains(expectedLib)) return false;
		}
		
		return true;
	}
}
