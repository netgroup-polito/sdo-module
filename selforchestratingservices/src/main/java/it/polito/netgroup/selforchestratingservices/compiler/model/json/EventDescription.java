package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventDescription
{
	public enum TYPE
	{
		polling,
		internal,
	};
	
	@JsonProperty("id")
	String id;
	@JsonProperty("type")
	TYPE type;
	@JsonProperty("check_every")
	String check_every;
	@JsonProperty("check_method")
	String check_method;
	@JsonProperty("check_on")
	List<EventCheckOnDescription> check_on;
	@JsonProperty("params")
	List<EventParameterDescription> params;
	@JsonProperty("actions")
	Map<String,List<String>> actions;

	
	//TODO
	
}
