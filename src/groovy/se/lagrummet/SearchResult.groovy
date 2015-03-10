package se.lagrummet

class SearchResult {

	Long totalResults = 0
	def totalResultsPerCategory = [:]
	Long maxItemsPerCategory = 4
    def errorMessages = []
	def items = [:]
	def itemsList = []
	
	def topHits = []

	public SearchResult() {
		for(Category cat : Category.values()) {
			items[(cat.toString())] = []
			totalResultsPerCategory[(cat.toString())] = 0
		}
	}
	
	public void addItemByType(SearchResultItem item) {
        println "se.lagrummet.SearchResult.addItemByType ${item.identifier} ${item.type}"
		def type = item.type
		def category = Category.getCategoryForType(type)

        if (category==Category.OVRIGT) {
            if(items[(category)].size() < maxItemsPerCategory) {
                items[(category)].add(item)
 		    }
            totalResultsPerCategory[(category)] += 1
        } else
            items[(category)].add(item)
	}
	
	public void addItem(SearchResultItem item) {
        println "se.lagrummet.SearchResult.addItem ${item.identifier}"
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

        this.itemsList += other.itemsList
		
		other.totalResultsPerCategory.each { category, otherCount ->
            try {
			    totalResultsPerCategory[(category)] += otherCount
            } catch (Exception e) {
                log.error "Failed do concatenate results", e
                errorMessages.add "Något gick fel. Det är inte säkert att sökresultatet är komplett."
            }
		}
		
		errorMessages.addAll(other.errorMessages)
		
		topHits.addAll(other.topHits)
		
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
	
	public void addTopHit(item) {
		topHits.add(item)
	}

}
