package com.pim.migration.cucumberRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
				plugin= {"pretty", 
						 "html:target/cucumber-html-report", 
						 "json:target/cucumber-json-report.json", 
						 "usage:target/cucumber-usage.json", 
						 "junit:target/cucumber-junit-report/cucumber-results.xml"},
				features=
						//{"src/main/resources/productService_product-rsService.feature"},
				 		{"src/main/resources/bazaarVoice.feature"},
				 glue=  {"com/pim/migration/stepDefinitions"}
				)
public class ServiceComparison_bvoice_cucumberTest {
}
