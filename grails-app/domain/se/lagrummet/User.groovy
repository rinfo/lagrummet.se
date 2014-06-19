package se.lagrummet

class User extends SecUser {

	String fullName
	String email
	String department
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		table "users"
        autoTimestamp: true
	}
	
    static constraints = {
		fullName blank:false
		email email:true, blank:true, nullable:true
		department blank:false
    }
}
