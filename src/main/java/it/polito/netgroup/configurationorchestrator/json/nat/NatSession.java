package it.polito.netgroup.configurationorchestrator.json.nat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.EqualKeyObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NatSession extends EqualKeyObject
{
	@Override
	public Boolean equalKey(EqualKeyObject obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		NatSession other = (NatSession) obj;

		return protocol.equals(other.protocol) &&
						src_address.equals(other.src_address) &&
						dst_address.equals(other.dst_address) &&
						src_port == other.src_port &&
						dst_port == other.dst_port &&
						translated_address.equals(other.translated_address) &&
						translated_port == other.translated_port;
	}

	@Override
	public EqualKeyObject copy() {
		NatSession natSession = new NatSession();

		natSession.protocol = this.protocol;
		natSession.src_address = this.src_address;
		natSession.dst_address = this.dst_address;
		natSession.src_port = this.src_port;
		natSession.dst_port = this.dst_port;
		natSession.translated_address = this.translated_address;
		natSession.translated_port = this.translated_port;
		natSession.tcp_state = this.tcp_state;

		return natSession;
	}

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
	}

	public enum PROTOCOL {
		ICMP,
		UDP,
		TCP
	}

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
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dst_address == null) ? 0 : dst_address.hashCode());
		result = prime * result + dst_port;
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + ((src_address == null) ? 0 : src_address.hashCode());
		result = prime * result + src_port;
		result = prime * result + ((translated_address == null) ? 0 : translated_address.hashCode());
		result = prime * result + translated_port;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NatSession other = (NatSession) obj;
		if (dst_address == null)
		{
			if (other.dst_address != null)
				return false;
		} else if (!dst_address.equals(other.dst_address))
			return false;
		if (dst_port != other.dst_port)
			return false;
		if (protocol != other.protocol)
			return false;
		if (src_address == null)
		{
			if (other.src_address != null)
				return false;
		} else if (!src_address.equals(other.src_address))
			return false;
		if (src_port != other.src_port)
			return false;
		if (translated_address == null)
		{
			if (other.translated_address != null)
				return false;
		} else if (!translated_address.equals(other.translated_address))
			return false;
		return translated_port == other.translated_port;
	}	
}
