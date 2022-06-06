package com.jobsapi.util;

import static io.restassured.RestAssured.baseURI;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobsapiconfig {

	public static  String baseURI = "https://jobs123.herokuapp.com";
	public static String baseURI1="https://numpyninja-joblistapi.herokuapp.com/Jobs";
		
		}
