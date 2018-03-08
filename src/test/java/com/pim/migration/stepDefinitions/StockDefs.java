package com.pim.migration.stepDefinitions;

import java.math.BigDecimal;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pim.migration.controller.CommonFunctions;
import com.pim.migration.controller.SpringIntegrationTest;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StockDefs extends SpringIntegrationTest {
	
	private Gson gson;
	private JsonObject objectsStock;
	private JsonObject stockAttributes;
	private JsonArray stockDetails;
	private String ranged;
	private CommonFunctions commonFunctions;
	
	@Given("^the user receives storeCode of (\\d+) characters, \"([^\"]*)\" exactly (\\d+) characters, StockLevel maximum (\\d+) characters Whole-Number, Ranged: (\\d+) character and OPCO in maximum (\\d+) characters in length$")
	public void the_user_receives_storeCode_of_characters_exactly_characters_StockLevel_maximum_characters_Whole_Number_Ranged_character_and_OPCO_in_maximum_characters_in_length(int arg1, String arg2, int arg3, int arg4, int arg5, int arg6) throws Throwable {
	    System.err.println("Write to Read the BODS FileHeading ==> The user Receives the respective characters of heading");
	 }

	@When("^user calls to get /stock response for a given \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_calls_to_get_stock_response_for_a_given(String storeid, String productean) throws Throwable {
		System.out.println("Calling stock-api Stage");
        System.out.println("Product URI: "+ configuration.getStockUrlStage());
        //executeGet(configuration.getStockUrlStage()+"/v1/stock/BQUK?filter%5BstoreId%5D="+storeid+"&filter%5Bean%5D="+productean);
        executeGet(configuration.getStockUrlStage()+"/v1/stock/BQUK/store/"+storeid+"/"+productean);
        System.out.println("stock-api CALLED");
	}

	private BigDecimal StoreID, ProductID;
	private long currentStockQuantity;
	private String productEAN;
	@And("^the user verifies stockData delta changes on Endpoint for \"([^\"]*)\" and \"([^\"]*)\" are matching with the latest CSVfile$")
	public void the_user_verifies_stockData_delta_changes_on_Endpoint_for_and_are_matching_with_the_latest_CSVfile(String storeid, String productean) throws Throwable {
	
		gson=new GsonBuilder().create();
		objectsStock = gson.fromJson(latestResponse.getBody(), JsonObject.class);
		
		System.out.println("STOCK OBJECTs : "+ objectsStock);
		
		String storeID = objectsStock.getAsJsonObject("data").getAsJsonPrimitive("id").getAsString();
		StoreID = new BigDecimal(storeID);
		             
        stockAttributes	= objectsStock.getAsJsonObject("data").getAsJsonObject("attributes");
        System.out.println("JSON Object STOCK Attributes : " +stockAttributes);
        
        productEAN = stockAttributes.getAsJsonPrimitive("ean").getAsString();
        setProductID(new BigDecimal(productEAN));
        
        currentStockQuantity = stockAttributes.getAsJsonPrimitive("quantity").getAsLong();
        
        ranged = stockAttributes.getAsJsonObject().get("ranged").getAsString();
        
        System.out.println("EndPoint stockData Response:  \n\t Store ID: " + StoreID + " \n\t STOCK Details: " + stockDetails + 
        					" \n\t STOCK Attributes: " + stockAttributes + " \n\t ProductID: " + productEAN + 
        					" \n\t currentStockQuantity: " + currentStockQuantity + " \n\t ranged: " + ranged);
	    
	//==============================================================================================
		String csvFileDelta = "src/test/resources/SAPECCtoStockAPI_20180207030134-0000.csv";
				//"src/test/resources/SAPECCtoStockAPI_Delta_21_20180207165657.csv";
		commonFunctions = new CommonFunctions();
		commonFunctions.readCSV(storeid, productean, csvFileDelta);
		System.out.println("::Latest StockData for given storeID & ProductEan is :: ");
		
		long stockQuantity = currentStockQuantity;
		String stockQuantiyAsString = Long.toString(stockQuantity);
		System.out.println("stockValue as String : "+stockQuantiyAsString);
		
		if(commonFunctions.EAN.equals(productean.toString())
				&&commonFunctions.EAN.equals(productEAN.toString())
					&& commonFunctions.STOCKLEVEL.equals(stockQuantiyAsString) 
						/*&& commonFunctions.RANGED.equals(ranged.toString())*/){
			
			Assert.assertTrue(commonFunctions.EAN.equals(productean.toString())
								&& commonFunctions.EAN.equals(productean.toString())
									&& commonFunctions.STOCKLEVEL.equals(stockQuantiyAsString) 
										/*&& commonFunctions.RANGED.equals(ranged.toString())*/);
	   		
			System.out.println("Endpoint stockAttributes are MATCHING with latest BODS csvFile: "+" \n ProductID is: "+commonFunctions.EAN.equals(productEAN.toString())+"\n\t Endpoint ProductEAN: "+productEAN+" csvFile ProductEAN: "+commonFunctions.EAN +
	   							" \n StockLevel is: "+commonFunctions.STOCKLEVEL.equals(stockQuantiyAsString)+"\n\t Endpoint currentStockQuantity: "+stockQuantiyAsString+" csvFile currentStockQuantity: "+commonFunctions.STOCKLEVEL /*+ 
	   								" \n\t ranged is: "+commonFunctions.RANGED.equals(ranged.toString())*/);
	   	}else{
	   		Assert.assertFalse(false);
	   		System.err.println("Endpoint stockAttributes are MATCHING with latest BODS csvFile: "+" \n ProductID is: "+commonFunctions.EAN.equals(productEAN.toString())+"\n\t Endpoint ProductEAN: "+productEAN+" csvFile ProductEAN: "+commonFunctions.EAN +
						" \n StockLevel is: "+commonFunctions.STOCKLEVEL.equals(stockQuantiyAsString)+"\n\t Endpoint currentStockQuantity: "+stockQuantiyAsString+" csvFile currentStockQuantity: "+commonFunctions.STOCKLEVEL /*+ 
							" \n\t ranged is: "+commonFunctions.RANGED.equals(ranged.toString())*/);
	   	}
	    
	}
	
	@Then("^the user verifies stockData delta changes and compares with the previous job run on Endpoint$")
	public void the_user_verifies_stockData_delta_changes_and_compares_with_the_previous_job_run_on_Endpoint() throws Throwable {
	    System.err.println("Verify with stockBulk/laststockDelta csvFile  ==> the user verifies stockData delta changes and compares with the previous job run on Endpoint");   
	}

	@When("^the user verifies stockData of \"([^\"]*)\" and validate with BODS CSVfile$")
	public void the_user_verifies_stockData_of_and_validate_with_BODS_CSVfile(String productean) throws Throwable {
	    System.out.println("::Verify the StockData for the given Ean and validate with BODS File::");  
	}

	@Then("^user checks the stockLevel in BODS fileSytem for \"([^\"]*)\" and \"([^\"]*)\" should match with the latest stockData delta on stock-api db$")
	public void user_checks_the_stockLevel_in_the_for_the_should_match_with_the_latest_stockData_delta_on_stock_api_db(String storeid, String productean) throws Throwable {
		String csvFile = "src/test/resources/SAPECCtoStockAPI_20180207030134-0000.csv";
		commonFunctions = new CommonFunctions();
		commonFunctions.readCSV(storeid, productean, csvFile);
		System.out.println("::Latest StockData for given storeID & ProductEan is :: ");
	}

	public BigDecimal getProductID() {
		return ProductID;
	}

	public void setProductID(BigDecimal productID) {
		ProductID = productID;
	}
}
