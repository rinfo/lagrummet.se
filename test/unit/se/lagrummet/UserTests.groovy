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
	
	void testUsernameIsEmail() {
		mockForConstraintsTests(User)
		def correctUser = new User(fullName: "user 1", username: "valid@email.com", password: "password")
		def failUser = new User(fullName: "user 1", username: "notanemail", password: "password")
		
		assertTrue(correctUser.validate())
		
		assertFalse(failUser.validate())
		assertEquals("email", failUser.errors["username"])
	}
	
	
	void testUserIsASecUser() {
		assertEquals(SecUser, User.getSuperclass())
	}
}
