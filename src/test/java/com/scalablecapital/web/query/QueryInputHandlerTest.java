package com.scalablecapital.web.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueryInputHandlerTest {
	
	@Test
	public void singleTermTest() {
		final String input = "apple";
		final String query = WebQueryUtils.transformToQuery(input);
				
		assertEquals(input, query);
	}
	
	@Test
	public void multipleTermsTest_singleWhitespaces() {
		final String input = "apple is the best";
		final String query = WebQueryUtils.transformToQuery(input);
		
		final String expected = "apple+is+the+best";
		assertEquals(expected, query);
	}
	
	@Test
	public void multipleTermsTest_multipleWhitespaces() {
		final String input = "apple     is the   best";
		final String query = WebQueryUtils.transformToQuery(input);
		
		final String expected = "apple+is+the+best";
		assertEquals(expected, query);
	}
}
