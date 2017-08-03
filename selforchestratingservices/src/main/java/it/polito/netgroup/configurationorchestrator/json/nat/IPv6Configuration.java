package it.polito.netgroup.configurationorchestrator.json.nat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPv6Configuration
{
	@JsonProperty("mac_address")
	private String mac_address;
	@JsonProperty("netmask")
	private String netmask;
	@JsonProperty("address")
	private String address;
	@JsonProperty("default_gw")
	private String default_gw;

	@JsonIgnore
	private ConfigurationType configurationType;

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

	@JsonProperty("netmask")
	public String getNetmask()
	{
		return netmask;
	}

	@JsonProperty("netmask")
	public void setNetmask(String netmask)
	{
		this.netmask = netmask;
	}

	@JsonProperty("address")
	public String getAddress()
	{
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address)
	{
		this.address = address;
	}

	@JsonProperty("default_gw")
	public String getDefault_gw()
	{
		return default_gw;
	}

	@JsonProperty("default_gw")
	public void setDefault_gw(String default_gw)
	{
		this.default_gw = default_gw;
	}

	@JsonProperty("configurationType")
	public String getConfigurationType()
	{
		return configurationType.toString();
	}

	@JsonProperty("configurationType")
	public void setConfigurationType(String configurationType)
	{
		this.configurationType = new ConfigurationType(configurationType);
	}
}