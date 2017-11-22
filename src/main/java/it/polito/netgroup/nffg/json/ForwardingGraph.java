
package it.polito.netgroup.nffg.json;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "id", "name", "description", "unify-monitoring", "domain", "VNFs", "end-points", "big-switch" })
public class ForwardingGraph
{

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("unify-monitoring")
	private String unifyMonitoring;
	@JsonProperty("domain")
	private String domain;
	@JsonProperty("VNFs")
	private List<VNF> vNFs = null;
	@JsonProperty("end-points")
	private List<EndPoint> endPoints = null;
	@JsonProperty("big-switch")
	private BigSwitch bigSwitch;

	@JsonProperty("id")
	public String getId()
	{
		return id;
	}

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

	@JsonProperty("unify-monitoring")
	public String getUnifyMonitoring()
	{
		return unifyMonitoring;
	}

	@JsonProperty("unify-monitoring")
	public void setUnifyMonitoring(String unifyMonitoring)
	{
		this.unifyMonitoring = unifyMonitoring;
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

	@JsonProperty("VNFs")
	public List<VNF> getVNFs()
	{
		return vNFs;
	}

	@JsonProperty("VNFs")
	public void setVNFs(List<VNF> vNFs)
	{
		this.vNFs = vNFs;
	}

	@JsonProperty("end-points")
	public List<EndPoint> getEndPoints()
	{
		return endPoints;
	}

	@JsonProperty("end-points")
	public void setEndPoints(List<EndPoint> endPoints)
	{
		this.endPoints = endPoints;
	}

	@JsonProperty("big-switch")
	public BigSwitch getBigSwitch()
	{
		return bigSwitch;
	}

	@JsonProperty("big-switch")
	public void setBigSwitch(BigSwitch bigSwitch)
	{
		this.bigSwitch = bigSwitch;
	}

}
