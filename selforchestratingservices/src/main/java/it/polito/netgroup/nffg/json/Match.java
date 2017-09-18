
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "hard_timeout", "ether_type", "vlan_id", "vlan_priority", "arp_spa", "arp_tpa", "source_mac", "dest_mac", "source_ip",
		"dest_ip", "tos_bits", "source_port", "dest_port", "protocol", "port_in" })
public class Match
{

	@JsonProperty("hard_timeout")
	private String hardTimeout;
	@JsonProperty("ether_type")
	private String etherType;
	@JsonProperty("vlan_id")
	private String vlanId;
	@JsonProperty("vlan_priority")
	private String vlanPriority;
	@JsonProperty("arp_spa")
	private String arpSpa;
	@JsonProperty("arp_tpa")
	private String arpTpa;
	@JsonProperty("source_mac")
	private String sourceMac;
	@JsonProperty("dest_mac")
	private String destMac;
	@JsonProperty("source_ip")
	private String sourceIp;
	@JsonProperty("dest_ip")
	private String destIp;
	@JsonProperty("tos_bits")
	private String tosBits;
	@JsonProperty("source_port")
	private String sourcePort;
	@JsonProperty("dest_port")
	private String destPort;
	@JsonProperty("protocol")
	private String protocol;
	@JsonProperty("port_in")
	private String portIn;


	public Match()
	{

	}

	public Match(String portIn, String sourceMac, String destMac) {
		this.sourceMac = sourceMac;
		this.destMac = destMac;
		this.portIn = portIn;
	}

	@JsonProperty("hard_timeout")
	public String getHardTimeout()
	{
		return hardTimeout;
	}

	@JsonProperty("hard_timeout")
	public void setHardTimeout(String hardTimeout)
	{
		this.hardTimeout = hardTimeout;
	}

	@JsonProperty("ether_type")
	public String getEtherType()
	{
		return etherType;
	}

	@JsonProperty("ether_type")
	public void setEtherType(String etherType)
	{
		this.etherType = etherType;
	}

	@JsonProperty("vlan_id")
	public String getVlanId()
	{
		return vlanId;
	}

	@JsonProperty("vlan_id")
	public void setVlanId(String vlanId)
	{
		this.vlanId = vlanId;
	}

	@JsonProperty("vlan_priority")
	public String getVlanPriority()
	{
		return vlanPriority;
	}

	@JsonProperty("vlan_priority")
	public void setVlanPriority(String vlanPriority)
	{
		this.vlanPriority = vlanPriority;
	}

	@JsonProperty("arp_spa")
	public String getArpSpa()
	{
		return arpSpa;
	}

	@JsonProperty("arp_spa")
	public void setArpSpa(String arpSpa)
	{
		this.arpSpa = arpSpa;
	}

	@JsonProperty("arp_tpa")
	public String getArpTpa()
	{
		return arpTpa;
	}

	@JsonProperty("arp_tpa")
	public void setArpTpa(String arpTpa)
	{
		this.arpTpa = arpTpa;
	}

	@JsonProperty("source_mac")
	public String getSourceMac()
	{
		return sourceMac;
	}

	@JsonProperty("source_mac")
	public void setSourceMac(String sourceMac)
	{
		this.sourceMac = sourceMac;
	}

	@JsonProperty("dest_mac")
	public String getDestMac()
	{
		return destMac;
	}

	@JsonProperty("dest_mac")
	public void setDestMac(String destMac)
	{
		this.destMac = destMac;
	}

	@JsonProperty("source_ip")
	public String getSourceIp()
	{
		return sourceIp;
	}

	@JsonProperty("source_ip")
	public void setSourceIp(String sourceIp)
	{
		this.sourceIp = sourceIp;
	}

	@JsonProperty("dest_ip")
	public String getDestIp()
	{
		return destIp;
	}

	@JsonProperty("dest_ip")
	public void setDestIp(String destIp)
	{
		this.destIp = destIp;
	}

	@JsonProperty("tos_bits")
	public String getTosBits()
	{
		return tosBits;
	}

	@JsonProperty("tos_bits")
	public void setTosBits(String tosBits)
	{
		this.tosBits = tosBits;
	}

	@JsonProperty("source_port")
	public String getSourcePort()
	{
		return sourcePort;
	}

	@JsonProperty("source_port")
	public void setSourcePort(String sourcePort)
	{
		this.sourcePort = sourcePort;
	}

	@JsonProperty("dest_port")
	public String getDestPort()
	{
		return destPort;
	}

	@JsonProperty("dest_port")
	public void setDestPort(String destPort)
	{
		this.destPort = destPort;
	}

	@JsonProperty("protocol")
	public String getProtocol()
	{
		return protocol;
	}

	@JsonProperty("protocol")
	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	@JsonProperty("port_in")
	public String getPortIn()
	{
		return portIn;
	}

	@JsonProperty("port_in")
	public void setPortIn(String portIn)
	{
		this.portIn = portIn;
	}

}
