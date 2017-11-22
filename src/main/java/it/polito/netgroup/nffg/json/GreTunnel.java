
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "local-ip", "remote-ip", "ttl", "gre-key", "secure" })
public class GreTunnel
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("local-ip")
	private String localIp;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("remote-ip")
	private String remoteIp;
	@JsonProperty("ttl")
	private String ttl;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("gre-key")
	private String greKey;
	@JsonProperty("secure")
	private Boolean secure;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("local-ip")
	public String getLocalIp()
	{
		return localIp;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("local-ip")
	public void setLocalIp(String localIp)
	{
		this.localIp = localIp;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("remote-ip")
	public String getRemoteIp()
	{
		return remoteIp;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("remote-ip")
	public void setRemoteIp(String remoteIp)
	{
		this.remoteIp = remoteIp;
	}

	@JsonProperty("ttl")
	public String getTtl()
	{
		return ttl;
	}

	@JsonProperty("ttl")
	public void setTtl(String ttl)
	{
		this.ttl = ttl;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("gre-key")
	public String getGreKey()
	{
		return greKey;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("gre-key")
	public void setGreKey(String greKey)
	{
		this.greKey = greKey;
	}

	@JsonProperty("secure")
	public Boolean getSecure()
	{
		return secure;
	}

	@JsonProperty("secure")
	public void setSecure(Boolean secure)
	{
		this.secure = secure;
	}

}
