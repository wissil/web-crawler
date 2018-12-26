package com.scalablecapital.web.crawler.app;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.scalablecapital.web.crawler.config.WebCrawlerModule;
import com.scalablecapital.web.crawler.google.engine.GoogleCrawler;
import com.scalablecapital.web.crawler.js.engine.JSLibraryFinder;

public class WebCrawler {

	private static final int TOP_N_LIBS = 5;
	
	/** Max time to crawl Google pages. */
	private static final long TIMEOUT_GOOGLE_S = 10;
	
	/** Max time to crawl pages finding JS libs. */
	private static final long TIMEOUT_JS_S = 30;

	@Inject
	private GoogleCrawler googleCrawler;

	@Inject
	private JSLibraryFinder libFinder;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		final Injector injector = Guice.createInjector(new WebCrawlerModule());
		final WebCrawler crawler = injector.getInstance(WebCrawler.class);
		crawler.run();
	}

	private void run() throws IOException, InterruptedException, ExecutionException {
		System.out.println("Program has started!");

		final Scanner input = new Scanner(System.in);

		// 1) get input from the user
		System.out.print("Enter your search term: ");
		final String searchTerm = input.nextLine();
		input.close();

		System.out.println("\nCrawling Google pages ...");
		// 1) get google pages
		final List<String> urls = 
				googleCrawler.crawl(searchTerm, TIMEOUT_GOOGLE_S, TimeUnit.SECONDS);

		System.out.println(String.format("Found %d URLs!", urls.size()));

		System.out.println("\nExtracting JavaScript libraries from the URLs ...");
		// 2) get libraries
		final Map<String, Integer> libs = 
				libFinder.findLibraries(urls, TIMEOUT_JS_S, TimeUnit.SECONDS);

		System.out.println("Results...\n");
		
		libs
			.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
			.limit(TOP_N_LIBS)
			.forEach(e -> System.out.println(String.format("Library=%s, n=%s", e.getKey(), e.getValue())));
		
		// kill if any thread is still running
		System.exit(1);
	}
}
