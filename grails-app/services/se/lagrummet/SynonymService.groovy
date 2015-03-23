package se.lagrummet

class SynonymService {

    def lookupSynonyms(searchTerm) {
		def synonyms = Synonym.findAllBySynonym(searchTerm.toLowerCase())
		def values = synonyms*.baseTerm
		return values
    }

    def correctIdentifierSpelling(searchTerm) {
        def correctedIds = false
        correcters.each { correcter ->
            def corrected = correcter(searchTerm.trim())
            if(corrected) {
				searchTerm = corrected
				correctedIds = corrected
            }
        }
		return correctedIds
    }
	
    //SFS 1999:175
    def correctSFS = { query ->
        if(!(query ==~ /(?i)SFS \d{4}:\d+/)) {
            def corrected = query.replaceAll(/(?ix)SFS\D*(\d{4})\D+(\d{1,})/, 'SFS $1:$2')
			if(query == corrected)
				return false
			corrected
        }
        else
            false
    }
	
    //NJA 1981 s. 350
    def correctNJA = { query ->
        if(!(query ==~ /(?i)NJA \d{4} s. \d+/)) {
            def corrected = query.replaceAll(/(?ix)NJA\D*(\d{4})\s*s\.?\s*(\d+)/, 'NJA $1 s. $2')
			if(query == corrected)
				return false
			corrected
        }
        else
            false
    }

    def correcters = [correctSFS,correctNJA]

}
