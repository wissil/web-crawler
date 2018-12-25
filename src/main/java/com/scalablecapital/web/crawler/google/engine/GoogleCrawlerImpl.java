package com.scalablecapital.web.crawler.google.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.scalablecapital.web.crawler.google.model.GoogleSearchResult;

public class GoogleCrawlerImpl implements GoogleCrawler {

	/** Number of pages concurrently downloaded. */
	private static final int N_PAGES_CONCURRENT = 5;

	private final ExecutorService executor;

	private final GoogleSinglePageCrawler pageCrawler;
	
	public GoogleCrawlerImpl(ExecutorService executor, GoogleSinglePageCrawler pageCrawler) {
		this.executor = Objects.requireNonNull(executor);
		this.pageCrawler = Objects.requireNonNull(pageCrawler);
	}

	@Override
	public List<GoogleSearchResult> crawl(String searchTerm) throws IOException, InterruptedException, ExecutionException {
		final List<GoogleSearchResult> results = Collections.synchronizedList(new ArrayList<>());

		boolean shouldContinue = true;

		int offset = 0;

		while (shouldContinue) {
			final int pageOffset = offset;

			// run the batch of tasks concurrently
			final List<Future<List<GoogleSearchResult>>> pageCrawlFutures = 
					executor.invokeAll(
							IntStream
								.range(0, N_PAGES_CONCURRENT)
								.map(pageNumber -> pageNumber + pageOffset)
								.mapToObj(pageNumber -> crawlPageCallable(searchTerm, pageNumber))
								.collect(Collectors.toList()));

			for (Future<List<GoogleSearchResult>> f : pageCrawlFutures) {
				// if any of the pages is empty -- break while
				final List<GoogleSearchResult> pageResults = f.get();
				
				System.out.println(pageResults.size());

				if (pageResults.isEmpty()) {
					// end of results has been reached
					shouldContinue = false;
					break;
				} else {
					// still has results left
					results.addAll(pageResults);
				}
			}

			offset += N_PAGES_CONCURRENT;
		}

		return Collections.unmodifiableList(results);
	}

	private Callable<List<GoogleSearchResult>> crawlPageCallable(String searchTerm, int pageNumber) {
		return new Callable<List<GoogleSearchResult>>() {

			@Override 
			public List<GoogleSearchResult> call() throws IOException {
				// unhandled exception if the page wasn't found
				// let it crash, no way to recover
				return pageCrawler.crawl(searchTerm, pageNumber);
			}
		};
	}
}
