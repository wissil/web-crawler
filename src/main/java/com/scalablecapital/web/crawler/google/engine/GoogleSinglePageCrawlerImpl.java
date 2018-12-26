package com.scalablecapital.web.crawler.google.engine;

import static com.scalablecapital.web.util.WebQueryUtils.transformToQuery;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSinglePageCrawlerImpl implements GoogleSinglePageCrawler {
	
	private static final String GOOGLE_SEARCH_URL = 
			"https://www.google.com/search"	// base URL
			+ "?q=%s"						// search term			
			+ "&start=%s";					// page number
	
	private final String userAgent;
	
	public GoogleSinglePageCrawlerImpl(String userAgent) {
		this.userAgent = Objects.requireNonNull(userAgent);
	}
	
	private static final String SEARCH_ELEMENT_ID = "search";
	
	private static final int RESULTS_PER_PAGE = 10;

	@Override
	public List<String> crawl(String searchTerm, int pageNumber) throws IOException {
		final String query = transformToQuery(searchTerm);
		
		final String searchURL = 
				String.format(GOOGLE_SEARCH_URL, query, String.valueOf(pageNumber * RESULTS_PER_PAGE));
		
		final Document document = 
				Jsoup.connect(searchURL).userAgent(userAgent).get();
		
		return isEmptyResult(document) ?
						Collections.emptyList() :
						parsePage(document);
	}

	private static List<String> parsePage(Document document) {
		// div element with class "rc" > child div element with class "r"
		final Elements elements = document.select("div.rc > div.r");
		
		return elements
				.stream()
				.map(e -> parseURL(e))
				.collect(Collectors.toList());
	}
	
	private static String parseURL(Element e) {
		// main URL is the one without a class
		return e.select("a:not([class])").attr("href");
	}
	
	private static boolean isEmptyResult(Document doc) {
		return doc.getElementById(SEARCH_ELEMENT_ID).childNodeSize() == 0;
	}
}
