package com.pim.migration.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class ProductResponse {
	
	public final Data data;
	public final Attributes attributes;
	public final RecommendedProductShippingMethods recommendedProductShippingMethods;

	@JsonCreator
	public ProductResponse(@JsonProperty("data") Data data, @JsonProperty("attributes") Attributes attributes, @JsonProperty("recommendedProductShippingMethods") RecommendedProductShippingMethods recommendedProductShippingMethods) {
		this.data = data;
		this.attributes=attributes;
		this.recommendedProductShippingMethods=recommendedProductShippingMethods;
	}
	
	public static final class Data{
	}
	
	public static final class Attributes{
		public final String ean;
		public final String productStatus;
		public final String contactCentreOrderingOnly;
		
		@JsonCreator
		public Attributes(@JsonProperty("ean") String ean, @JsonProperty("productStatus") String productStatus, @JsonProperty("contactCentreOrderingOnly") String contactCentreOrderingOnly) {
			this.ean=ean;
			this.productStatus=productStatus;
			this.contactCentreOrderingOnly=contactCentreOrderingOnly;
		}
	}
	
	public static final class RecommendedProductShippingMethods{
		public final HomeDelivery homeDelivery;
		
		@JsonCreator
		public RecommendedProductShippingMethods(@JsonProperty("homeDelivery") HomeDelivery homeDelivery) {
			this.homeDelivery=homeDelivery;
		}
	
		public static final class HomeDelivery{
			public final String availability;
			public final String shippingMethodId;
			public final String sundayDeliveryAvailable;
			public final String sundayDeliveryMessage;
			
			@JsonCreator
			public HomeDelivery(@JsonProperty("availability") String availability, @JsonProperty("shippingMethodId") String shippingMethodId, @JsonProperty("sundayDeliveryAvailable") String sundayDeliveryAvailable, @JsonProperty("sundayDeliveryMessage") String sundayDeliveryMessage) {
				this.availability=availability;
				this.shippingMethodId=shippingMethodId;
				this.sundayDeliveryAvailable=sundayDeliveryAvailable;
				this.sundayDeliveryMessage=sundayDeliveryMessage;
			}
		}
		public HomeDelivery getHomeDelivery() {
			return homeDelivery;
		}
	}
	
	public Data getData() {
		return data;
	}
	public Attributes getAttributes() {
		return attributes;
	}
	public RecommendedProductShippingMethods getRecommendedProductShippingMethods() {
		return recommendedProductShippingMethods;
	}
	
}
