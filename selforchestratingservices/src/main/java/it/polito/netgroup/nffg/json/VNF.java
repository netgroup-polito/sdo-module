
package it.polito.netgroup.nffg.json;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "id", "name", "functional-capability", "vnf_template", "domain", "gui-position", "ports", "unify-control",
		"unify-env-variables", "groups" })
public class VNF
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
	@JsonProperty("functional-capability")
	private String functionalCapability;
	@JsonProperty("vnf_template")
	private String vnfTemplate;
	@JsonProperty("domain")
	private String domain;
	@JsonProperty("gui-position")
	private Object guiPosition;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	private List<Port> ports = null;
	@JsonProperty("unify-control")
	private List<UnifyControl> unifyControl = null;
	@JsonProperty("unify-env-variables")
	private List<UnifyEnvVariable> unifyEnvVariables = null;
	@JsonProperty("groups")
	private List<Object> groups = null;

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

	@JsonProperty("functional-capability")
	public String getFunctionalCapability()
	{
		return functionalCapability;
	}

	@JsonProperty("functional-capability")
	public void setFunctionalCapability(String functionalCapability)
	{
		this.functionalCapability = functionalCapability;
	}

	@JsonProperty("vnf_template")
	public String getVnfTemplate()
	{
		return vnfTemplate;
	}

	@JsonProperty("vnf_template")
	public void setVnfTemplate(String vnfTemplate)
	{
		this.vnfTemplate = vnfTemplate;
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

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	public List<Port> getPorts()
	{
		return ports;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	public void setPorts(List<Port> ports)
	{
		this.ports = ports;
	}

	@JsonProperty("unify-control")
	public List<UnifyControl> getUnifyControl()
	{
		return unifyControl;
	}

	@JsonProperty("unify-control")
	public void setUnifyControl(List<UnifyControl> unifyControl)
	{
		this.unifyControl = unifyControl;
	}

	@JsonProperty("unify-env-variables")
	public List<UnifyEnvVariable> getUnifyEnvVariables()
	{
		return unifyEnvVariables;
	}

	@JsonProperty("unify-env-variables")
	public void setUnifyEnvVariables(List<UnifyEnvVariable> unifyEnvVariables)
	{
		this.unifyEnvVariables = unifyEnvVariables;
	}

	@JsonProperty("groups")
	public List<Object> getGroups()
	{
		return groups;
	}

	@JsonProperty("groups")
	public void setGroups(List<Object> groups)
	{
		this.groups = groups;
	}

}
