package se.lagrummet

class PingDBService {

    static transactional = true

    /**
     * Executes a count of the page table, just to create traffic to avoid the MySQL timeout
     */
    def pingDB() {
        return Page.count()
    }
}
