package com.scalablecapital.web.crawler.model;

import java.util.Objects;

/**
 * Represents a single search result from <b>Google</b>.
 * 
 * @author wissil
 *
 */
public class GoogleSearchResult {

	private final String title;
	
	private final String url;
	
	public GoogleSearchResult(String title, String url) {
		this.title = Objects.requireNonNull(title);
		this.url = Objects.requireNonNull(url);
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "GoogleSearchResult [title=" + title + ", url=" + url + "]";
	}
}
