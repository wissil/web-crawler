package com.scalablecapital.web.crawler.js.engine;


import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.inject.Inject;
import com.scalablecapital.web.crawler.config.WebCrawlerTestBase;


public class JSLibraryFinderTest extends WebCrawlerTestBase {

	@Inject
	private JSLibraryFinder libFinder;
	
	@Test
	public void singleUrlTest() throws IOException {
		final String url = 
				"https://helloinsight.org/?ref=madewithvuejs.com";
		
		final Set<String> libs = libFinder.findLibraries(url);
		
		assertFalse(libs.isEmpty());
	}
	
	@Test
	public void multipleUrlsTest() throws IOException, InterruptedException {		
		final List<String> urls = Arrays.asList(
				"https://en.wikipedia.org/wiki/IPhone_X",
				"https://en.wikipedia.org/wiki/Apple_A11",
				"https://www.devicespecifications.com/en/model-cpu/36ea45ae",
				"https://www.gsmarena.com/apple_iphone_x-8858.php",
				"https://www.ubergizmo.com/products/lang/en_us/devices/iphone-x/",
				"https://browser.geekbench.com/ios_devices/52",
				"https://gadgets.ndtv.com/apple-iphone-x-4258",
				"https://www.smartphonehrvatska.com/2018/02/07/apple-iphone-x-iphone-8-i-8-plus-ne-trebaju-cpu-throttling/",
				"https://www.forbes.com/sites/gordonkelly/2018/09/15/apple-iphone-xs-vs-iphone-x-whats-the-difference-upgrade/",
				"https://www.forbes.com/sites/gordonkelly/2018/09/15/apple-iphone-xs-vs-iphone-x-whats-the-difference-upgrade/",
				"https://bgr.com/2018/10/31/iphone-x-speed-throttling-may-occur-as-the-battery-gets-old/",
				"https://www.youtube.com/watch?v=IoaEY1-BNpU",
				"https://www.youtube.com/watch?v=P1gPP3Kh3DM",
				"https://www.imore.com/why-apple-winning-silicon",
				"https://wccftech.com/iphone-8-plus-iphone-x-get-cpu-throttling-feature/",
				"https://www.theverge.com/circuitbreaker/2018/10/31/18047174/iphone-x-8-plus-performance-throttling-battery-management-ios-12-1-update",
				"https://www.trustedreviews.com/news/apple-adds-controversial-cpu-throttling-iphone-x-iphone-8-3613582",
				"https://www.cnet.com/news/iphone-xs-a12-bionic-chip-is-industry-first-7nm-cpu/",
				"https://www.macworld.com/article/3308541/iphone-ipad/iphone-xs-iphone-xs-max-benchmark-results.html",
				"https://www.imore.com/why-apple-winning-silicon",
				"https://www.apple.com/iphone-xs/a12-bionic/",
				"https://www.macworld.co.uk/review/iphone/iphone-x-vs-samsung-note-9-3682130/",
				"https://www.zdnet.com/article/iphone-x-gets-speed-throttling-months-after-apple-said-it-probably-doesnt-need-it/",
				"https://www.digitaltrends.com/mobile/apple-iphone-xs-vs-apple-iphone-x/",
				"https://www.theiphonewiki.com/wiki/List_of_iPhones",
				"http://ascii.jp/elem/000/001/748/1748092/",
				"https://gizmodo.com/iphone-xs-guts-report-how-fast-is-the-a12-processor-a-1829388773",
				"https://www.ikream.com/2018/12/iphone-x-heating-fast-overheating-troubleshooting-guide-27382",
				"https://www.gadgetsnow.com/compare-mobile-phones/Apple-iPhone-8-Plus-vs-Apple-iPhone-X",
				"https://www.anandtech.com/show/13392/the-iphone-xs-xs-max-review-unveiling-the-silicon-secrets",
				"https://www.mp-elektronika.hr/apple-iphone-x-64gb",
				"https://everymac.com/systems/apple/iphone/iphone-faq/iphone-processor-types.html",
				"https://www.racunalo.com/usporedilica/samsung-galaxy-s9plus-sd845-vs-apple-iphone-x/",
				"https://mashable.com/2017/09/14/inside-apple-a11-bionic-and-silicon-team/",
				"https://newatlas.com/apple-comparison-2018-iphone-x-xr-xs-xsmax-8-7-plus/56362/",
				"https://www.techradar.com/news/best-iphone",
				"https://www.airtel.in/onlinestore/iphone-x",
				"https://www.myistore.co.za/discover-iphone/iphone-x-info",
				"https://www.ifixit.com/Teardown/iPhone+X+Teardown/98975",
				"https://www.aliexpress.com/w/wholesale-cpu-iphone-x.html",
				"https://hothardware.com/news/apple-a11-bionic-processor-crushes-challengers-benchmark-leak",
				"https://arstechnica.com/gadgets/2018/10/iphone-xs-and-xs-max-review-big-screens-big-performance-big-lenses-big-prices/",
				"https://www.androidauthority.com/why-are-apples-chips-faster-than-qualcomms-gary-explains-802738/",
				"https://9to5mac.com/2017/09/10/iphone-x-processor-wireless-charging-more/",
				"http://www.iphonehacks.com/2017/09/iphone-x-benchmarks-monster-cpu.html",
				"https://techinsights.com/about-techinsights/overview/blog/apple-iphone-x-teardown/",
				"https://qz.com/1263505/the-iphone-x-and-the-samsung-galaxy-s9-are-pretty-much-the-same-phone/",
				"http://blakespot.com/ios_device_specifications_grid.html",
				"https://deviceatlas.com/blog/how-deviceatlas-detects-iphone-models",
				"https://www.quora.com/Who-makes-processors-for-iPhone",
				"https://www.wired.com/story/iphone-xs-and-xs-max/",
				"https://ifixit.org/blog/9419/iphone-x-wallpapers-internals/",
				"https://www.pcmag.com/article/359419/samsung-galaxy-s9-vs-iphone-x-flagship-phones-compared",
				"https://www.phonearena.com/news/Apple-iPhone-X-A11-chip-six-processor-cores-powerful_id97910",
				"https://www.igeeksblog.com/how-to-find-iphone-x-modem/",
				"https://www.macrumors.com/roundup/iphone-x/",
				"https://www.reddit.com/r/TheSilphRoad/comments/7gu85v/cpu_analysis_of_current_update_on_ios_11_and/",
				"https://www.techadvisor.co.uk/review/iphones/iphone-x-review-3663926/",
				"https://www.gta.net/wireless/phones-and-tablets/iphone/iphone-x",
				"http://www.i-runway.com/blog/iphone-x-its-oled-going-behind-the-screen/",
				"http://barefeats.com/ipad-pro-versus-13-inch-macbook-pro.html",
				"https://www.telus.com/en/mobility/phones/iphone-x",
				"https://apple.hdblog.it/2017/09/12/iphone-x-primo-benchmark-apple-a11/",
				"https://www.iphonelife.com/content/how-to-turn-throttling-iphone-ios-113",
				"https://www.vodafone.co.uk/brands/apple/iphone-x/",
				"https://www.wired.co.uk/article/iphone-x-vs-iphone-7-vs-iphone-8",
				"https://www.guidingtech.com/a11-bionic-chip-vs-snapdragon-845-comparison/",
				"https://www.notebookcheck.net/The-iPhone-XS-has-a-smaller-battery-capacity-than-the-iPhone-X.333432.0.html",
				"https://www.computerworld.com/article/3235140/apple-ios/apples-face-id-the-iphone-xs-facial-recognition-tech-explained.html",
				"https://www.gadget.ro/iphone-xs-un-iphone-x-evoluat-la-nivel-de-cpu-si-gpu/",
				"https://www.iphoned.nl/iphone-x/specs/",
				"https://www.amazon.in/Apple-iPhone-Space-Grey-Storage/dp/B072LPF91D",
				"https://www.idownloadblog.com/2017/09/11/iphone-x-a11-six-cores/",
				"https://www.pocket-lint.com/phones/buyers-guides/142229-apple-iphone-x-vs-iphone-8-plus-vs-iphone-8-what-s-the-difference",
				"https://www.tenorshare.com/iphone-x-tips/fix-iphone-x-gets-hot-and-battery-drains.html",
				"http://socialcompare.com/en/comparison/apple-iphone-product-line-comparison",
				"https://www.t-mobile.at/iphone-x/",
				"https://www.digit.in/mobile-phones/iphonex-vs-apple-iphone-xr-compare-67235.html?pid=67235-137189-0-0",
				"https://ios.gadgethacks.com/news/everything-you-need-know-about-iphone-xs-max-0187281/",
				"https://www.sprint.com/en/shop/cell-phones/apple-iphone-x.html",
				"https://www.redmondpie.com/iphone-x-iphone-8-a11-bionic-chip-beats-ipad-pros-a10x-fusion-on-par-with-2017-macbook-pro/",
				"https://www.techrepublic.com/article/how-to-turn-off-battery-throttling-on-your-iphone/",
				"https://oreteki-design.com/iphone_x-cpu-a11_bionic",
				"https://www.bloomberg.com/graphics/2018-apple-custom-chips/",
				"https://www.firstpost.com/tech/news-analysis/the-apple-iphone-x-all-new-a11-bionic-chip-smokes-past-androids-best-a-closer-look-reveals-why-4039065.html",
				"https://japanese.engadget.com/2018/09/12/iphone-xs-max-xs-x-cpu-512gb/",
				"https://www.iphonebenchmark.net/cpumark_chart.html",
				"http://www.antutu.com/en/ranking/ios1.htm",
				"https://www.knowyourmobile.com/mobile-phones/iphone-x/25026/iphone-x-deals-no-upfront-cost",
				"https://books.google.hr/books?id=7ARTDwAAQBAJ&pg=PA44&lpg=PA44&dq=iphone+x+cpu&source=bl&ots=lZmJH_YtCm&sig=OXmHzvvGALSw_HVJLfdvOZEWXNc&hl=hr&sa=X&ved=2ahUKEwjMt9KO7bvfAhUQ-6wKHUu2C5k4WhDoATACegQICBAB",
				"http://uk.businessinsider.com/a11-bionic-iphone-x-more-powerful-than-a-2017-macbook-pro-2017-9",
				"https://www.howtogeek.com/342185/how-to-disable-your-iphones-cpu-throttling-in-ios-11.3/",
				"https://www.v3.co.uk/v3-uk/news/3017218/apple-releases-gbp999-iphone-x-with-face-id-facial-recognition-and-a11-bionic-cpu",
				"http://theconversation.com/never-mind-the-iphone-x-battery-life-could-soon-take-a-great-leap-forward-83901",
				"https://www.computerbild.de/artikel/cb-Tests-Handy-Apple-iPhone-X-17841777.html",
				"https://www.dailystar.co.uk/tech/news/664030/Samsung-Galaxy-S9-vs-iPhone-X-news-Apple-new-leak-Release-Date-Price-Specs-CPU",
				"https://books.google.hr/books?id=4kyJSmD0G40C&pg=PA205&lpg=PA205&dq=iphone+x+cpu&source=bl&ots=UmpIbjxqXV&sig=VavFzQGhWzceq0yFSiXTEquyARM&hl=hr&sa=X&ved=2ahUKEwjMt9KO7bvfAhUQ-6wKHUu2C5k4WhDoATAJegQIABAB",
				"https://www.xataka.com/analisis/iphone-x-analisis-caracteristicas-precio-y-especificaciones",
				"http://osxdaily.com/",
				"https://books.google.hr/books?id=6kYrQtVgHLIC&pg=PT1637&lpg=PT1637&dq=iphone+x+cpu&source=bl&ots=1iYFbhTvMD&sig=xeYVUgaRf3lWNUCZRGHXoxptFcY&hl=hr&sa=X&ved=2ahUKEwjUgZSQ7bvfAhVOR6wKHXkCA5Y4ZBDoATACegQICBAB",
				"https://www.notebookcheck.com/Patentstreit-iPhone-X-darf-in-Deutschland-nicht-mehr-verkauft-werden.384921.0.html",
				"https://fptshop.com.vn/dien-thoai/apple-iphone",
				"https://books.google.hr/books?id=qs6Y3hRWR3MC&pg=SL57-PA57&lpg=SL57-PA57&dq=iphone+x+cpu&source=bl&ots=SVfzM1g-IT&sig=e9bnVEojEhPmPcnzMYJs4I7ku5c&hl=hr&sa=X&ved=2ahUKEwjUgZSQ7bvfAhVOR6wKHXkCA5Y4ZBDoATAFegQICRAB",
				"https://www.lifeproof.com/",
				"https://www.smartprix.com/mobiles/apple-brand",
				"https://www.iculture.nl/iphone/iphone-xs/",
				"http://mondo.rs/a1155192/Mob-IT/Vesti/iPhone-No-Service-problem-iOS-12.1.2-update-iOS-update-greska-iPhone-bez-signala-iOS-12.1.2.html",
				"https://www.idropnews.com/iphone-x/iphone-x-plus-a12-geekbench-scores-leak-unrivaled-speed/75715/",
				"https://www.eurogamer.net/articles/digitalfoundry-2018-how-epic-games-runs-fortnite-at-60fps-on-iphone",
				"https://www.bestbuy.com/site/help-topics/return-exchange-policy/pcmcat260800050014.c?id=pcmcat260800050014",
				"https://medium.com/s/the-upgrade/the-golden-age-of-the-iphone-is-ending-15f673ba70d8",
				"https://news.mydrivers.com/1/608/608823.htm",
				"http://ascon.com.br/0sbq33l/y0jdysr.php?wpmachyaw=144hz-difference-between-iphone",
				"https://www.iemoji.com/",
				"https://www.hachi.tech/",
				"http://www.janpara.co.jp/",
				"https://shadow.tech/",
				"https://www.macwelt.de/a/systembremse-erklaert-wenn-kernel-task-das-system-bremst,3438750",
				"https://www.ixbt.com/news/2018/12/24/amd-cpu-apu-ryzen-3000.html",
				"https://www.amazon.com/Apple-MMEF2AM-AirPods-Wireless-Bluetooth/dp/B01MQWUXZS",
				"https://www.bhphotovideo.com/c/buy/smartphones/ci/24039/N/3955685938",
				"https://emarket.do/",
				"https://slickdeals.net/article/buying-guide/blu-vivo-xi-plus-smartphone-review/",
				"https://www.golem.de/news/cpus-mit-igpu-amd-bringt-athlon-220ge-und-athlon-240ge-1812-128674.html",
				"https://www.t3.com/news/amazon-deals-5-fire-tablets-get-huge-discounts-in-boxing-day-sales",
				"https://gr.gizchina.com/2018/12/25/asus-zenfone-5-new-flash-sale-now/",
				"https://www.gravis.de/",
				"http://www.provalia-capital.com/zuenyrs/rqvvyrc.php?wpmachyaw=phone-clone",
				"https://www.skinit.com/",
				"https://blende.com.br/s4ueeo7/eoumfyp.php?wpmachyaw=identify-ipsw",
				"http://miditecnica.com/ekhtusf/bw5qvuv.php?wpmachyaw=tedata-dns-iphone"
		);
		
		final long start = System.currentTimeMillis();
		// assert multiple at once
		final Map<String, Integer> occurrences = 
				libFinder.findLibraries(urls, 5, TimeUnit.SECONDS);
		
		final long end = System.currentTimeMillis();
		
		System.out.println("Duration: " + (end - start) + " ms");
				
		occurrences
			.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
//			.limit(5)
			.forEach(e -> {
				System.out.println(String.format("Lib=%s, n=%s", e.getKey(), e.getValue()));
			});
	}
}
