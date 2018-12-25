package com.scalablecapital.web.crawler.js.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public enum JsLib {
	
	ANGULAR(Pattern.compile(".*(Angular|angular).*")),
	
	HOTJAR(Pattern.compile(".*(Hotjar|hotjar).*")),
	
	JQUERY(Pattern.compile(".*(jQuery|jquery).*")),

	VUE(Pattern.compile(".*(Vue|vue).*")),
		
	// regex that will never match: anything not followed by an x, followed by an x
	OTHER(Pattern.compile("(?!x)x"));
	
	final Pattern pattern;
	
	JsLib(Pattern pattern) {
		this.pattern = Objects.requireNonNull(pattern);
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public static JsLib fromUrl(String libUrl) {
		return Arrays
				.stream(JsLib.values())
				.filter(lib -> lib.getPattern().matcher(libUrl).matches())
				.findFirst()
				.orElse(OTHER);
	}
}
