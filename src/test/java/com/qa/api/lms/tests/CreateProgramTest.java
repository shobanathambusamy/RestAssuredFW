package com.qa.api.lms.tests;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.api.lms.pojo.Program;
import com.qa.api.lms.restclient.RestClient;
import com.qa.api.lms.util.ExcelUtil;
import com.qa.api.lms.util.ValidateUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateProgramTest {
	
	
	
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
public Object[][] getProgramData(){
	Object[][] programData = ExcelUtil.getTestData("LMSData");
	return programData;
	
}
	
	@Test(dataProvider = "getProgramData", dependsOnGroups = { "init" })
	public void createProgramTest(int programId, String programName, String programDescription, boolean online) {
		
	//POST CALL
	Program program = new Program(programId, programName, programDescription, online);
	Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );	
	ValidateUtil.validatePostResponse(program, postResponse);
	program.setProgramId(postResponse.jsonPath().getInt("programId"));

	
	//GETCALL
	String basePathWithId= basePath + "/{programId}";
	Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePathWithId, username, password,String.valueOf(program.getProgramId()) , true);
	ValidateUtil.validateResponse(program, getResponse);
	
		
	}
	
	@Test(dataProvider = "getProgramData", dependsOnGroups = { "init" })
	public void createProgramWithInvalidProgramNameAsNullTest(int programId, String programName, String programDescription, boolean online) {
		
	//POST CALL
	Program program = new Program(programId, null, programDescription, online);
	Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );	
	ValidateUtil.validateInvalidResponse(postResponse);
	
	}
	
	@Test(dataProvider = "getProgramData", dependsOnGroups = { "init" })
	public void createProgramWithInvalidProgramNameAsEmptyTest(int programId, String programName, String programDescription, boolean online) {
		
	//POST CALL
	Program program = new Program(programId, "  ", programDescription, online);
	Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );	
	ValidateUtil.validateInvalidResponse(postResponse);
		
	}
	
	@Test(dataProvider = "getProgramData", dependsOnGroups = { "init" })
	public void createProgramWithNullValuesForProgramDecriptionTest(int programId, String programName, String programDescription, boolean online) {
		
	//POST CALL
	Program program = new Program(0, programName, null,false);
	Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );	
	ValidateUtil.validatePostResponse(program, postResponse);
	program.setProgramId(postResponse.jsonPath().getInt("programId"));

	
	//GETCALL
	String basePathWithId= basePath + "/{programId}";
	Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePathWithId, username, password,String.valueOf(program.getProgramId()) , true);
	ValidateUtil.validateResponse(program, getResponse);
	
		
	}
	
	
	@Test(dataProvider = "getProgramData", dependsOnGroups = { "init" })
	public void createProgramWithExistingIdTest(int programId, String programName, String programDescription, boolean online) {
		
	//POST CALL
	Program program = new Program(programId, programName, programDescription, online);
	Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );	
	ValidateUtil.validatePostResponse(program, postResponse);
	program.setProgramId(postResponse.jsonPath().getInt("programId"));

	
	//GETCALL
	String basePathWithId= basePath + "/{programId}";
	Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePathWithId, username, password,String.valueOf(program.getProgramId()) , true);
	ValidateUtil.validateResponse(program, getResponse);
	
	System.out.println("<----------Existing Id POST CALL --------->");
	
	// POST CALL With ExistingID
	int existingProgramId = getResponse.jsonPath().getInt("programId");
	Program existingProgram = new Program();
	existingProgram.setProgramId(existingProgramId);
	existingProgram.setProgramName(programName + " new data");
	existingProgram.setProgramDescription(programDescription + " new data");
	existingProgram.setOnline(false);
	Response postResponseWithExistingId = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password,
			null, true, existingProgram);
	ValidateUtil.validatePostResponse(existingProgram, postResponseWithExistingId);

	// GETCALL
	Response getExistingIdResponse = RestClient.doGet(ContentType.JSON, baseURI, basePathWithId, username, password,
			String.valueOf(existingProgram.getProgramId()), true);
	ValidateUtil.validateResponse(existingProgram, getExistingIdResponse);
	

	}

}
