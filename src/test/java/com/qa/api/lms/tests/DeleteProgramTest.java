package com.qa.api.lms.tests;

import org.testng.Assert;
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

public class DeleteProgramTest {

	String baseURI = "https://lms-program-rest-service.herokuapp.com";
	String basePath = "/programs";
	String username;
	String password;
	
	
	@BeforeClass(dependsOnGroups = { "init" })
	public void getCredentials(ITestContext context) {
		
		System.out.println("@@BeforeClass executes");
		ISuite suite = context.getSuite();
		System.out.println(suite.getAttribute("username"));
		System.out.println(suite.getAttribute("password"));		
		username = (String) suite.getAttribute("username");
		password = (String) suite.getAttribute("password");
		
	}

	@DataProvider
	public Object[][] getProgramData() {
		Object[][] programData = ExcelUtil.getTestData("LMSData");
		return programData;

	}

	@Test(dataProvider = "getProgramData",  dependsOnGroups = { "init" })
	public void deleteProgramTest(int programId, String programName, String programDescription, boolean online) {

		// POST CALL
		Program program = new Program(programId, programName, programDescription, online);
		Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath,username, password,
				null, true, program);
		ValidateUtil.validatePostResponse(program, postResponse);
		program.setProgramId(postResponse.jsonPath().getInt("programId"));

		// DELETE CALL
		basePath = basePath + "/{programId}";
		Response deleteResponse = RestClient.doDelete(ContentType.JSON, baseURI, basePath,
				username, password, String.valueOf(program.getProgramId()), true);
		System.out.println(deleteResponse.getStatusCode());
		deleteResponse.prettyPrint();
		Assert.assertEquals(200, deleteResponse.statusCode());

		// GETCALL

		Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePath,username, password,
				String.valueOf(program.getProgramId()), true);
		System.out.println(getResponse.getStatusCode());
		getResponse.prettyPrint();
		Assert.assertEquals(200, getResponse.statusCode());
		Assert.assertEquals("null", getResponse.asString());

	}

	@Test(dataProvider = "getProgramData" ,  dependsOnGroups = { "init" })
	public void deleteProgramTest_ProgramNotAvailable(int programId, String programName, String programDescription,
			boolean online) {

		basePath = "/programs";

		// POST CALL
		Program program = new Program(programId, programName, programDescription, online);
		Response postResponse = RestClient.doPost(ContentType.JSON, baseURI, basePath, username, password,
				null, true, program);
		ValidateUtil.validatePostResponse(program, postResponse);
		program.setProgramId(postResponse.jsonPath().getInt("programId"));

		// DELETE CALL
		basePath = basePath + "/{programId}";
		Response deleteResponse1 = RestClient.doDelete(ContentType.JSON, baseURI, basePath,
				username, password, String.valueOf(program.getProgramId()), true);
		System.out.println(deleteResponse1.getStatusCode());
		deleteResponse1.prettyPrint();
		Assert.assertEquals(200, deleteResponse1.statusCode());

		// GETCALL

		Response getResponse = RestClient.doGet(ContentType.JSON, baseURI, basePath, username, password,
				String.valueOf(program.getProgramId()), true);
		System.out.println(getResponse.getStatusCode());
		getResponse.prettyPrint();
		Assert.assertEquals(200, getResponse.statusCode());
		Assert.assertEquals("null", getResponse.asString());

		// DELETECALL

		Response deleteResponse2 = RestClient.doDelete(ContentType.JSON, baseURI, basePath,
				username, password, String.valueOf(program.getProgramId()), true);
		System.out.println(deleteResponse2.getStatusCode());
		deleteResponse2.prettyPrint();
		Assert.assertEquals(500, deleteResponse2.statusCode());

	}

}
