package com.scalablecapital.web.query;

public class WebQueryUtils {
	
	private static final String QUERY_JOIN_SYMBOL = "+";
	
	private static final String ANY_WHITESPACE = "\\s+";
	
	private WebQueryUtils() {
		// shouldn't be able to instantiate
	}

	/**
	 * Transforms the given <code>input</code> into the
	 * format suitable to make a web query.
	 * 
	 * @param input
	 * @return the given <code>input</code> string in the format of
	 * a web query.
	 */
	public static String transformToQuery(String input) {
		return input.replaceAll(ANY_WHITESPACE, QUERY_JOIN_SYMBOL);
	}

}
