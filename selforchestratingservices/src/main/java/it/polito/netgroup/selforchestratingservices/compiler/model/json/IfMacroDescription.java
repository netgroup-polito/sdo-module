package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IfMacroDescription extends MacroDescription
{
	@JsonProperty("condition")
	IfCondition condition;
	
	@JsonProperty("on_true")
	List<MacroDescription> on_true;
	
	@JsonProperty("on_false")
	List<MacroDescription> on_false;

	@Override
	public String getJavaCode()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
