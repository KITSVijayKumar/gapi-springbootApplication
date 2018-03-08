package com.pim.migration.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductLocationResponse {
	
	public final Data data;
	public final Attributes attributes;
	public final Locations locations[];
	
	@JsonCreator
	public ProductLocationResponse(@JsonProperty("data") Data data, @JsonProperty("attributes") Attributes attributes, @JsonProperty("locations") Locations[] locations) {
		this.data = data;
		this.attributes=attributes;
		this.locations=locations;
	}
	
	public static final class Data{
		public final long id;
		public final String type;
		
		@JsonCreator
		public Data(@JsonProperty("id") long id, @JsonProperty("type") String type){
			this.id=id;
			this.type=type;
		}
	}
	public static final class Attributes{
		public final long ean;
	
		@JsonCreator
		public Attributes(@JsonProperty("ean") long ean) {
			this.ean=ean;
		}
	}
	public static final class Locations{
		public final short aisle;
		public final int bay;
		public final String availableLocationType;
		public final String binType;
		
		@JsonCreator
		public Locations(@JsonProperty("aisle") short aisle, @JsonProperty("bay") int bay, @JsonProperty("availableLocationType") String availableLocationType, @JsonProperty("binType") String binType) {
			this.aisle=aisle;
			this.bay=bay;
			this.availableLocationType=availableLocationType;
			this.binType=binType;
		}
	}
	
	public Data getData() {
		return data;
	}
	public Attributes getAttributes() {
		return attributes;
	}
	public Locations[] getLocations() {
		return locations;
	}
}
