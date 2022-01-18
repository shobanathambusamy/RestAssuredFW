package com.qa.api.lms.tests;

import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.lms.restclient.RestClient;
import com.qa.api.lms.util.ExcelUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BasicAuthConfigTest {
	
	String baseURI = "https://lms-program-rest-service.herokuapp.com";
	String basePath= "/programs";
	String username;
	String password;
	
	@DataProvider
	public Object[][] getAuthCredentials(){
		Object[][] programData = ExcelUtil.getTestData("LMSCredentials");
		return programData;
		
	}
	
	
	
	@Test(groups = { "init" }, dataProvider = "getAuthCredentials")
	public void getCredentialsFromDP(ITestContext context, String username, String password) {

		ISuite suite = context.getSuite();
		suite.setAttribute("username", username);
		suite.setAttribute("password", password);

	}
	
	@Test 
	public void getAllPrograms_WithNoAuthTest() {
		
		
	Response response = RestClient.doGet(ContentType.JSON, baseURI, basePath,  null,  null, null, true);
	System.out.println(response.getStatusCode());
	response.prettyPrint();
	Assert.assertEquals(401, response.statusCode());
	
	}
	
	

}
