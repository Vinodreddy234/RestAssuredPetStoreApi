package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {
	
       @Test(priority=1, dataProvider="Data",dataProviderClass=DataProviders.class)
	
	public void testPostuser(String userID, String userName, String fname, String lname, String userEmail,String psw,String ph) {
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(psw);
		userPayload.setPhone(ph);
	 
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
       @Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
       public void testDeleteUser(String userName) {
    	   Response response = UserEndPoints.deleteUser( userName);
  		 
  		 Assert.assertEquals(response.getStatusCode(), 200);
    	   
       }

	
	
}
