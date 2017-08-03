package it.polito.netgroup.configurationorchestrator.json.nat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NatSession
{
	public enum TCP_STATE{
        NONE,
        SYN_SENT,
        SYN_RECV,
        ESTABLISHED,
        FIN_WAIT,
        CLOSE_WAIT,
        LAST_ACK,
        TIME_WAIT,
        CLOSE,
        LISTEN
	};
	
	public enum PROTOCOL {
		ICMP,
		UDP,
		TCP
	};
	
	@JsonProperty("protocol")
	private PROTOCOL protocol;
	@JsonProperty("src_address")
	private String src_address;
	@JsonProperty("dst_address")
	private String dst_address;
	@JsonProperty("src_port")
	private int src_port;
	@JsonProperty("dst_port")
	private int dst_port;
	@JsonProperty("translated_address")
	private String translated_address;
	@JsonProperty("translated_port")
	private int translated_port;
	@JsonProperty("tcp_state")
	private TCP_STATE tcp_state;

	public PROTOCOL getProtocol()
	{
		return protocol;
	}

	public void setProtocol(PROTOCOL protocol)
	{
		this.protocol = protocol;
	}

	public String getSrc_address()
	{
		return src_address;
	}

	public void setSrc_address(String src_address)
	{
		this.src_address = src_address;
	}

	public String getDst_address()
	{
		return dst_address;
	}

	public void setDst_address(String dst_address)
	{
		this.dst_address = dst_address;
	}

	public int getSrc_port()
	{
		return src_port;
	}

	public void setSrc_port(int src_port)
	{
		this.src_port = src_port;
	}

	public int getDst_port()
	{
		return dst_port;
	}

	public void setDst_port(int dst_port)
	{
		this.dst_port = dst_port;
	}

	public String getTranslated_address()
	{
		return translated_address;
	}

	public void setTranslated_address(String translated_address)
	{
		this.translated_address = translated_address;
	}

	public int getTranslated_port()
	{
		return translated_port;
	}

	public void setTranslated_port(int translated_port)
	{
		this.translated_port = translated_port;
	}

	public TCP_STATE getTcp_state()
	{
		return tcp_state;
	}

	public void setTcp_state(TCP_STATE tcp_state)
	{
		this.tcp_state = tcp_state;
	}
	
}
