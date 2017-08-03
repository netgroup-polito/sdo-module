
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
{ "id", "description", "priority", "match", "actions" })
public class FlowRule
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("id")
	private String id;
	@JsonProperty("description")
	private String description;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("priority")
	private Integer priority;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("match")
	private Match match;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("actions")
	private List<Action> actions = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	@JsonProperty("description")
	public String getDescription()
	{
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("priority")
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("priority")
	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("match")
	public Match getMatch()
	{
		return match;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("match")
	public void setMatch(Match match)
	{
		this.match = match;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("actions")
	public List<Action> getActions()
	{
		return actions;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("actions")
	public void setActions(List<Action> actions)
	{
		this.actions = actions;
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
