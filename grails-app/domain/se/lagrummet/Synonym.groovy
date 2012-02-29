package se.lagrummet

class Synonym {

	String baseTerm
	String synonym
	
	boolean deleted
	static transients = ['deleted']
	
	
    static constraints = {
		baseTerm(blank:false, nullable:false)
		synonym(blank:false, nullable: false)
    }
	
	static mapping = {
		sort baseTerm: "asc"
	}
}
