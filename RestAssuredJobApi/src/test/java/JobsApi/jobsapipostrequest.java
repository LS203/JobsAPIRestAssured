package JobsApi;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jobsapi.util.ExcelUtils;
import com.jobsapi.util.jobsapiconfig;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static org.hamcrest.MatcherAssert.assertThat;



public class jobsapipostrequest {
	@DataProvider(name="PostJobsData")
	public Object[][] getCreatejobsapiData()throws Exception
	{
		
		Object[][] postJobsApiData=ExcelUtils.readExcelData("src/test/resources/TestData/JobsTestData.xlsx","Sheet1");
		return (postJobsApiData);	
	}
	
	
	@Test(dataProvider = "PostJobsData")
	public void ReadExcelData(String JobTitle, String JobCompanyName,String JobLocation ,String JobType ,String JobPostedTime,String JobDescription,String JobId)
	{
		//System.out.println(JobId);
		int jobId = Integer.parseInt(JobId.substring(0, JobId.indexOf('.')));
		System.out.println("Job ID: "+jobId);
		String url = jobsapiconfig.baseURI + "/Jobs";
		RestAssured.baseURI = url;
	   // System.out.println("Url: "+url);
	    RequestSpecification httpRequest =  RestAssured.given().auth().preemptive().basic("admin", "password");//.get(jobsapiconfig.baseURI + "/Jobs"));
	    httpRequest.header("content-type","application/json");
	    
	    Response response = RestAssured.given().queryParam("Job Id", jobId)
	    		.queryParam("Job Title", JobTitle)
	    		.queryParam("Job Company Name", JobCompanyName)
	    		.queryParam("Job Type", JobType)
	    		.queryParam("Job Description", JobDescription)
	    		.queryParam("Job Posted time", JobPostedTime)
	    		.queryParam("Job Location", JobLocation).when().post();
	    
	    	String Responsebody=response.getBody().asPrettyString();
	    	
		//Response response=given().auth().basic("admin", "password").get(url);
		System.out.println(response.asString());
		int statuscode=response.getStatusCode();
	
		Assert.assertEquals(statuscode, 200, "Status Code");
		Assert.assertEquals(Responsebody.contains("Job Id"),true,"JobId assertion");
		Assert.assertEquals(Responsebody.contains("Job Type"), true, "Job Type Assertion");
		Assert.assertEquals(Responsebody.contains("Job Description"), true, "Job Description Assertion");
		Assert.assertEquals(Responsebody.contains("Job Posted time"), true, "Job PostedTime Assertion");
		Assert.assertEquals(Responsebody.contains("Job Location"), true, "Job Location Assertion");
		Assert.assertEquals(Responsebody.contains("Job Title"), true, "Job Title Assertion");

		//schema validation
	   assertThat("Json Schema",Responsebody.replaceAll("NaN","null"),matchesJsonSchemaInClasspath("JsonSchemaJobsApi.json"));
	
	}


	
	
}
