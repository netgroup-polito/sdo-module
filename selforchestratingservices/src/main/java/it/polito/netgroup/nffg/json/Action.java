
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "output_to_port", "set_vlan_id", "set_vlan_priority", "push_vlan", "pop_vlan", "set_ethernet_src_address",
		"set_ethernet_dst_address", "set_ip_src_address", "set_ip_dst_address", "set_ip_tos", "set_l4_src_port",
		"set_l4_dst_port", "output_to_queue", "drop", "output_to_controller" })
public class Action
{

	@JsonProperty("output_to_port")
	private String outputToPort;
	@JsonProperty("set_vlan_id")
	private String setVlanId;
	@JsonProperty("set_vlan_priority")
	private String setVlanPriority;
	@JsonProperty("push_vlan")
	private String pushVlan;
	@JsonProperty("pop_vlan")
	private Boolean popVlan;
	@JsonProperty("set_ethernet_src_address")
	private String setEthernetSrcAddress;
	@JsonProperty("set_ethernet_dst_address")
	private String setEthernetDstAddress;
	@JsonProperty("set_ip_src_address")
	private String setIpSrcAddress;
	@JsonProperty("set_ip_dst_address")
	private String setIpDstAddress;
	@JsonProperty("set_ip_tos")
	private String setIpTos;
	@JsonProperty("set_l4_src_port")
	private String setL4SrcPort;
	@JsonProperty("set_l4_dst_port")
	private String setL4DstPort;
	@JsonProperty("output_to_queue")
	private String outputToQueue;
	@JsonProperty("drop")
	private Boolean drop;
	@JsonProperty("output_to_controller")
	private Boolean outputToController;

	@JsonProperty("output_to_port")
	public String getOutputToPort()
	{
		return outputToPort;
	}

	@JsonProperty("output_to_port")
	public void setOutputToPort(String outputToPort)
	{
		this.outputToPort = outputToPort;
	}

	@JsonProperty("set_vlan_id")
	public String getSetVlanId()
	{
		return setVlanId;
	}

	@JsonProperty("set_vlan_id")
	public void setSetVlanId(String setVlanId)
	{
		this.setVlanId = setVlanId;
	}

	@JsonProperty("set_vlan_priority")
	public String getSetVlanPriority()
	{
		return setVlanPriority;
	}

	@JsonProperty("set_vlan_priority")
	public void setSetVlanPriority(String setVlanPriority)
	{
		this.setVlanPriority = setVlanPriority;
	}

	@JsonProperty("push_vlan")
	public String getPushVlan()
	{
		return pushVlan;
	}

	@JsonProperty("push_vlan")
	public void setPushVlan(String pushVlan)
	{
		this.pushVlan = pushVlan;
	}

	@JsonProperty("pop_vlan")
	public Boolean getPopVlan()
	{
		return popVlan;
	}

	@JsonProperty("pop_vlan")
	public void setPopVlan(Boolean popVlan)
	{
		this.popVlan = popVlan;
	}

	@JsonProperty("set_ethernet_src_address")
	public String getSetEthernetSrcAddress()
	{
		return setEthernetSrcAddress;
	}

	@JsonProperty("set_ethernet_src_address")
	public void setSetEthernetSrcAddress(String setEthernetSrcAddress)
	{
		this.setEthernetSrcAddress = setEthernetSrcAddress;
	}

	@JsonProperty("set_ethernet_dst_address")
	public String getSetEthernetDstAddress()
	{
		return setEthernetDstAddress;
	}

	@JsonProperty("set_ethernet_dst_address")
	public void setSetEthernetDstAddress(String setEthernetDstAddress)
	{
		this.setEthernetDstAddress = setEthernetDstAddress;
	}

	@JsonProperty("set_ip_src_address")
	public String getSetIpSrcAddress()
	{
		return setIpSrcAddress;
	}

	@JsonProperty("set_ip_src_address")
	public void setSetIpSrcAddress(String setIpSrcAddress)
	{
		this.setIpSrcAddress = setIpSrcAddress;
	}

	@JsonProperty("set_ip_dst_address")
	public String getSetIpDstAddress()
	{
		return setIpDstAddress;
	}

	@JsonProperty("set_ip_dst_address")
	public void setSetIpDstAddress(String setIpDstAddress)
	{
		this.setIpDstAddress = setIpDstAddress;
	}

	@JsonProperty("set_ip_tos")
	public String getSetIpTos()
	{
		return setIpTos;
	}

	@JsonProperty("set_ip_tos")
	public void setSetIpTos(String setIpTos)
	{
		this.setIpTos = setIpTos;
	}

	@JsonProperty("set_l4_src_port")
	public String getSetL4SrcPort()
	{
		return setL4SrcPort;
	}

	@JsonProperty("set_l4_src_port")
	public void setSetL4SrcPort(String setL4SrcPort)
	{
		this.setL4SrcPort = setL4SrcPort;
	}

	@JsonProperty("set_l4_dst_port")
	public String getSetL4DstPort()
	{
		return setL4DstPort;
	}

	@JsonProperty("set_l4_dst_port")
	public void setSetL4DstPort(String setL4DstPort)
	{
		this.setL4DstPort = setL4DstPort;
	}

	@JsonProperty("output_to_queue")
	public String getOutputToQueue()
	{
		return outputToQueue;
	}

	@JsonProperty("output_to_queue")
	public void setOutputToQueue(String outputToQueue)
	{
		this.outputToQueue = outputToQueue;
	}

	@JsonProperty("drop")
	public Boolean getDrop()
	{
		return drop;
	}

	@JsonProperty("drop")
	public void setDrop(Boolean drop)
	{
		this.drop = drop;
	}

	@JsonProperty("output_to_controller")
	public Boolean getOutputToController()
	{
		return outputToController;
	}

	@JsonProperty("output_to_controller")
	public void setOutputToController(Boolean outputToController)
	{
		this.outputToController = outputToController;
	}

}
