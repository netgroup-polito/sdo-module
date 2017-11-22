
package it.polito.netgroup.nffg_template.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "platformType", "socket" })
public class CPUrequirements
{

	@JsonProperty("platformType")
	private String platformType;
	@JsonProperty("socket")
	private List<Socket> socket = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("platformType")
	public String getPlatformType()
	{
		return platformType;
	}

	@JsonProperty("platformType")
	public void setPlatformType(String platformType)
	{
		this.platformType = platformType;
	}

	@JsonProperty("socket")
	public List<Socket> getSocket()
	{
		return socket;
	}

	@JsonProperty("socket")
	public void setSocket(List<Socket> socket)
	{
		this.socket = socket;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
	}

}
