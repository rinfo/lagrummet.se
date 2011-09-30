package se.lagrummet

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
		mockForConstraintsTests(User)
		
		def user = new User()
		assertFalse(user.validate())
		assertEquals("nullable", user.errors["fullName"])
		assertEquals("nullable", user.errors["username"])
		assertEquals("nullable", user.errors["password"])
		
    }
	
	void testUniqueUserName() {
		
		def user = new User(fullName:"User 1", username: "notunique@email.com", password:"password")
		mockForConstraintsTests(User, [user])
		
		def testUser = new User(fullName:"User 2", username: "notunique@email.com", password:"password")
		assertFalse(testUser.validate())
		
		assertEquals("unique", testUser.errors["username"])
	}
	
	void testUserIsASecUser() {
		assertEquals(SecUser, User.getSuperclass())
	}
}
