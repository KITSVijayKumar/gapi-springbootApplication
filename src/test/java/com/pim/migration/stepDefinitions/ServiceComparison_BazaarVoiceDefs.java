package com.pim.migration.stepDefinitions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pim.migration.controller.CommonFunctions;
import com.pim.migration.controller.SpringIntegrationTest;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class ServiceComparison_BazaarVoiceDefs extends SpringIntegrationTest {

	private HttpStatus currentStatusCode;
	private Gson gson;
	private JsonObject objectsProduct;
	private JsonObject objectsProductrs;
	private JsonArray productsattributesTechSpecs;
	private JsonArray productsRSattributesTechSpecs;
	private JsonObject productsattributesHealthAndSafety;
	private JsonObject productsRSattributesHealthAndSafety;
	private CommonFunctions commonFunctions;
	
	
	@When("^user calls to get /product response for a given \"([^\"]*)\"$")
	public void user_calls_to_get_product_response_for_a_given(String ean) throws Throwable {
		System.out.println("Calling products-api Stage");
        System.out.println("Product URI: "+ configuration.getProductUrl());
        executeGet(configuration.getProductUrl()+"/v2/products/BQUK/"+ean);
        System.out.println("ProductResponseCALLED");
	}

	@Then("^the user should receive status code of (\\d+)$")
	public void the_user_should_receive_status_code_of(int statusCode) throws Throwable {
		currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("Status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
        System.out.println("StatusCode: "+ currentStatusCode);
	}

	@And("^the user should be able to see all the expected product attributes$")
	public void the_user_should_be_able_to_see_all_the_expected_product_attributes() throws Throwable {
		gson=new GsonBuilder().create();
        objectsProduct = gson.fromJson(latestResponse.getBody(), JsonObject.class);
        JsonArray productAttributesTechSpecs = objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonArray("technicalSpecifications");
        productsattributesTechSpecs=productAttributesTechSpecs;
        System.out.println("JSON OBJECTS PRODUCTs TechSpecs : " +productsattributesTechSpecs);
	}

	@When("^user calls to get /product-rs response for a given \"([^\"]*)\"$")
	public void user_calls_to_get_product_rs_response_for_a_given(String ean) throws Throwable {
		System.out.println("Calling products-rs-api Stage");
        System.out.println("Product URI: "+ configuration.getProduct_rs_Url());
        executeGet(configuration.getProduct_rs_Url()+"/v2/products/BQUK/"+ean);
        //executeGet(configuration.getProduct_prodn_Url()+"/v2/products/BQUK/"+ean);
        System.out.println("product-rs-ResponseCALLED ");
	}

	@And("^the user should be able to see all the expected product-rs attributes$")
	public void the_user_should_be_able_to_see_all_the_expected_product_rs_attributes() throws Throwable {
		gson=new GsonBuilder().create();
        objectsProductrs = gson.fromJson(latestResponse.getBody(), JsonObject.class);
        productsRSattributesTechSpecs = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
        System.out.println("JSON OBJECTS PRODUCTs-rs TechSpecs: " +productsRSattributesTechSpecs);
	}

	@Then("^compare the endpoints symmetricDifference for TECH_SPECS$")
	public void symmetricDifference_for_TECH_SPECS() throws Throwable {
		
		if (productsattributesTechSpecs.equals(productsRSattributesTechSpecs))
			{
			System.out.println("TEST PASSED = Responses Matched" + productsattributesTechSpecs.equals(productsRSattributesTechSpecs));
			}
			else
			{
			System.out.println("TEST FAILED = Reponse NOT-Matched \n\t productsattributes: " + productsattributesTechSpecs + "\n\t productsRSattributes: "+ productsRSattributesTechSpecs);
			}
		
			System.out.println("Products-TECHSPEC Size  "+ productsattributesTechSpecs.size());
			System.out.println("Products-RS-TECHSPEC Size  "+ productsRSattributesTechSpecs.size());
        
			for(int i=0; i < productsRSattributesTechSpecs.size() && i < productsattributesTechSpecs.size(); i++) {
            
				if(!productsRSattributesTechSpecs.get(i).toString().equalsIgnoreCase(productsattributesTechSpecs.get(i).toString())){
             
                //System.out.println("DIFFERENCES against Product-API for TECH-SPECS : " + productsRSattributesTechSpecs.get(i).toString());
                System.out.println("DIFFERENCES against Product-API for TECH-SPECS : " + "\n\t productsRSattributes: "+ productsRSattributesTechSpecs.get(i).toString());
                }
			}
			
	}
		
	@And("^the endpoints symmetricDifference for HLTH_and_SAFETY$")
	public void symmetricDifference_for_HLTH_and_SAFETY() throws Throwable {
		gson=new GsonBuilder().create();
		
		//productsattributesTechSpecs =       objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject().getAsJsonArray("technicalSpecifications");
		productsattributesHealthAndSafety = objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject("productHealthAndSafety");
		productsRSattributesHealthAndSafety = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonObject("productHealthAndSafety");
		
		
		System.out.println("JSON OBJECTS PRODUCTs Health&Safety : " +productsattributesHealthAndSafety);
		System.out.println("JSON OBJECTS PRODUCTs-rs Health&Safety: " +productsRSattributesHealthAndSafety);
		
		
		if (productsattributesHealthAndSafety.equals(productsRSattributesHealthAndSafety))
		{
		System.out.println("TEST PASSED = Responses Matched" + productsattributesHealthAndSafety.equals(productsRSattributesHealthAndSafety));
		}
		else
		{
		System.out.println("TEST FAILED = Reponse NOT-Matched \n\t productsattributesHealthAndSafety: " + productsattributesHealthAndSafety + "\n\t productsRSattributesHealthAndSafety: "+ productsRSattributesHealthAndSafety);
		}
		
		System.out.println("Products-Health&Safety Size  "+ productsattributesHealthAndSafety.size());
		System.out.println("Products-RS-Health&Safety Size  "+ productsRSattributesHealthAndSafety.size());
        
			if(!productsRSattributesHealthAndSafety.toString().equalsIgnoreCase(productsattributesHealthAndSafety.toString())){
         
            System.out.println("DIFFERENCES against Product-API for HEALTH & SAFETY: " + productsRSattributesHealthAndSafety.toString());
		}
	}

	@And("^the endpoints symmetricDifference for HM_Del_SHPPNG_MethodsBQ$")
	public void symmetricDifference_for_HMDel_SHPPNG_MethodsBQ(DataTable HmDelDatatable) throws Throwable {
		
		gson=new GsonBuilder().create();
		JsonObject products_homeDeliveryShippingBQ = objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes")
														.getAsJsonObject("recommendedProductShippingMethods").getAsJsonObject("homeDelivery");
		JsonObject productsRS_homeDeliveryShippingBQ = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
														.getAsJsonObject("recommendedProductShippingMethods").getAsJsonObject("homeDelivery");
	
		System.out.println("HOME-DELIVERY SHIPPING: "+products_homeDeliveryShippingBQ);
		System.out.println("HOME-DELIVERY RS SHIPPING: "+productsRS_homeDeliveryShippingBQ);
		
		
		List<List<String>> data = HmDelDatatable.raw();
		for (int i=0; i<data.size(); i++){
			String input = data.get(i).toString();
			System.out.println("FeatureFileTableData :" + input.substring(2, input.length()-2));
						
			if(products_homeDeliveryShippingBQ.toString().contains(input.substring(2, input.length()-2)) && 
				productsRS_homeDeliveryShippingBQ.toString().contains(input.substring(2, input.length()-2))){
			
		System.out.println("COMPARISON Matched = Passed, No DIFFERENCES against Product-API for homeDeliveryShippingBQ");
		}else{
		System.out.println("COMPARISON Not-Matched = Failed, DIFFERENCES against Product-API for homeDeliveryShippingBQ: "+productsRS_homeDeliveryShippingBQ);
		}
		}
	}

	@And("^the endpoints symmetricDifference for OTHER_SHPPNG_MethodsBQ$")
	public void symmetricDifference_for_OTHER_SHPPNG_MethodsBQ(DataTable otherDelDatatable) throws Throwable {
		gson=new GsonBuilder().create();
		/*JsonElement products_otherShippingBQ = objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonArray("recommendedProductShippingMethods").get(1);
		JsonElement productsRS_otherShippingBQ = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes").getAsJsonArray("recommendedProductShippingMethods").get(1);
        */
	}
	
	@Then("^the user checks child objects of 'data:' are ordered$")
	public void the_user_checks_child_objects_of_data_are_ordered(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	   
	}

	@And("^the user checks child objects of 'attributes:' are ordered$")
	public void the_user_checks_child_objects_of_attributes_are_ordered(DataTable attrObjectsDatatable) throws Throwable {
		//feature file data
				List<List<String>> attrObjectslist = attrObjectsDatatable.raw();
				
				String[] FeatureFileTableData=new String[attrObjectslist.size()];
				for (int i=0; i<attrObjectslist.size(); i++){
					//List<String> input_attrObjectslist = attrObjectslist.get(i);
					String input_attrObjectslist = attrObjectslist.get(i).toString();
					System.out.println("FeatureFileTableData :" + input_attrObjectslist);
					
					FeatureFileTableData[i]=input_attrObjectslist.substring(1, input_attrObjectslist.length()-1);
					System.out.println(FeatureFileTableData[i]);
				}
				gson=new GsonBuilder().create();
				JsonObject attributes = objectsProduct.getAsJsonObject("data").getAsJsonObject("attributes");
				
					ArrayList<String> arraylist=new ArrayList<String>();
						for(Map.Entry<String, JsonElement> entry:attributes.entrySet()) {			
							String keylist = entry.getKey().toString();
							arraylist.add(keylist);
							//Collections.sort(arraylist);
							System.out.println(keylist);
						}
					//Iterator<String> iter=list.iterator();
					String[] respdataProductAttr=new String[arraylist.size()];
						for(int i=0;i<arraylist.size();i++){
							System.out.println(arraylist.get(i));
							respdataProductAttr[i]=arraylist.get(i);
						}
						
						
						System.out.println("************FeatureFileTableData********************");
						for(int i=0;i<FeatureFileTableData.length;i++){
							System.out.println(FeatureFileTableData[i]);
						}
						System.out.println("************respdataProductAttr*********************");
						for(int i=0;i<respdataProductAttr.length;i++){
							System.out.println(respdataProductAttr[i]);
						}
						if(Arrays.equals(FeatureFileTableData, respdataProductAttr)){
							System.out.println("Both arrays are equal");
						}else{
							System.err.println("Both arrays are not equal");
						}
	}
	
	@And("^the user checks child objects of 'relationships:' are ordered$")
	public void the_user_checks_child_objects_of_relationships_are_ordered(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	   
	}

	@Then("^the user checks the child objects of 'links:' are ordered$")
	public void the_user_checks_the_child_objects_of_links_are_ordered(DataTable arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
	    // E,K,V must be a scalar (String, Integer, Date, enum etc)
	    
	}
	

//===============================================================================================
	
	@When("^there is a new bazaarvoice refresh to GAPI$")
	public void newBazaarVoiceRefreshToGAPI() throws Throwable {
		System.out.println("New Bazaarvoice Refresh to GAPI: ");
	}
	
	private String productsRS_reviews;
	private JsonObject productsRS_productAverageRating;
	private BigDecimal productsRS_ratingValue;
	private long productsRS_ratingCount;
	@Then("^the user should verify the review information of \"([^\"]*)\" and validate with bv_diy_ratings xml file$")
	public void verifyTheReviewInformationAndValidateWith_bv_diy_ratings_xmlFile(String ProductEan) throws Throwable {
		gson=new GsonBuilder().create();
		objectsProductrs = gson.fromJson(latestResponse.getBody(), JsonObject.class);
		
		productsRS_reviews = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
				.getAsJsonPrimitive("reviews").getAsString();
		productsRS_productAverageRating = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
				.getAsJsonObject("productAverageRating");
		
		/*productsRS_ratingValue = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
				.getAsJsonObject("productAverageRating").getAsJsonPrimitive("ratingValue").getAsBigDecimal();*/
		
		String str_productsRS_ratingValue = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
				.getAsJsonObject("productAverageRating").getAsJsonPrimitive("ratingValue").getAsString();
		
		if("".equals(str_productsRS_ratingValue)) {
			productsRS_ratingValue = null;
		}else {
			productsRS_ratingValue = new BigDecimal(str_productsRS_ratingValue);
		}
		
		productsRS_ratingCount = objectsProductrs.getAsJsonObject("data").getAsJsonObject("attributes")
				.getAsJsonObject("productAverageRating").getAsJsonPrimitive("ratingCount").getAsLong();
		
		System.out.println(" \n ::::::::::::EndPoint-Values for::::::::::::  " + ProductEan);
		System.out.println("		\n\t products-rs Endpoint Result: reviews: " + productsRS_reviews + "		\n\t products-rs Endpoint Result: productAverageRating: " + productsRS_productAverageRating + "		\n\t products-rs Endpoint Result: ratingValue: " + productsRS_ratingValue + "		\n\t products-rs Endpoint Result: ratingCount: " + productsRS_ratingCount);
		
	}
	
	@And("^the user checks that reviews: content, ratingValue: and ratingCount: in productAverageRating: matches the <<ProductReviewsUrl, <<AverageOverallRating and <<TotalReviewCount in <<ReviewStatistics for every \"([^\"]*)\" in the latest Bazaarvoice xml file feed$")
	public void MatchEndpointAndBVXmlForProductReviews(String ProductEan) throws Throwable {
	    String FileLocation = "src/test/resources/bv_diy_ratings_090118.xml";
		commonFunctions = new CommonFunctions();
	    commonFunctions.readXMLdocument(FileLocation);
	    commonFunctions.getXMLxpathForProductReviewsFrom_ReviewStatistics(ProductEan); //<ReviewStatistics>
	    //commonFunctions.getXMLxpathForProductReviewsFrom_NativeReviewStatistics(ProductEan); //<NativeReviewStatistics>
	    String ProductReviewsUrl = commonFunctions.productReviewsUrl;
	    
	    double AverageOverallRating = 0.00;
	    double TotalReviewCount = -999;
	    BigDecimal averageOverallRating = null;
	    

	    if(Double.isNaN(commonFunctions.averageOverallRating)) {
	    	System.err.println(" \n\t XML <AverageOverallRating> Mapping to ratingValue: is an EMPTY-STRING"); 
	    }else{
	    	AverageOverallRating = commonFunctions.averageOverallRating;
	    	TotalReviewCount = commonFunctions.totalReviewCount;
	    
	    	averageOverallRating = new BigDecimal(AverageOverallRating);
	    	averageOverallRating = averageOverallRating.setScale(2, RoundingMode.HALF_EVEN);
	    
	    	System.out.println(" \n ::::::::::::BV-XML-Values for::::::::::::  " + ProductEan);
	    	System.out.println("\n\t ProductReviewsUrl : " +ProductReviewsUrl + "\n\t AverageOverallRating : " +AverageOverallRating + "=> RoundingUp to 2dp "+averageOverallRating + "\n\t TotalReviewCount : " +TotalReviewCount);
	    }
	    
	   	if(productsRS_reviews.equals(ProductReviewsUrl)){
	   		System.out.println("Endpoint reviews Url is MATCHING with XML ProductReviewsUrl");
	   	}else{
	   		System.err.println(" \n\t Product Reviews URL is NOT-MATCHING "+" \n\t Endpoint: "+productsRS_reviews + " \n\t BV_XML: "+ProductReviewsUrl);
	   	}	
	   	
	   	
	   	
	   	if(productsRS_ratingValue==null) {
	   	   System.err.println("\n\t Endpoint ratingValue is EMPTY-STRING");    
	   	}else {
	   		
	   			averageOverallRating = new BigDecimal(AverageOverallRating);
	   			averageOverallRating = averageOverallRating.setScale(2, RoundingMode.HALF_EVEN);
	   			//if(productsRS_ratingValue.doubleValue()==AverageOverallRating){
	   			if(productsRS_ratingValue.equals(averageOverallRating)){
	   				System.out.println("Endpoint ratingValue MATCHES to XML AverageOverallRating");
	   			}else {
	   				System.err.println(" \n\t Endpoint ratingValue is NOT-MATCHING to XML AverageOverallRating "+" \n\t Endpoint: "+productsRS_ratingValue + " \n\t BV_XML: "+AverageOverallRating);
		   		}
	   	}
	   	
	   	
	   	if((long)productsRS_ratingCount==(long)TotalReviewCount){
		   	System.out.println("Endpoint ratingCount MATCHES to XML TotalReviewCount");
		}else{
		   	System.err.println(" \n\t Endpoint ratingCount is NOT-MATCHING to XML TotalReviewCount "+" \n\t Endpoint:  "+productsRS_ratingCount + " \n\t BV_XML: "+TotalReviewCount);
		} 	
	}
}


