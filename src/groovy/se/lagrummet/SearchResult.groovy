package se.lagrummet

class SearchResult {

	Long itemsPerPage = 50
	Long startIndex = 0
	Long totalResults = 0
	def totalResultsPerCategory = [:]
	Long maxItemsPerCategory = 5
	def errorMessages = []
	
	def items = [:]
	
	public void addItemByType(SearchResultItem item) {
		def type = item.type
		def category = getCategoryForType(type)
		if(!items[(category)]) {
			items[(category)] = []
		}
		if(items[(category)].size() < maxItemsPerCategory) {
			items[(category)].add(item)
		}
		if(!totalResultsPerCategory[(category)]) {
			totalResultsPerCategory[(category)] = 0
		}
		totalResultsPerCategory[(category)] += 1
	}
	
	private String getCategoryForType(String type) {
		//Lagar, Rattsfall, Propositioner, Utredningar, Ovrigt
		return typeToCategory[(type)] ?: Category.OKAND
		
	}
	
	public SearchResult mergeWith(SearchResult other) {
		if(!other) {
			return this
		}
		this.totalResults += other.totalResults
		
		other.items.each { category, otherItems ->
			if(!items[(category)]) {
				items[(category)] = []
			}
			items[(category)].addAll(otherItems)
		}
		
		other.totalResultsPerCategory.each { category, otherCount ->
			if(!totalResultsPerCategory[(category)]) {
				totalResultsPerCategory[(category)] = 0
			}
			totalResultsPerCategory[(category)] += otherCount
		}
		
		errorMessages.addAll(other.errorMessages)
		
		return this
	}
	
	public static enum Category {
		RATTSFALL("Rattsfall"), LAGAR("Lagar"), PROPOSITIONER("Propositioner"), UTREDNINGAR("Utredningar"), OVRIGT("Ovrigt"), OKAND("Okand")
		private final String title
		Category(String title) { this.title = title }
		public String toString() { return title }
	}
	
	static typeToCategory = [
		'Rattsfallsnotis' : Category.RATTSFALL,
		'Rattsfallspublikation' : Category.RATTSFALL,
		'Rattsfallsrapport' : Category.RATTSFALL,
		'Rattsfallsreferat' : Category.RATTSFALL,
		'VagledandeAvgorande' : Category.RATTSFALL,
		'VagledandeDomstolsavgorande' : Category.RATTSFALL,
		'VagledandeMyndighetsavgorande' : Category.RATTSFALL,
		
		'Forfattning' : Category.LAGAR,
		'Forfattningsreferens' : Category.LAGAR,
		'Forfattningssamling' : Category.LAGAR,
		'FSDokument' : Category.LAGAR,
		'Forordning' : Category.LAGAR,
		'Grundlag' : Category.LAGAR,
		'KonsolideradGrundforfattning' : Category.LAGAR,
		'Paragraf' : Category.LAGAR,
		'Lag' : Category.LAGAR,
		'Myndighetsforeskrift' : Category.LAGAR,
		
		'Proposition' : Category.PROPOSITIONER,
		
		'Kommittedirektiv' : Category.UTREDNINGAR,
		'Utredningsbetankande' : Category.UTREDNINGAR,
		'Utredningsserie' : Category.UTREDNINGAR,
		
		'Lagrummet.Artikel' : Category.OVRIGT
		]
}
