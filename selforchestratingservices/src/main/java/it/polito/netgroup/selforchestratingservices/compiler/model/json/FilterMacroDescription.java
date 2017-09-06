package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterMacroDescription extends MacroDescription
{	
	@JsonProperty("filter_method")
	String filter_method;
	@JsonProperty("equal_to")
	EvaluableStatement equal_to;
	
	@JsonProperty("on")
	EvaluableStatement on;

	@Override
	public String getJavaCode()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
