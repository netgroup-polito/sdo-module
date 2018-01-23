
package it.polito.netgroup.nffg_template.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "position", "label", "min", "ipv4-config", "ipv6-config", "name", "technology" })
public class Port
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("position")
	private String position;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("label")
	private String label;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("min")
	private String min;
	@JsonProperty("ipv4-config")
	private String ipv4Config;
	@JsonProperty("ipv6-config")
	private String ipv6Config;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("name")
	private String name;
	@JsonProperty("technology")
	private Port.Technology technology;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("position")
	public String getPosition()
	{
		return position;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("position")
	public void setPosition(String position)
	{
		this.position = position;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("label")
	public String getLabel()
	{
		return label;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("label")
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("min")
	public String getMin()
	{
		return min;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("min")
	public void setMin(String min)
	{
		this.min = min;
	}

	@JsonProperty("ipv4-config")
	public String getIpv4Config()
	{
		return ipv4Config;
	}

	@JsonProperty("ipv4-config")
	public void setIpv4Config(String ipv4Config)
	{
		this.ipv4Config = ipv4Config;
	}

	@JsonProperty("ipv6-config")
	public String getIpv6Config()
	{
		return ipv6Config;
	}

	@JsonProperty("ipv6-config")
	public void setIpv6Config(String ipv6Config)
	{
		this.ipv6Config = ipv6Config;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("name")
	public String getName()
	{
		return name;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("name")
	public void setName(String name)
	{
		this.name = name;
	}

	@JsonProperty("technology")
	public Port.Technology getTechnology()
	{
		return technology;
	}

	@JsonProperty("technology")
	public void setTechnology(Port.Technology technology)
	{
		this.technology = technology;
	}

	public enum Technology
	{

		VHOST("vhost"), USVHOST("usvhost"), IVSHMEM("ivshmem");
		private final String value;
		private final static Map<String, Port.Technology> CONSTANTS = new HashMap<String, Port.Technology>();

		static
		{
			for (Port.Technology c : values())
			{
				CONSTANTS.put(c.value, c);
			}
		}

		Technology(String value)
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
		public static Port.Technology fromValue(String value)
		{
			Port.Technology constant = CONSTANTS.get(value);
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
