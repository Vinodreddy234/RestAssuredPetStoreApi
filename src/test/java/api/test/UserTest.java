package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker= new Faker();
		userPayload= new User();
		
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
	logger= LogManager.getLogger(this.getClass());	
	logger.debug( "debugging..........");
		
		
	}
	
	@Test(priority=1)
	
	public void testPostuser() {
		logger.info("@@@@@@@@@    Creating User  @@@@@@@@@@@@@@@@");
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	 
		
		logger.info("@@@@@@@@@    Created User  @@@@@@@@@@@@@@@@");
	}
	
	@Test(priority=2)
	public void  testReadUser() {
		logger.info("@@@@@@@@@   Reading User info  @@@@@@@@@@@@@@@@");
		 Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		 response.then().log().all();
		 Assert.assertEquals(response.getStatusCode(), 200);
		 logger.info("@@@@@@@@@    user info is Displayed  @@@@@@@@@@@@@@@@");
		
	}
	

	@Test(priority=3)
	
	public void testUpdateUser() {
		logger.info("@@@@@@@@@    Updating User  @@@@@@@@@@@@@@@@");
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
	 

		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//cheaking the data update
		 Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
			response.then().log();
		 Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		 
		 
		 logger.info("@@@@@@@@@    Updated  User  @@@@@@@@@@@@@@@@");
	}
	
	@Test(priority=4)
	public void testDeleteUser(){
		logger.info("@@@@@@@@@    Deleting User  @@@@@@@@@@@@@@@@");
		
		 Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		 
		 Assert.assertEquals(response.getStatusCode(), 200);
		 logger.info("@@@@@@@@@    Deleted User  @@@@@@@@@@@@@@@@");
	}

}
