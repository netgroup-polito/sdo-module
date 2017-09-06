package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelfOrchestratorModel
{
	@JsonProperty("name")
	String name;
	@JsonProperty("types")
	List<VariableTypeDescription> types;
	@JsonProperty("variables")
	List<VariableDescription> variables;
	@JsonProperty("events")
	List<EventDescription> events;
	@JsonProperty("actions")
	List<ActionDescription> actions;
	@JsonProperty("elementaryServices")
	List<ElementaryServiceDescription> elementaryServices;
}
