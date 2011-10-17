package se.lagrummet

class SearchResult {

	Long itemsPerPage = 50
	Long startIndex = 0
	Long totalResults = 0
	
	def items = [:]
	def originalItems
	
	public void addItemByType(SearchResultItem item) {
		def type = item.type
		def category = getCategoryForType(type)
		if(!items[(category)]) {
			items[(category)] = []
		}
		items[(category)].add(item)
	}
	
	private String getCategoryForType(String type) {
		//Lagar, Rattsfall, Propositioner, Utredningar, Ovrigt
		return typeToCategory[(type)] ?: unknown
		
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
		
		return this
	}
	
	static typeToCategory = [
		'Rattsfallsnotis' : 'Rattsfall',
		'Rattsfallspublikation' : 'Rattsfall',
		'Rattsfallsrapport' : 'Rattsfall',
		'Rattsfallsreferat' : 'Rattsfall',
		'VagledandeAvgorande' : 'Rattsfall',
		'VagledandeDomstolsavgorande' : 'Rattsfall',
		'VagledandeMyndighetsavgorande' : 'Rattsfall',
		
		'Forfattning' : 'Lagar',
		'Forfattningsreferens' : 'Lagar',
		'Forfattningssamling' : 'Lagar',
		'FSDokument' : 'Lagar',
		'Forordning' : 'Lagar',
		'Grundlag' : 'Lagar',
		'KonsolideradGrundforfattning' : 'Lagar',
		'Paragraf' : 'Lagar',
		'Lag' : 'Lagar',
		'Myndighetsforeskrift' : 'Lagar',
		
		'Proposition' : 'Propositioner',
		
		'Kommittedirektiv' : 'Utredningar',
		'Utredningsbetankande' : 'Utredningar',
		'Utredningsserie' : 'Utredningar'
		]
	static def unknown = 'Okand'
}
