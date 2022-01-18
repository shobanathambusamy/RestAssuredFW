package com.qa.api.lms.restclient;

import java.util.Map;

import org.testng.ISuite;
import org.testng.ITestContext;

import com.qa.api.lms.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestClient {
	
	
	//HTTP Methods GET/PUT/POST/DELETE
	/**
	 * This method is used to call GET API and returning Response for GET call
	 * @param contentType
	 * @param baseURI
	 * @param basePath
	 * @param credentials
	 * @param pathParameter
	 * @param log
	 * @return
	 */
	public static Response doGet(ContentType contentType, String baseURI, String basePath, String username, String password, String pathParameter, boolean log) {
		
		setBaseURI(baseURI);
		RequestSpecification request = createRequest(contentType, basePath, username, password,pathParameter, log );
		return getResponse("GET", request, basePath);
		
	}
	
/**
 * This method is used to call POST API and returning Response for POST call
 * @param contentType
 * @param baseURI
 * @param basePath
 * @param credentials
 * @param pathParameter
 * @param log
 * @param obj
 * @return
 */
public static Response doPost(ContentType contentType, String baseURI, String basePath, String username, String password, String pathParameter, boolean log,  Object obj) {
		
		setBaseURI(baseURI);
		RequestSpecification request = createRequest(contentType, basePath, username , password,pathParameter, log );
		addRequestPayload(request,obj );
		
		return getResponse("POST", request, basePath);
		
	}

/**
 * This method is used to call PUT API and returning response from PUT call
 * @param contentType
 * @param baseURI
 * @param basePath
 * @param token
 * @param log
 * @param obj
 * @return
 */
public static Response doPut(ContentType contentType, String baseURI, String basePath, String username, String password, String pathParameter, boolean log,  Object obj) {
	RestClient.setBaseURI(baseURI);
	RequestSpecification request = RestClient.createRequest(contentType, basePath, username, password,pathParameter, log);
	addRequestPayload(request,obj );
	return RestClient.getResponse("PUT", request, basePath);
}

/**
 * This method is used to call delete API.
 * @param contentType
 * @param baseURI
 * @param basePath
 * @param token
 * @param log
 * @return
 */

public static Response doDelete(ContentType contentType, String baseURI, String basePath, String username, String password, String pathParameter, boolean log) {
	RestClient.setBaseURI(baseURI);
	RequestSpecification request = RestClient.createRequest(contentType,basePath, username, password,pathParameter, log);
	return RestClient.getResponse("DELETE", request, basePath);
}




public static void addRequestPayload(RequestSpecification request, Object obj) {
	String jsonPayload = TestUtil.getSerializedJSON(obj);
	request.body(jsonPayload);
	
	 
}


	private static Response getResponse(String httpMethod, RequestSpecification request, String basePath) {
		// TODO Auto-generated method stub
		
		return executeAPI(httpMethod, request, basePath);
	}

	private static Response executeAPI(String httpMethod, RequestSpecification request, String basePath) {
		
		Response response = null;
		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;

		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;
		default:
			System.out.println("Please pass the correct http method");
			break;
		
		}
		// TODO Auto-generated method stub
		return response;
	}

	private static RequestSpecification createRequest(ContentType contentType,String basePath,
			String username, String password, String pathParameter, boolean log) {
		// TODO Auto-generated method stub
		
		
		RequestSpecification request;
		
		if(log) {
			request = RestAssured.given().log().all();
			
		}else {
			request = RestAssured.given();
		}
		
		
		if(username!=null && password!=null){
		request.given().auth().preemptive().basic(username, password);
		}

		
		
		
		
		if(pathParameter!=null) {
			request.pathParam("programId", pathParameter);
		
			
			
		}
		request.contentType(contentType);
		return request;
	}

	private static void setBaseURI(String baseURI) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = baseURI;
	}

}
