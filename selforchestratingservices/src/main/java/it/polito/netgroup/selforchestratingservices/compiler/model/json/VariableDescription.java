package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableDescription
{
	@JsonProperty("name")
	String name;
	@JsonProperty("type")
	String type;
	@JsonProperty("key_of")
	String key_of;
	@JsonProperty("value_of")
	String value_of;
	@JsonProperty("list_of")
	String list_of;
	@JsonProperty("timeout")
	String timeout;
	@JsonProperty("value")
	String value;
	
	//TODO
}
