
package it.polito.netgroup.nffg.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "id", "name", "domain", "gui-position", "type", "host-stack", "internal", "interface", "interface-out", "gre-tunnel",
		"vlan" })
public class EndPoint
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
	@JsonProperty("domain")
	private String domain;
	@JsonProperty("gui-position")
	private Object guiPosition;
	@JsonProperty("type")
	private EndPoint.Type type;
	@JsonProperty("host-stack")
	private HostStack hostStack;
	@JsonProperty("internal")
	private Internal internal;
	@JsonProperty("interface")
	private Interface _interface;
	@JsonProperty("interface-out")
	private InterfaceOut interfaceOut;
	@JsonProperty("gre-tunnel")
	private GreTunnel greTunnel;
	@JsonProperty("vlan")
	private Vlan vlan;

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

	@JsonProperty("domain")
	public String getDomain()
	{
		return domain;
	}

	@JsonProperty("domain")
	public void setDomain(String domain)
	{
		this.domain = domain;
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

	@JsonProperty("type")
	public EndPoint.Type getType()
	{
		return type;
	}

	@JsonProperty("type")
	public void setType(EndPoint.Type type)
	{
		this.type = type;
	}

	@JsonProperty("host-stack")
	public HostStack getHostStack()
	{
		return hostStack;
	}

	@JsonProperty("host-stack")
	public void setHostStack(HostStack hostStack)
	{
		this.hostStack = hostStack;
	}

	@JsonProperty("internal")
	public Internal getInternal()
	{
		return internal;
	}

	@JsonProperty("internal")
	public void setInternal(Internal internal)
	{
		this.internal = internal;
	}

	@JsonProperty("interface")
	public Interface getInterface()
	{
		return _interface;
	}

	@JsonProperty("interface")
	public void setInterface(Interface _interface)
	{
		this._interface = _interface;
	}

	@JsonProperty("interface-out")
	public InterfaceOut getInterfaceOut()
	{
		return interfaceOut;
	}

	@JsonProperty("interface-out")
	public void setInterfaceOut(InterfaceOut interfaceOut)
	{
		this.interfaceOut = interfaceOut;
	}

	@JsonProperty("gre-tunnel")
	public GreTunnel getGreTunnel()
	{
		return greTunnel;
	}

	@JsonProperty("gre-tunnel")
	public void setGreTunnel(GreTunnel greTunnel)
	{
		this.greTunnel = greTunnel;
	}

	@JsonProperty("vlan")
	public Vlan getVlan()
	{
		return vlan;
	}

	@JsonProperty("vlan")
	public void setVlan(Vlan vlan)
	{
		this.vlan = vlan;
	}

	public enum Type
	{

		HOST_STACK("host-stack"), INTERNAL("internal"), INTERFACE("interface"), INTERFACE_OUT(
				"interface-out"), GRE_TUNNEL("gre-tunnel"), VLAN("vlan");
		private final String value;
		private final static Map<String, EndPoint.Type> CONSTANTS = new HashMap<String, EndPoint.Type>();

		static
		{
			for (EndPoint.Type c : values())
			{
				CONSTANTS.put(c.value, c);
			}
		}

		private Type(String value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return this.value;
		}

		@JsonValue
		public String value()
		{
			return this.value;
		}

		@JsonCreator
		public static EndPoint.Type fromValue(String value)
		{
			EndPoint.Type constant = CONSTANTS.get(value);
			if (constant == null)
			{
				throw new IllegalArgumentException(value);
			} else
			{
				return constant;
			}
		}

	}

}
