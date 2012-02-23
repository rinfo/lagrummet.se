package se.lagrummet

class SynonymService {

    def lookupSynonyms(searchTerm) {
		def synonyms = Synonym.findAllBySynonym(searchTerm.toLowerCase())
		def values = synonyms*.baseTerm
		return values
    }
}
