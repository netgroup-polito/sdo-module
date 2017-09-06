package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventCheckOnDescription
{
	@JsonProperty("type")
	String type;
	@JsonProperty("name")
	String name;
}
