
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "internal-group", "node-id" })
public class Internal
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("internal-group")
	private String internalGroup;
	@JsonProperty("node-id")
	private String nodeId;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("internal-group")
	public String getInternalGroup()
	{
		return internalGroup;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("internal-group")
	public void setInternalGroup(String internalGroup)
	{
		this.internalGroup = internalGroup;
	}

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

}
