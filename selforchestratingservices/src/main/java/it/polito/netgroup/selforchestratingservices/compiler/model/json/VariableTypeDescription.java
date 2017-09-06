package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableTypeDescription
{
	public enum TYPE
	{
		entry
	}
	@JsonProperty("name")
	String name;
	@JsonProperty("type")
	TYPE type;
	@JsonProperty("params")
	List<String> params;
	
	public String getJavaCode(String pkg)
	{
		//PACKAGE
		String java = "package "+pkg+";\n"
				+ "\n";
		
		//START CLASS
		java = java	+ "public class "+name+"\n"
				+ "{\n";
		
		//PRIVATE PROPERTIES
		for(String param : params)
		{
			java = java + "private " + param + " " + param.toLowerCase()+ "\n";
		}
		
		//CONSTRUCTOR
		java = java	+ "\tpublic "+name+"(\n";
		
		for(String param : params)
		{
			java = java + param + " _" + param.toLowerCase()+ " ";
		}
		
		java = java	+ ")\n"
				+ "\t{\n";
		
		for(String param : params)
		{
			java = java + "\t\t" + param.toLowerCase() + " = _" + param.toLowerCase()+ "\n";
		}		
		java = java + "\t}\n";
				
		//GETTER
		for(String param : params)
		{
			java = java	+ "\tpublic "+param+" get"+param+"()\n"
				+ "\t{\n"
				+ "\t\treturn "+param+";\n"
				+ "\t}\n";
		}
		
		//CLOSE CLASS
		java = java + "}\n";
		
		return java;
	}
}
