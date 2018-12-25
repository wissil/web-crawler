package com.scalablecapital.web.crawler.js.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.scalablecapital.web.crawler.config.WebCrawlerTestBase;

public class JsLibTest extends WebCrawlerTestBase {
	
	@Test
	public void hotJarTest() {
		final String libUrl =
				"https://script.hotjar.com/modules-a627940c905336c0645955040907df60.js";
		
		assertEquals(JsLib.HOTJAR, JsLib.fromUrl(libUrl));
	}
	
	@Test
	public void jQueryTest() {
		final String libUrl = 
				"http://scrumbucket.org/wp-includes/js/jquery/jquery.js?ver=1.12.4";
		
		assertEquals(JsLib.JQUERY, JsLib.fromUrl(libUrl));
	}
	
	@Test
	public void vueJsTest() {
		final String libUrl =
				"//cdn.ravenjs.com/3.26.2/vue/raven.min.js";
		
		assertEquals(JsLib.VUE, JsLib.fromUrl(libUrl));
	}
	
	@Test
	public void otherTest() {
		final String libUrl = 
				"https://www.google-analytics.com/analytics.js";
		
		assertEquals(JsLib.OTHER, JsLib.fromUrl(libUrl));
	}

}
