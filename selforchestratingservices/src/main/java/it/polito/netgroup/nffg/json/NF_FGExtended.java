package it.polito.netgroup.nffg.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.nffg_template.json.NotImplementedYetException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NF_FGExtended extends NF_FG
{
	public NF_FGExtended()
	{
		
	}

	public NF_FGExtended(String nffg_id, String nffg_name)
	{
		setForwardingGraph(new ForwardingGraph());

		getForwardingGraph().setId(nffg_id);
		getForwardingGraph().setName(nffg_name);

		getForwardingGraph().setVNFs(new ArrayList<>());
		getForwardingGraph().setEndPoints(new ArrayList<>());
		getForwardingGraph().setBigSwitch(new BigSwitch());
		getForwardingGraph().getBigSwitch().setFlowRules(new ArrayList<>());
	}
	
	@JsonIgnore
	public String getName()
	{
		return getForwardingGraph().getName();
	}

	@JsonIgnore
	public void addVNF(VNF vnf) throws DuplicateVNFException
	{
		try
		{
			getVNF(vnf.getId());
			throw new DuplicateVNFException();
		} catch (VNFNotFoundException e)
		{
		}

		getForwardingGraph().getVNFs().add(vnf);
	}

	@JsonIgnore
	public void addEndPoint(EndPoint endpoint) throws DuplicateEndPointException
	{
		try
		{
			getEndPoint(endpoint.getId());
			throw new DuplicateEndPointException();
		} catch (EndPointNotFoundException e)
		{
		}
		getForwardingGraph().getEndPoints().add(endpoint);
	}

	@JsonIgnore
	private EndPoint getEndPoint(String id) throws EndPointNotFoundException
	{
		for (EndPoint endPoint : getForwardingGraph().getEndPoints())
		{
			if (endPoint.getId().equals(id))
			{
				return endPoint;
			}
		}
		throw new EndPointNotFoundException("not found EndPoint with id : " + id);
	}

	@JsonIgnore
	public void link_ports(String id1, String id2, PortUniqueID port1, PortUniqueID port2)
			throws PortNotFoundException, DuplicateFlowRuleException
	{
		if (!existPort(port1))
		{
			throw new PortNotFoundException("Port with id:" + port1.getValue() + " is invalid");
		}
		if (!existPort(port2))
		{
			throw new PortNotFoundException("Port with id:" + port2.getValue() + " is invalid");
		}
		Action action = new Action();
		action.setOutputToPort(port2.getValue());
		List<Action> actions = new ArrayList<>();
		actions.add(action);
		Match match = new Match();
		match.setPortIn(port1.getValue());
		FlowRule fr = new FlowRule();
		fr.setId(id1);
		fr.setPriority(1);
		fr.setMatch(match);
		fr.setActions(actions);

		addFlowRule(fr);

		action = new Action();
		action.setOutputToPort(port1.getValue());
		actions = new ArrayList<>();
		actions.add(action);
		match = new Match();
		match.setPortIn(port2.getValue());
		fr = new FlowRule();
		fr.setId(id2);
		fr.setPriority(1);
		fr.setMatch(match);
		fr.setActions(actions);

		addFlowRule(fr);
	}
	
	@JsonIgnore
	public FlowRule getFlowRule(String id) throws FlowRuleNotFoundException
	{
		for (FlowRule flowRule : getForwardingGraph().getBigSwitch().getFlowRules())
		{
			if (flowRule.getId().equals(id))
			{
				return flowRule;
			}
		}
		throw new FlowRuleNotFoundException();
	}
	
	@JsonIgnore
	public void addFlowRule(FlowRule fr) throws DuplicateFlowRuleException
	{
		try
		{
			getFlowRule(fr.getId());
		} catch (FlowRuleNotFoundException e)
		{
			getForwardingGraph().getBigSwitch().getFlowRules().add(fr);
			return;
		}
		throw new DuplicateFlowRuleException("Duplicate flowrule with ID:"+fr.getId());
	}

	@JsonIgnore
	private FlowRule addFlowRule(String id)
	{
		for (FlowRule flowRule : getFlowRules())
		{
			if (flowRule.getId().equals(id))
				return flowRule;
		}
		return null;
	}
	
	@JsonIgnore
	public boolean existPort(PortUniqueID port1)
	{
		try
		{
			if (port1.getType().equals("vnf"))
			{
				VNFExtended vnf = getVNF(port1.getVnfId());
				return vnf.existPort(port1.getVnfPort());
			} else if (port1.getType().equals("endpoint"))
			{
				@SuppressWarnings("unused")
				EndPoint endpoint = getEndpoint(port1.getEndpointName());
				return true;
			} else
			{
				throw new NotImplementedYetException("Unknown type " + port1.getType());
			}
		} catch (EndPointNotFoundException e)
		{
			return false;
		} catch (VNFNotFoundException e)
		{
			return false;
		} catch (NotImplementedYetException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@JsonIgnore
	private EndPoint getEndpoint(String id) throws EndPointNotFoundException
	{
		for (EndPoint endpoint : getForwardingGraph().getEndPoints())
		{
			if (endpoint.getId().equals(id))
			{
				return endpoint;
			}
		}
		throw new EndPointNotFoundException("not found EndPoint with id : " + id);

	}

	@JsonIgnore
	public void removeVNF(VNF vnf)
	{
		getForwardingGraph().getVNFs().remove(vnf);
	}

	@JsonIgnore
	public VNFExtended getVNF(String id) throws VNFNotFoundException
	{
		for (VNF vnf : getForwardingGraph().getVNFs())
		{
			if (vnf.getId().equals(id))
			{
				return VNFExtended.fromVNF(vnf);
			}
		}
		throw new VNFNotFoundException("not found VNF with id : " + id);
	}
	
	@JsonIgnore
	public List<FlowRule> getFlowRules()
	{
		return getForwardingGraph().getBigSwitch().getFlowRules();
	}

	@JsonIgnore
	public List<FlowRule> getFlowRulesSendingTrafficToPort(PortUniqueID port)
	{
		List<FlowRule> ret = new ArrayList<FlowRule>();

		for (FlowRule flowRule : getFlowRules())
		{
			for (Action action : flowRule.getActions())
			{
				if (action.getOutputToPort().equals(port.getValue()))
				{
					ret.add(flowRule);
					break;
				}
			}
		}
		return ret;
	}

	@JsonIgnore
	public List<FlowRule> getFlowRulesSendingTrafficFromPort(PortUniqueID port)
	{
		List<FlowRule> ret = new ArrayList<FlowRule>();

		for (FlowRule flowRule : getFlowRules())
		{
			String portIn = flowRule.getMatch().getPortIn();
			if (portIn != null && portIn.equals(port.getValue()))
			{
				ret.add(flowRule);
			}
		}
		return ret;
	}

	@JsonIgnore
	public List<FlowRule> getFlowRulesStartingWith(String prefix)
	{
		List<FlowRule> ret = new ArrayList<FlowRule>();

		for (FlowRule flowRule : getFlowRules())
		{
			if (flowRule.getId().startsWith(prefix))
			{
				ret.add(flowRule);
			}
		}
		return ret;
	}

	@JsonIgnore
	public void removeFlowRule(String id)
	{
		for (FlowRule f : getFlowRules())
		{
			if (f.getId().equals(id))
			{
				getFlowRules().remove(f);
				return;
			}
		}
	}

	@JsonIgnore
	public String getId()
	{
		return getForwardingGraph().getId();
	}

	@JsonIgnore
	public static NF_FGExtended getFromJson(String json) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, NF_FGExtended.class);
	}
	
	@JsonIgnore
	public String getJson() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public void removeVNF(VnfForConfiguration nat)
	{
		// TODO Auto-generated method stub
		
	}

	public void removeVNF(String id)
	{
		// TODO Auto-generated method stub
		
	}
}
