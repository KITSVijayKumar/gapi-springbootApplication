package com.pim.migration.stepDefinitions;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pim.migration.configuration.PIMController;
import com.pim.migration.controller.ReadXML;
import com.pim.migration.controller.SpringIntegrationTest;


public class ProductDefs extends SpringIntegrationTest {
	
	@Autowired
	PIMController pimController;
	private HttpServletResponse response;
	private HttpStatus currentStatusCode;
	private Gson gson;
	private JsonElement techspecs;
	
	
	@Given("^the user calls to get /product$")
	public void the_user_calls_to_get_product() throws Throwable {
		System.out.println("Calling products-api Stage");
        System.out.println("Product URI: "+ configuration.getProductUrl());
	}
	
	@When("^user performs a delta change in productStatus for an article \"([^\"]*)\"$")
    public void user_performs_a_delta_change_in_productStatus_for_an_article(String ean) throws Throwable {
        //executeGet("https://products-api-stage.gapinp.kd.kfplc.com/v2/products/BQUK/"+ean);
        System.out.println("PRODUCT******URL******* "+ configuration.getProductUrl());
        executeGet(configuration.getProductUrl()+"/v2/products/BQUK/"+ean);
        System.out.println("ProductResponseCALLED");
    }
	
	@Then("^the user receives status code of (\\d+)$")
	public void the_user_receives_status_code_of(int statusCode) throws Throwable {
        currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("Status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
        System.out.println("StatusCode: "+ currentStatusCode);
	}

	@And("^the user should see the changes for LO Status flag reflected on the endpoint$")
	public void the_user_should_see_the_changes_for_LO_Status_flag_reflected_on_the_endpoint(DataTable dataTable) throws Throwable {
		
		List<List<String>> data = dataTable.raw();
		
		gson=new GsonBuilder().create();
	 	
		JsonObject objects = gson.fromJson(latestResponse.getBody(), JsonObject.class);
	 	
	 	JsonElement productEan 		= objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().get("ean");
		JsonElement productStats 	= objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().get("productStatus");
		JsonElement productCCOO 	= objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().get("contactCentreOrderingOnly");
		JsonElement prodHmDelAvail  = objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject()
										.getAsJsonObject("recommendedProductShippingMethods").getAsJsonObject("homeDelivery").getAsJsonObject().get("availability");
		
		String actualResp="{"+"\"ean\""+": "+productEan+",\"productStatus\""+": "+productStats+",\"contactCentreOrderingOnly\""+": "+productCCOO+",\"availability\""+": "+prodHmDelAvail+"}";

		for(int i=0;i<data.size();i++) {
			
			/*System.out.println("Expected Response : "+data.get(i).get(0));
			System.out.println(actualResp);*/
			
			 if(actualResp.toString().equals(data.get(i).get(0))) { 
				 System.out.println("Yes Contains = PASSED: "+data.get(i).get(0));
			 	 }
			 }
		}
	
	@Then("^the user should be able to validate the technicalSpec atrributes values$")
	public void the_user_should_be_able_to_validate_the_technicalSpec_atrributes_values(DataTable dataTable) throws Throwable {
		
		List<List<String>> data = dataTable.raw();
		
		gson=new GsonBuilder().create();
	 	
		JsonObject objects = gson.fromJson(latestResponse.getBody(), JsonObject.class);
		 
		JsonArray techSpecs=objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
		  System.out.println(techSpecs);
		  for(int i=0;i<data.size();i++) {
				
				System.out.println("Expected Response : "+data.get(i).get(0));
				
				System.out.println(techSpecs);
				
				 if(techSpecs.toString().contains(data.get(i).get(0))) { 
					 System.out.println("Yes Contains = PASSED");
				 	 }else{ 
				 		  System.out.println("Not Contains");
				 	 	  }
				 }
	}

	
	
//@KAPS-143
    @Given("^the user calls to get /product-rs$")
    public void the_user_calls_to_get_product_rs() throws Throwable {
        System.out.println("Calling products-rs-api Stage");
        System.out.println("Product URI: "+ configuration.getProduct_rs_Url());
    }

    @When("^user calls for TechSpecs of an \"([^\"]*)\"$")
    public void user_calls_for_TechSpecs_of_an(String ean) throws Throwable {
        executeGet(configuration.getProduct_rs_Url()+"/v2/products/BQUK/"+ean);
        //executeGetProduct(configuration.getProduct_rs_Url()+"/v2/products/BQUK/"+ean);
        System.out.println("product-rs-ResponseCALLED ");
    }
    
    @Then("^the user receives status code of (\\d+) for LatestResponseProduct$")
	public void the_user_receives_status_code_for_LatestResponseProduct(int statusCode) throws Throwable {
        currentStatusCode = latestResponseProduct.getTheResponse().getStatusCode();
        assertThat("Status code is incorrect : " + latestResponseProduct.getBody(), currentStatusCode.value(), is(statusCode));
        System.out.println("StatusCode: "+ currentStatusCode);
    }
   
    @And("^the user should see the techinicalSpecifications are <=(\\d+) sequenced in ascending order$")
    public void the_user_should_see_the_techinicalSpecifications_are_sequenced_in_ascending_order(int arg1) throws Throwable {
    	gson=new GsonBuilder().create();
        JsonObject objects = gson.fromJson(latestResponse.getBody(), JsonObject.class);
        JsonArray techSpecs=objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
        techspecs=techSpecs;
        System.out.println(techspecs);
    }

    @And("^the user verifies the same with rs-xml$")
    public void the_user_verifies_the_same_with_rs_xml() throws Throwable {
    	System.out.println("XML is being Read");
        new ReadXML().readTechSpecAttributes();
        System.out.println("XML has been Read");
    }
    
    @Then("^the user should see name & value attributes for technicalSpecifications on the endpoint\\.$")
    public void the_user_should_see_name_value_attributes_for_technicalSpecifications_on_the_endpoint() throws Throwable {
        
        gson=new GsonBuilder().create();
        JsonObject objects = gson.fromJson(latestResponse.getBody(), JsonObject.class);
        JsonArray techSpecs=objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
        
        System.out.println("Available technicalSpecifications on Endpoint: "+techSpecs);
        
        if(techSpecs.toString().contains("name") && techSpecs.toString().contains("value")){
        	System.out.println("TEST PASSED - Showing name and value for technicalSpecifications: Attribute");
        }else{
        	System.out.println("TEST FAILED: ");
        }   		
    }

    @Given("^the user gets Tech Spec XX attribute values from rs-xml$")
    public void the_user_gets_Tech_Spec_XX_attribute_values_from_rs_xml() throws Throwable {
        System.out.println("the user gets Tech Spec XX attribute values from rs-xml");
        new ReadXML().readTechSpecAttributes();
    }

    @When("^the user finds no-values for Tech_Spec$")
    public void the_user_finds_no_values_for_Tech_Spec() throws Throwable {
    	new ReadXML().readEmptyTechSpecAttributes();
    }

    @Then("^the user should see empty techinicalSpecifications for the \"([^\"]*)\" on the endpoint\\.$")
    public void the_user_should_see_empty_techinicalSpecifications_for_the_on_the_endpoint(String arg1) throws Throwable {
    	new ReadXML().readEmptyTechSpecAttributes();
    }

    @When("^user receives the technicalSpecifications array list$")
    public void user_receives_the_technicalSpecifications_array_list() throws Throwable {
    	gson=new GsonBuilder().create();
        JsonObject objects = gson.fromJson(latestResponse.getBody(), JsonObject.class);
        JsonArray techSpecs=objects.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
        techspecs=techSpecs;
        System.out.println(techspecs);
    }

    @Then("^the user validates the same with Tech_Spec_XX in RS-Xml\\.$")
    public void the_user_validates_the_same_with_Tech_Spec_XX_in_RS_Xml() throws Throwable {
        assertThat(techspecs, is(new ReadXML().techSpecCDATA));
        
        if(techspecs.toString().equalsIgnoreCase(new ReadXML().CDATA)) {
        	System.out.println();
        }
    }
}	



