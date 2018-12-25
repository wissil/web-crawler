package com.scalablecapital.web.crawler.js.engine;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.scalablecapital.web.crawler.js.model.JsLib;

public class JSLibraryFinderImpl implements JSLibraryFinder {
	
	private final String userAgent;
	
	public JSLibraryFinderImpl(String userAgent) {
		this.userAgent = Objects.requireNonNull(userAgent);
	}

	@Override
	public Set<JsLib> findLibraries(String url) throws IOException {
		final Document document = 
				Jsoup.connect(url).userAgent(userAgent).get();
		
		final Elements elements = document.select("script[src]");
				
		return elements
					.stream()
					.map(e -> e.attr("src"))
					.map(libUrl -> JsLib.fromUrl(libUrl))
					.filter(lib -> !lib.equals(JsLib.OTHER))
					.collect(Collectors.toSet());
	}

}

