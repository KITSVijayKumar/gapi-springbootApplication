package com.pim.migration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@org.springframework.context.annotation.Configuration
@PropertySource({ "classpath:application.properties" })
@ConfigurationProperties("application")
@ComponentScan({ "org.pim-rs" })
public class Configuration {

		
	@Value("${applicationUrl}")
	protected String applicationUrl;
	
	@Value("${productLocationUrl}")
	protected String productLocationUrl;
	
	@Value("${productUrl}")
	protected String productUrl;
	
	@Value("${product_rs_Url}")
	protected String product_rs_Url;
	
	@Value("${product_prodn_Url}")
	protected String product_prodn_Url;
	
	@Value("${stockUrlStage}")
	protected String stockUrlStage;
	
	
	public String getApplicationUrl(){
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl){
		this.applicationUrl=applicationUrl;
	}

	
	public String getProductLocationUrl() {
		return productLocationUrl;
	}
	public void setProductLocationUrl(String productLocationUrl) {
		this.productLocationUrl=productLocationUrl;
	}
	
		
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl=productUrl;
	}
	
	
	public String getProduct_rs_Url() {
		return product_rs_Url;
	}
	public void setProduct_rs_Url(String product_rs_Url) {
		this.product_rs_Url=product_rs_Url;
	}
	
	
	public String getProduct_prodn_Url() {
		return product_prodn_Url;
	}
	public void setProduct_prodn_Url(String product_prodn_Url) {
		this.product_prodn_Url=product_prodn_Url;
	}
	
	
	public String getStockUrlStage() {
		return stockUrlStage;
	}
	public void setStockUrlStage(String stockUrlStage) {
		this.stockUrlStage=stockUrlStage;
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer
	propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
