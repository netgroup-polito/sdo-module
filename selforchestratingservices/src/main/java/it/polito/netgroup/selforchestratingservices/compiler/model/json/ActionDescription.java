package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionDescription
{
	@JsonProperty("id")
	String id;
	@JsonProperty("params")
	List<ActionParameterDescription> params;
	@JsonProperty("steps")
	List<MacroDescription> steps;
}
