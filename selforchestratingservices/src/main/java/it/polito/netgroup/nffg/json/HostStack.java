
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
{ "configuration", "IPv4" })
public class HostStack
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("configuration")
	private HostStack.Configuration configuration;
	@JsonProperty("IPv4")
	private String iPv4;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("configuration")
	public HostStack.Configuration getConfiguration()
	{
		return configuration;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("configuration")
	public void setConfiguration(HostStack.Configuration configuration)
	{
		this.configuration = configuration;
	}

	@JsonProperty("IPv4")
	public String getIPv4()
	{
		return iPv4;
	}

	@JsonProperty("IPv4")
	public void setIPv4(String iPv4)
	{
		this.iPv4 = iPv4;
	}

	public enum Configuration
	{

		DHCP("DHCP"), STATIC("static"), PPPOE("pppoe");
		private final String value;
		private final static Map<String, HostStack.Configuration> CONSTANTS = new HashMap<String, HostStack.Configuration>();

		static
		{
			for (HostStack.Configuration c : values())
			{
				CONSTANTS.put(c.value, c);
			}
		}

		private Configuration(String value)
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
		public static HostStack.Configuration fromValue(String value)
		{
			HostStack.Configuration constant = CONSTANTS.get(value);
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
