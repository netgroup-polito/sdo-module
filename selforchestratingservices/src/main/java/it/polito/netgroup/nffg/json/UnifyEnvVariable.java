
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "variable" })
public class UnifyEnvVariable
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("variable")
	private String variable;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("variable")
	public String getVariable()
	{
		return variable;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("variable")
	public void setVariable(String variable)
	{
		this.variable = variable;
	}

}
