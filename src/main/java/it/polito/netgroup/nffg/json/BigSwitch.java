
package it.polito.netgroup.nffg.json;

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
{ "flow-rules" })
public class BigSwitch
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("flow-rules")
	private List<FlowRule> flowRules = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("flow-rules")
	public List<FlowRule> getFlowRules()
	{
		return flowRules;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("flow-rules")
	public void setFlowRules(List<FlowRule> flowRules)
	{
		this.flowRules = flowRules;
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
