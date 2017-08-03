
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "vlan-id", "if-name", "node-id" })
public class Vlan
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vlan-id")
	private String vlanId;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("if-name")
	private String ifName;
	@JsonProperty("node-id")
	private String nodeId;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vlan-id")
	public String getVlanId()
	{
		return vlanId;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vlan-id")
	public void setVlanId(String vlanId)
	{
		this.vlanId = vlanId;
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
