package it.polito.netgroup.configurationorchestrator.json.nat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArpEntry
{
	@JsonProperty("ip_address")
	private String ip_address;
	@JsonProperty("mac_address")
	private String mac_address;

	@JsonProperty("ip_address")	
	public String getIp_address()
	{
		return ip_address;
	}

	@JsonProperty("ip_address")
	public void setIp_address(String ip_address)
	{
		this.ip_address = ip_address;
	}

	@JsonProperty("mac_address")
	public String getMac_address()
	{
		return mac_address;
	}

	@JsonProperty("mac_address")
	public void setMac_address(String mac_address)
	{
		this.mac_address = mac_address;
	}
	
	
	
}
