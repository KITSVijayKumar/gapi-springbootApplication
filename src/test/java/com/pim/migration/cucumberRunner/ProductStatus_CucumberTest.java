package com.pim.migration.cucumberRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/productStatus.feature",
				plugin = {"pretty", 
						"html:target/cucumber-html-report", 
						"json:target/cucumber-json-report.json", 
						"pretty:target/cucumber-pretty.txt", 
						"usage:target/cucumber-usage.json", 
						"junit:target/cucumber-junit-report/cucumber-results.xml"},
				glue = {"com/pim/migration/stepDefinitions"}			
				)
public class ProductStatus_CucumberTest {
}
