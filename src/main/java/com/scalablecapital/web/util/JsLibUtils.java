package com.scalablecapital.web.util;

public class JsLibUtils {

	private JsLibUtils() {
		// shouldn't be able to instantiate
	}
	
	/**
	 * Normalize the <code>url</code> that represents the 
	 * JavaScript library.
	 * 
	 * This method is used for the deduplication of the same libraries.
	 * 
	 * @param url
	 * @return normalized JavaScript library name.
	 */
	public static String normalize(String url) {
		// get only the last part of the URL representing the library
		url = url.substring(url.lastIndexOf("/") + 1);
		
		// get only the first part of the library, to treat the same examples like:
		// jquery.js and jquery.min.js, jquery.js?version=1.1
		if (url.contains("?")) url = url.substring(0, url.indexOf("?"));
		if (url.contains(".")) url = url.substring(0, url.indexOf("."));
		if (url.contains("-")) url = url.substring(0, url.indexOf("-"));
				
		return String.format("%s.js", url.trim());
	}
}
