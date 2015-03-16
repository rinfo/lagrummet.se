package se.lagrummet

class SearchResult {

    Category category
	long totalResults = 0
    def errorMessages = []
	def items = []

	SearchResult(Category category) {
        this.category = category
	}
	
	void addItem(SearchResultItem item) {
		items.add(item)
	}

    def addEach = {
        addItem(it)
    }

    int itemsCount() { items.size() }

    boolean hasMoreResults() {
        if (items.isEmpty())
            return false
        return totalResults > items.size()
    }

}
