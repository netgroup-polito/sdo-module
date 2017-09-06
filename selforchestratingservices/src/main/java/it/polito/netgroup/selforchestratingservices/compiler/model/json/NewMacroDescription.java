package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewMacroDescription extends MacroDescription
{
	@JsonProperty("type")
	String type;
	
	@JsonProperty("params")
	List<MacroDescription> params;

	@Override
	public String getJavaCode()
	{
		String java = " new "+type+"(\n";
		
		for(MacroDescription param : params)
		{
			java = java + param.getJavaCode() + ",";
		}
		java = java.substring(0, java.length() - 1);
		
		java = java + ");\n";
		
		return java;
	}	
}
