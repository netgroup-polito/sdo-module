package it.polito.netgroup.configurationorchestrator.json.nat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FloatingIp
{
	@JsonProperty("private_ip")
	private String private_ip;
	
	@JsonProperty("public-ip")
	private String public_ip;

	@JsonProperty("private_ip")
	public String getPrivate_ip()
	{
		return private_ip;
	}

	@JsonProperty("private_ip")
	public void setPrivate_ip(String private_ip)
	{
		this.private_ip = private_ip;
	}

	@JsonProperty("public-ip")
	public String getPublic_ip()
	{
		return public_ip;
	}

	@JsonProperty("public-ip")
	public void setPublic_ip(String public_ip)
	{
		this.public_ip = public_ip;
	}
	
}
