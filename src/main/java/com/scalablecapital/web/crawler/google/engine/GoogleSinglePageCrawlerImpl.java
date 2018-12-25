package com.scalablecapital.web.crawler.google.engine;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scalablecapital.web.crawler.google.model.GoogleSearchResult;

import static com.scalablecapital.web.query.WebQueryUtils.transformToQuery;

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
	public List<GoogleSearchResult> crawl(String searchTerm, int pageNumber) throws IOException {
		final String query = transformToQuery(searchTerm);
		
		final String searchURL = 
				String.format(GOOGLE_SEARCH_URL, query, String.valueOf(pageNumber * RESULTS_PER_PAGE));
		
		final Document document = 
				Jsoup.connect(searchURL).userAgent(userAgent).get();
		
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
