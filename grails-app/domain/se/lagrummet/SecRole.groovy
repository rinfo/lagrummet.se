package se.lagrummet

class SecRole {

	String authority
	String name

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
		name blank: false, unique: true
	}
}
