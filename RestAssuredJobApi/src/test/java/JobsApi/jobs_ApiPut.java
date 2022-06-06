package JobsApi;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jobsapi.util.ExcelUtils;
import com.jobsapi.util.jobsapiconfig;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobs_ApiPut {

	@DataProvider(name="updatedata")
	public Object[][]updatejobsapi()throws Exception
	{     Object[][]UpdateJobsapi=ExcelUtils.readExcelData("src/test/resources/TestData/updatejobs.xlsx","Sheet1");
		
		return (UpdateJobsapi);
		}
	
	@Test(dataProvider="updatedata")
	public void ReadExcelData(String JobId, String Field, String Value)
	{
		int jobId=Integer.parseInt(JobId.substring(0, JobId.indexOf('.')));
		String url=jobsapiconfig.baseURI+"/Jobs";
		RestAssured.baseURI=url;
		RequestSpecification httpRequest=RestAssured.given().auth().preemptive().basic("admin","password");
		httpRequest.header("content-type","application/json");
 Response response=RestAssured.given().queryParam("Job Id", jobId)
		                              .queryParam(Field, Value)
		                               .when().put();
	
 String Responsebody=response.getBody().asPrettyString();
 int statuscode=response.getStatusCode();
 System.out.println(statuscode);
 Assert.assertEquals(statuscode, 200, "Status Code");
 
 System.out.print(Responsebody);
 
 assertThat("Json Schema",Responsebody.replaceAll("NaN","null"),matchesJsonSchemaInClasspath("JsonSchemaJobsApi.json"));

}
}
