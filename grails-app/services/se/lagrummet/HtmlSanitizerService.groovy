package se.lagrummet

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class HtmlSanitizerService {

	public String cleanHtml(String html) {
		Document doc = Jsoup.parse(html)
		doc.outputSettings().prettyPrint(false)

		doc.select("base").remove()
		doc.select("head").remove()
		
		return doc.body().html()
	}
	
	public String stripTags(String html, List<String> tagsToRemove) {
		Document doc = Jsoup.parse(html)
		doc.outputSettings().prettyPrint(false)		
		
		tagsToRemove.each { tag ->
			doc.select(tag).unwrap()
		}

		return doc.body().html()
	}
}
