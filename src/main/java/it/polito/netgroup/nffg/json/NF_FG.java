
package it.polito.netgroup.nffg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "forwarding-graph" })
public class NF_FG
{

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("forwarding-graph")
	private ForwardingGraph forwardingGraph;

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("forwarding-graph")
	public ForwardingGraph getForwardingGraph()
	{
		return forwardingGraph;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("forwarding-graph")
	public void setForwardingGraph(ForwardingGraph forwardingGraph)
	{
		this.forwardingGraph = forwardingGraph;
	}

}
