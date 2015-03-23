package se.lagrummet

class SynonymService {

    def lookupSynonyms(searchTerm) {
		def synonyms = Synonym.findAllBySynonym(searchTerm.toLowerCase())
		def values = synonyms*.baseTerm
		return values
    }

    def correctIdentifierSpelling(searchTerm) {
        def correctedIds = []
        correcters.each { correcter ->
            def corrected = correcter(searchTerm.trim())
            if(corrected)
                correctedIds << corrected
        }
    }
    //SFS 1999:175
    def correctSFS = { query ->
        if(!query =~ /(?ix)SFS \d{4}:\d+/)
            query.replaceAll(/(?ix)(SFS)\D*(\d{4})\D+(\d{1,})/, '$1 $2:$3')
        else
            false
    }
    //NJA 1981 s. 350
    def correctNJA = { query ->
        if(!query =~ /(?ix)NJA \d{4}] s. \d+/)
            query.replaceAll(/(?ix)(NJA)\D*\d{4}\s*s\.?\s*\d+/, '$1 $2 s. $3')
        else
            false
    }

    def correcters = [correctSFS,correctNJA]

}
