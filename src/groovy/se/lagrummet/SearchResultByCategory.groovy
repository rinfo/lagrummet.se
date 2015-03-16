package se.lagrummet

class SearchResultByCategory {

    long totalResults = 0
    def searchResultsByCategory = [:]

    def errorMessages = []

    SearchResultByCategory() {
    }

    def items(def category) {
        return getSearchResultByCategory(category).items
    }

    SearchResult getSearchResultByCategory(String category) {
        if (!category)
            throw new NullPointerException("category is null!")
        return getSearchResultByCategory(Category.getFromString(category.toString()))
    }

    SearchResult getSearchResultByCategory(Category category) {
        if (!category)
            throw new NullPointerException("category is null!")
        def searchResult = searchResultsByCategory[category]
        if (searchResult)
            return searchResult
        searchResult = new SearchResult(category)
        searchResultsByCategory[category] = searchResult
        return searchResult
    }

    void addItem(SearchResultItem item) {
        def type = item.type
        def category = Category.getCategoryForType(type)

        getSearchResultByCategory(category).addItem(item)
    }

    def getErrorMessages() {
        def errorMessages = this.errorMessages.clone()
        searchResultsByCategory.each{ key, searchResult -> errorMessages += searchResult.errorMessages }
        errorMessages
    }

    long calculateTotalResults() {
        long totalResults = 0;
        searchResultsByCategory.each { key, value -> totalResults += value.totalResults }
        return totalResults
    }

    def addEach = {
        addItem(it)
    }

    long totalResultsPerCategory(def category) {
        getSearchResultByCategory(category).totalResults
    }

    public void addStats(List stats) {
        stats.each {
            if(it.dimension == "byType") {
                it.observations.each { type ->
                    def category = Category.getFromString(type.term)
                    getSearchResultByCategory(category).totalResults = type.count
                }
            }
        }
    }

    int itemsCount(def category) { getSearchResultByCategory(category).itemsCount() }

    boolean hasMoreResults(def category) {
        getSearchResultByCategory(category).hasMoreResults()
    }


}
