package JobsApi;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jobsapi.util.ExcelUtils;
import com.jobsapi.util.jobsapiconfig;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobs_ApiDelete {
	@DataProvider(name="DeleteData")

	public Object[][]deleteJobsapi()throws Exception
	{     Object[][]deleteJobsapi=ExcelUtils.readExcelData("src/test/resources/TestData/updatejobs.xlsx","Sheet2");
		
		return (deleteJobsapi);
		}
	
	@Test(dataProvider="DeleteData")
	public void ReadExcelData(String JobId)
	{
		int jobId=Integer.parseInt(JobId.substring(0, JobId.indexOf('.')));
		String url=jobsapiconfig.baseURI+"/Jobs";
		RestAssured.baseURI=url;
		RequestSpecification httpRequest=RestAssured.given().auth().preemptive().basic("admin","password");
		httpRequest.header("content-type","application/json");
 Response response=RestAssured.given().queryParam("Job Id", jobId)
		                                    .when().delete();
 
 String Responsebody=response.getBody().asPrettyString();
 int statuscode=response.getStatusCode();
 System.out.println(statuscode);
}
}




