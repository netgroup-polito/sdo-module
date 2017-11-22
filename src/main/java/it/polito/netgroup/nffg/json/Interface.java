
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "node-id", "if-name" })
public class Interface
{

	@JsonProperty("node-id")
	private String nodeId;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("if-name")
	private String ifName;

	@JsonProperty("node-id")
	public String getNodeId()
	{
		return nodeId;
	}

	@JsonProperty("node-id")
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("if-name")
	public String getIfName()
	{
		return ifName;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("if-name")
	public void setIfName(String ifName)
	{
		this.ifName = ifName;
	}

}
