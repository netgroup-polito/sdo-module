
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "host-tcp-port", "vnf-tcp-port" })
public class UnifyControl
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("host-tcp-port")
	private Integer hostTcpPort;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-tcp-port")
	private Integer vnfTcpPort;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("host-tcp-port")
	public Integer getHostTcpPort()
	{
		return hostTcpPort;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("host-tcp-port")
	public void setHostTcpPort(Integer hostTcpPort)
	{
		this.hostTcpPort = hostTcpPort;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-tcp-port")
	public Integer getVnfTcpPort()
	{
		return vnfTcpPort;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-tcp-port")
	public void setVnfTcpPort(Integer vnfTcpPort)
	{
		this.vnfTcpPort = vnfTcpPort;
	}

}
