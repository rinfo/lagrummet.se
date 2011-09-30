package se.lagrummet

class User extends SecUser {

	String fullName
	String email
	String department
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		table "users"
	}
	
    static constraints = {
		email email:true, blank:true, nullable:true
		department blank:true, nullable:true
    }
}
