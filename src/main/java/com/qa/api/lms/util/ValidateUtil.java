package com.qa.api.lms.util;


import org.testng.Assert;

import com.qa.api.lms.pojo.Program;

import io.restassured.response.Response;

public class ValidateUtil {
	
	
	public static void  validateResponse (Program programObj, Response responseObj) {
		
		System.out.println(responseObj.getStatusCode());
		responseObj.prettyPrint();
		Assert.assertEquals(200, responseObj.statusCode());
		
		Assert.assertEquals(programObj.getProgramId(), responseObj.jsonPath().getInt("programId"));
		Assert.assertEquals(programObj.getProgramName(),responseObj.jsonPath().getString("programName"));
		Assert.assertEquals(programObj.getProgramDescription(), responseObj.jsonPath().getString("programDescription"));
		Assert.assertEquals(programObj.isOnline(),responseObj.jsonPath().getBoolean("online"));
	}
	
	
	
	public static void validateInvalidResponse( Response responseObj) {
		System.out.println(responseObj.getStatusCode());
		responseObj.prettyPrint();
		Assert.assertEquals(500, responseObj.statusCode());
		
		
	}
	
public static void  validatePostResponse (Program programObj, Response responseObj) {
		
		System.out.println(responseObj.getStatusCode());
		responseObj.prettyPrint();
		Assert.assertEquals(200, responseObj.statusCode());
		
		Assert.assertEquals(programObj.getProgramName(),responseObj.jsonPath().getString("programName"));
		Assert.assertEquals(programObj.getProgramDescription(), responseObj.jsonPath().getString("programDescription"));
		Assert.assertEquals(programObj.isOnline(),responseObj.jsonPath().getBoolean("online"));
	}


}
