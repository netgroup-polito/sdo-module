package it.polito.netgroup.configurationorchestrator.json.transcoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IfEntry
{
	private enum type {
		L3,
		trasparent,
		not_defined
	};

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("type")
	private type type;
	@JsonProperty("management")
	private boolean management;
	@JsonProperty("ipv4_configuration")
	private IPv4Configuration ipv4_configuration;
	@JsonProperty("ipv6_configuration")
	private IPv6Configuration ipv6_configuration;


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public type getType()
	{
		return type;
	}

	public void setType(type type)
	{
		this.type = type;
	}

	public boolean isManagement()
	{
		return management;
	}

	public void setManagement(boolean management)
	{
		this.management = management;
	}

	public IPv4Configuration getIpv4_configuration()
	{
		return ipv4_configuration;
	}

	public void setIpv4_configuration(IPv4Configuration ipv4_configuration)
	{
		this.ipv4_configuration = ipv4_configuration;
	}


	public IPv6Configuration getIpv6_configuration()
	{
		return ipv6_configuration;
	}

	
	public void setIpv6_configuration(IPv6Configuration ipv6_configuration)
	{
		this.ipv6_configuration = ipv6_configuration;
	}
	
	@JsonProperty("id")
	public String getId()
	{
		return id;
	}

	@JsonProperty("id")
	public void setId(String id)
	{
		this.id = id;
	}
	
}
