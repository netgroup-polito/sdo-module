package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventParameterDescription
{
	@JsonProperty("name")
	String name;
	@JsonProperty("type")
	String type;
}
