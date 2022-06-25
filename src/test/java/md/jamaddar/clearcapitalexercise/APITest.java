package md.jamaddar.clearcapitalexercise;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import md.jamaddar.datamodel.DataArray;
import md.jamaddar.datamodel.JsonRoot;

public class APITest {
	@Test
	public void validateApiResponse() throws JsonMappingException, JsonProcessingException {
		RestAssured.baseURI = "https://datausa.io/api/data";  
		RequestSpecification httpRequest = RestAssured.given(); 
		httpRequest.queryParam("drilldowns","State");
		httpRequest.queryParam("measures","Population");
		httpRequest.queryParam("year","latest");
		
		Response response = httpRequest.request(Method.GET, ""); 
		ObjectMapper om = new ObjectMapper();
		JsonRoot jsonroot = om.readValue(response.asString(), JsonRoot.class);
		
		Assert.assertTrue(validateSameYear(jsonroot.data), "Not All data have same year value.");
		Assert.assertTrue(validateEachStateIsUnique(jsonroot.data), "Not All state values are unique.");
	}
	
	public boolean validateEachStateIsUnique(ArrayList<DataArray> data) {
		List<String> states = new ArrayList<>();
		for(DataArray d : data) {
			if(states.contains(d.state)) {
				return false;
			} else {
				states.add(d.state);
			}
		}
		return true;	
	}
	
	public boolean validateSameYear(ArrayList<DataArray> data) {
		String dataYear = data.get(0).year;
		for(DataArray d : data) {
			if(!dataYear.equals(d.year)) return false;
		}
		return true;
	}
}







