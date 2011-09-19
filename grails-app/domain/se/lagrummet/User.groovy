package se.lagrummet

class User extends SecUser {

	String fullName
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		table "users"
	}
	
    static constraints = {
    }
}
