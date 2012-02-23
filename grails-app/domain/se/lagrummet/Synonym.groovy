package se.lagrummet

class Synonym {

	String baseTerm
	String synonym
	
	
    static constraints = {
		baseTerm(blank:false, nullable:false)
		synonym(blank:false, nullable: false)
    }
	
	static mapping = {
		sort baseTerm: "asc"
	}
}
