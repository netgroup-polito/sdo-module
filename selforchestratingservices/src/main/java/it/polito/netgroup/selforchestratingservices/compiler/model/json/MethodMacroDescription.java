package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodMacroDescription extends MacroDescription
{
	@JsonProperty("method")
	String method;

	@JsonProperty("on")
	EvaluableStatement on;
	@JsonProperty("params")
	List<MacroDescription> params;
	@Override
	public String getJavaCode()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
