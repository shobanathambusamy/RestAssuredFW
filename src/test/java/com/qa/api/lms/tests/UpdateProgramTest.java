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

public class UpdateProgramTest {
	
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
		public void updateProgramTest(int programId, String programName, String programDescription, boolean online) {
			
			
			//POST CALL
		Program program = new Program(programId, programName, programDescription, online);
		Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password, null, true,program );
		ValidateUtil.validatePostResponse(program, postResponse);
		program.setProgramId(postResponse.jsonPath().getInt("programId"));

		
		//GET CALL
		basePath= basePath + "/{programId}";
		Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePath, username, password,String.valueOf(program.getProgramId()) , true);
		ValidateUtil.validateResponse(program, getResponse);
		
		
		//PUT CALL
		Program updateProgram = new Program();
		updateProgram.setProgramId(program.getProgramId());
		updateProgram.setProgramDescription(programDescription + "update");
		updateProgram.setProgramName(programDescription + "update");
		updateProgram.setOnline(false);
		Response putResponse = RestClient.doPut(ContentType.JSON, baseURI, basePath, username, password,String.valueOf(updateProgram.getProgramId()), true,updateProgram );
		ValidateUtil.validateResponse(updateProgram, putResponse);

		//GET CALL
	
		Response getAfterPutResponse = RestClient.doGet(ContentType.JSON, baseURI, basePath, username, password,String.valueOf(updateProgram.getProgramId()) , true);
	    ValidateUtil.validateResponse(updateProgram, getAfterPutResponse);
				
			
		}

}
