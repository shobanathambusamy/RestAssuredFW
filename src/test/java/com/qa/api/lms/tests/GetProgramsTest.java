package com.qa.api.lms.tests;

import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.api.lms.restclient.RestClient;
import com.qa.api.lms.util.ExcelUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetProgramsTest {
	
	
	String baseURI = "https://lms-program-rest-service.herokuapp.com";
	String basePath= "/programs";
	String username;
	String password;
	
	
	@BeforeClass(dependsOnGroups = { "init" })
	public void getCredentials(ITestContext context) {
		
		System.out.println("@BeforeClass executes");
		ISuite suite = context.getSuite();
		System.out.println(suite.getAttribute("username"));
		System.out.println(suite.getAttribute("password"));		
		username = (String) suite.getAttribute("username");
		password = (String) suite.getAttribute("password");
		
	}
	
	@DataProvider
	public Object[][] getData(){
		Object[][] programData = ExcelUtil.getTestData("LMSGetAPIData");
		return programData;
		
	}
	
	@DataProvider
	public Object[][] getProgramNotAvailableData(){
		Object[][] programData = ExcelUtil.getTestData("LMSGetAPIUnavailableData");
		System.out.println("data provider"+ programData.toString());
		return programData;
		
	}
		
		
	
	
	
	
	@Test (dependsOnGroups = { "init" })
	public void getAllProgramsTest() {
		
		
	Response response = RestClient.doGet(ContentType.JSON, baseURI, basePath,  username,  password, null, true);
	System.out.println(response.getStatusCode());
	response.prettyPrint();
	Assert.assertEquals(200, response.statusCode());
	
	}
	
	@Test(dataProvider = "getData", dependsOnGroups = { "init" })
	public void getProgramById(int programId) {
		
		System.out.println("From excel sheet"+ programId);
		String path = basePath + "/{programId}";
		
		Response response = RestClient.doGet(ContentType.JSON, baseURI, path,  username,  password, String.valueOf(programId), true);
		//3050
		System.out.println(response.getStatusCode());
		response.prettyPrint();
		Assert.assertEquals(200, response.statusCode());
		Assert.assertNotEquals("null",response.jsonPath().getString("programName"));
		Assert.assertNotEquals("null",response.jsonPath().getString("programDescription"));
	
		
	}
	
	@Test(dependsOnGroups = { "init" }, dataProvider = "getProgramNotAvailableData")
	public void getProgramById_ProgramNotAvailable(int programId) {
		System.out.println("From excel sheet"+ programId);
		
		String path= basePath + "/{programId}";
		Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, path,  username,  password,
				 String.valueOf(programId), true);
		System.out.println(getResponse.getStatusCode());
		getResponse.prettyPrint();
		Assert.assertEquals(200, getResponse.statusCode());
		Assert.assertEquals("null", getResponse.asString());
		
	}
	
	@Test (dependsOnGroups = { "init" })
	public void getAllPrograms_WithInvalidURLTest() {
		String invalidBasePath = "/program";
		
		
	Response response = RestClient.doGet(ContentType.JSON, baseURI, invalidBasePath,  username,  password, null, true);
	System.out.println(response.getStatusCode());
	response.prettyPrint();
	Assert.assertEquals(404, response.statusCode());
	
	}
	
	
	
	@Test(dependsOnGroups = { "init" })
	public void getProgramByZeroId_Test() {
		String path = basePath + "/{programId}";
		Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, path,  username,  password,
				 String.valueOf(0), true);
		System.out.println(getResponse.getStatusCode());
		getResponse.prettyPrint();
		Assert.assertEquals(200, getResponse.statusCode());
		Assert.assertEquals("null", getResponse.asString());
		
	}
	
	
       
}
