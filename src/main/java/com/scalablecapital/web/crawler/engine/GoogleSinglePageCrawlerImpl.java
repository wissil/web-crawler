package com.scalablecapital.web.crawler.engine;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scalablecapital.web.crawler.model.GoogleSearchResult;

import static com.scalablecapital.web.query.WebQueryUtils.transformToQuery;

public class GoogleSinglePageCrawlerImpl implements GoogleSinglePageCrawler {
	
	/** Google Chrome user agent. */
	private static final String USER_AGENT = 
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) "
					+ "Chrome/70.0.3538.77 Safari/537.36";

	private static final String GOOGLE_SEARCH_URL = 
			"https://www.google.com/search"	// base URL
			+ "?q=%s"						// search term			
			+ "&start=%s";					// page number
	
	private static final String SEARCH_ELEMENT_ID = "search";
	
	private static final int RESULTS_PER_PAGE = 10;

	@Override
	public List<GoogleSearchResult> crawl(String searchTerm, int pageNumber) throws IOException {
		final String query = transformToQuery(searchTerm);
		
		final String searchURL = 
				String.format(GOOGLE_SEARCH_URL, query, String.valueOf(pageNumber * RESULTS_PER_PAGE));
		
		final Document document = 
				Jsoup.connect(searchURL).userAgent(USER_AGENT).get();
		
		return isEmptyResult(document) ?
						Collections.emptyList() :
						parsePage(document);
	}

	private static List<GoogleSearchResult> parsePage(Document document) {
		// div element with class "rc" > child div element with class "r"
		final Elements elements = document.select("div.rc > div.r");
		
		return elements
				.stream()
				.map(e -> new GoogleSearchResult(parseTitle(e), parseURL(e)))
				.collect(Collectors.toList());
	}
	
	private static String parseURL(Element e) {
		// main URL is the one without a class
		return e.select("a:not([class])").attr("href");
	}
	
	private static String parseTitle(Element e) {
		// main title is the one under h3 tag
		return e.select("h3").text();
	}
	
	private static boolean isEmptyResult(Document doc) {
		return doc.getElementById(SEARCH_ELEMENT_ID).childNodeSize() == 0;
	}
}
