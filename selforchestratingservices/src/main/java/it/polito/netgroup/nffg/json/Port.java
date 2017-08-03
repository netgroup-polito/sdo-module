
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "id", "name", "mac", "unify-ip", "trusted", "gui-position" })
public class Port
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("mac")
	private String mac;
	@JsonProperty("unify-ip")
	private String unifyIp;
	@JsonProperty("trusted")
	private Boolean trusted;
	@JsonProperty("gui-position")
	private Object guiPosition;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	public String getId()
	{
		return id;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	public void setId(String id)
	{
		this.id = id;
	}

	@JsonProperty("name")
	public String getName()
	{
		return name;
	}

	@JsonProperty("name")
	public void setName(String name)
	{
		this.name = name;
	}

	@JsonProperty("mac")
	public String getMac()
	{
		return mac;
	}

	@JsonProperty("mac")
	public void setMac(String mac)
	{
		this.mac = mac;
	}

	@JsonProperty("unify-ip")
	public String getUnifyIp()
	{
		return unifyIp;
	}

	@JsonProperty("unify-ip")
	public void setUnifyIp(String unifyIp)
	{
		this.unifyIp = unifyIp;
	}

	@JsonProperty("trusted")
	public Boolean getTrusted()
	{
		return trusted;
	}

	@JsonProperty("trusted")
	public void setTrusted(Boolean trusted)
	{
		this.trusted = trusted;
	}

	@JsonProperty("gui-position")
	public Object getGuiPosition()
	{
		return guiPosition;
	}

	@JsonProperty("gui-position")
	public void setGuiPosition(Object guiPosition)
	{
		this.guiPosition = guiPosition;
	}

}
