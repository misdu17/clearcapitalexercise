package md.jamaddar.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataArray {
	
	public DataArray() {}
	@JsonProperty("ID State") 
    public String iDState;
	@JsonProperty("State") 
	public String state;
	@JsonProperty("ID Year") 
	public int iDYear;
	@JsonProperty("Year") 
	public String year;
	@JsonProperty("Population") 
	public int population;
	@JsonProperty("Slug State") 
	public String slugState;

}
