package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EvaluableStatement
{
	@JsonIgnore
	String value;
	
	@JsonCreator
	public EvaluableStatement(MacroDescription macro)
	{
		value = macro.getJavaCode();
	}
	
	@JsonCreator
	public EvaluableStatement(String _value)
	{
		value = "\""+_value+"\"";
	}
	
	
	public String getJavaValue()
	{
		return value;
	}
}
