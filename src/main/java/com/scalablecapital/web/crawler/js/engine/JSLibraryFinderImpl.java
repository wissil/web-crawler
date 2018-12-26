package com.scalablecapital.web.crawler.js.engine;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.scalablecapital.web.util.JsLibUtils;

public class JSLibraryFinderImpl implements JSLibraryFinder {

	private final String userAgent;

	private final ExecutorService executor;

	public JSLibraryFinderImpl(String userAgent, ExecutorService executor) {
		this.userAgent = Objects.requireNonNull(userAgent);
		this.executor = Objects.requireNonNull(executor);
	}

	@Override
	public Set<String> findLibraries(String url) throws IOException {
		final Document document = 
				Jsoup.connect(url).userAgent(userAgent).get();

		final Elements elements = document.select("script[src]");

		return elements
				.stream()
				.map(e -> e.attr("src"))
				.map(libUrl -> JsLibUtils.normalize(libUrl))
				.filter(lib -> !(lib.equals("main.js") || lib.equals("js.js")))
				.collect(Collectors.toSet());
	}

	@Override
	public Map<String, Integer> findLibraries(List<String> urls, long timeout, TimeUnit unit) throws IOException, InterruptedException {
		return parallel(urls, new ConcurrentHashMap<>(), timeout, unit);
	}

	private Map<String, Integer> parallel(List<String> urls, Map<String, Integer> libs, long timeout, TimeUnit unit) throws IOException, InterruptedException {
		final List<Future<Set<String>>> jsLibFindFutures = 
				executor.invokeAll(
						urls
						.stream()
						.map(url -> findLibCallable(url))
						.collect(Collectors.toList()),
				timeout, unit);

		for (Future<Set<String>> f : jsLibFindFutures) {
			Set<String> current = null;
			try {
				current = f.get();
			} catch (ExecutionException e) {
				// this page failed
				// TODO: log error
			} catch (CancellationException e) {
				// timeout reached, return what have so far
				return Collections.unmodifiableMap(libs);
			}

			if (current != null) {
				updateLibraries(libs, current);
			}
		}

		return Collections.unmodifiableMap(libs);
	}

	private static void updateLibraries(Map<String, Integer> libraries, Set<String> libsOnUrl) {
		libsOnUrl.forEach(lib -> libraries.merge(lib, 1, Integer::sum));
	}

	private Callable<Set<String>> findLibCallable(String url) {
		return new Callable<Set<String>>() {

			@Override 
			public Set<String> call() throws IOException {
				// unhandled exception if the page wasn't found
				// let it crash, no way to recover
				return findLibraries(url);
			}
		};
	}
}

