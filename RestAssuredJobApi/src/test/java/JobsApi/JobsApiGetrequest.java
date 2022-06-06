package JobsApi;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jobsapi.util.ExcelUtils;
import com.jobsapi.util.jobsapiconfig;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class JobsApiGetrequest {
	
	@DataProvider(name = "GetJobsData")
	public Object[][] getjobapiExcelData() throws Exception
	{
		Object[][] getdata=ExcelUtils.readExcelData("src/test/resources/TestData/JobsTestData.xlsx","Sheet1");
		return (getdata);	
	}
	


	@Test
	public void get() {

		Response response = given().auth().basic("admin", "password").get(jobsapiconfig.baseURI + "/Jobs");
		System.out.println(response.getStatusCode());
		String responsebody = response.getBody().asPrettyString();
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getHeader("content-type"));
		System.out.println(response.getTime());
		int statuscode = response.getStatusCode();
		Assert.assertEquals(statuscode, 200);
		// Assert.assertEquals(responsebody.contains("Job Id"), true,"correct value");
		Assert.assertTrue(responsebody.contains("Job Id"));
		Assert.assertTrue(responsebody.contains("Job Type"));
		Assert.assertTrue(responsebody.contains("Job Company Name"));
		Assert.assertTrue(responsebody.contains("Job Description"));
		Assert.assertTrue(responsebody.contains("Job Location"));
		Assert.assertTrue(responsebody.contains("Job Posted time"));
		Assert.assertTrue(responsebody.contains("Job Title"));
		//schema validation
		   assertThat("Json Schema",responsebody.replaceAll("NaN","null"),matchesJsonSchemaInClasspath("JsonSchemaJobsApi.json"));
		
	}

//negative scenario	
	/*@Test
	public void teststatuscode() {
		Response response = given().auth().basic("admin", "password").get(jobsapiconfig.baseURI + "/Jobs/2");
		System.out.println(response.getStatusCode());
		String responsebody = response.getBody().asPrettyString();
		int statuscode = response.getStatusCode();
		Assert.assertEquals(statuscode, 404, " checking for Not Found");
	}*/

	
	
	
/*	@Test(dataProvider = "GetJobsData")
	public void ReadExcelData(String Username, String password,String statuscode)throws IOException
	{
		String url = jobsapiconfig.baseURI +"/Jobs"+9001;
		RestAssured.bsaseURI=url;
		 httprequest=RestAssured.given().auth().basic.preemptive().basic(username,password);
		system.out.println("connected");
		System.out.println(response.asString());
		
*/
	}




