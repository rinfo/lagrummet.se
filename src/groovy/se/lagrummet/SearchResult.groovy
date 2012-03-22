package se.lagrummet

class SearchResult {

	Long totalResults = 0
	def totalResultsPerCategory = [:]
	Long maxItemsPerCategory = 4
	def errorMessages = []
	def items = [:]
	def itemsList = []
	
	public SearchResult() {
		for(Category cat : Category.values()) {
			items[(cat.toString())] = []
			totalResultsPerCategory[(cat.toString())] = 0
		}
	}
	
	public void addItemByType(SearchResultItem item) {
		def type = item.type
		def category = Category.getCategoryForType(type)
		
		if(items[(category)].size() < maxItemsPerCategory) {
			items[(category)].add(item)
		}
		
		totalResultsPerCategory[(category)] += 1
	}
	
	public void addItem(SearchResultItem item) {
		itemsList.add(item)
	}
	
	
	
	public SearchResult mergeWith(SearchResult other) {
		if(!other) {
			return this
		}
		this.totalResults += other.totalResults
		
		other.items.each { category, otherItems ->
			items[(category)].addAll(otherItems)
		}
		
		other.totalResultsPerCategory.each { category, otherCount ->
			totalResultsPerCategory[(category)] += otherCount
		}
		
		errorMessages.addAll(other.errorMessages)
		
		return this
	}
	
	public void addStats(List stats) {
		for(Category cat : Category.values()) {
			totalResultsPerCategory[(cat.toString())] = 0
		}

		stats.each {
			if(it.dimension == "type") {
				it.observations.each { type ->
					def category = Category.getCategoryForType(type.term)
					totalResultsPerCategory[(category)] += type.count
				}
			}
		}
	}
	
	public SearchResult resetCategory(Category cat) {
		items[(cat.toString())] = []
		totalResults -= totalResultsPerCategory[(cat.toString())]
		totalResultsPerCategory[(cat.toString())] = 0
		return this
	}
}
