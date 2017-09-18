package it.polito.netgroup.selforchestratingservices.old.loadbalancer;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.nffg.json.Action;
import it.polito.netgroup.nffg.json.DuplicateFlowRuleException;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.nffg.json.FlowRuleNotFoundException;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.nffg.json.Match;
import it.polito.netgroup.nffg.json.NF_FGExtended;
import it.polito.netgroup.nffg.json.PortUniqueID;

public class LoadBalancer
{

	private MacAddress mac;
	private PortUniqueID port_in;
	private int counter;

	public LoadBalancer(MacAddress _virtual_mac, PortUniqueID _port_in)
	{
		mac = _virtual_mac;
		port_in = _port_in;
		counter = 0;
	}

	public void set_default(NF_FGExtended nffg, PortUniqueID out_port) throws DuplicateFlowRuleException
	{
		List<FlowRule> flowrules = nffg.getFlowRulesStartingWith("LB_DEFAULT_");
		for (FlowRule flowrule : flowrules)
		{
			//logging.debug("removing flow rule with name '%s' " %
			// (flowrule.id))
			nffg.removeFlowRule(flowrule.getId());
		}
		// logging.debug("setting default flow rule : port_in=%s -> %s "
		// %(self.port_in,out_port))

		Action actionDefault = new Action();
		actionDefault.setOutputToPort(out_port.getValue());
		List<Action> actionsDefault = new ArrayList<Action>();
		actionsDefault.add(actionDefault);
		Match matchDefault = new Match();
		matchDefault.setPortIn(port_in.getValue());
		FlowRule frDefault = new FlowRule();
		frDefault.setId("LB_DEFAULT_" + counter);
		frDefault.setMatch(matchDefault);
		frDefault.setPriority(1);
		frDefault.setActions(actionsDefault);
		
		nffg.addFlowRule(frDefault);

		// logging.debug("setting reply flow rule : port_in=%s -> %s "
		// %(out_port,self.port_in))

		Action action = new Action();
		action.setOutputToPort(port_in.getValue());
		List<Action> actions = new ArrayList<Action>();
		actions.add(action);
		Match match = new Match();
		match.setPortIn(out_port.getValue());
		FlowRule fr = new FlowRule();
		fr.setId("LB_REPLY_" + counter);
		fr.setMatch(match);
		fr.setPriority(1);
		fr.setActions(actions);
		
		nffg.addFlowRule(fr);

		counter++;
	}

	public void remove_host(NF_FGExtended nffg, MacAddress host_mac)
	{
	    String flow_rule_name="LB_"+host_mac.getValue().replace(":","");
	    //logging.debug("removing flow rule %s " %(flow_rule_name))

	    nffg.removeFlowRule(flow_rule_name);
	}

	public PortUniqueID get_default_port(NF_FGExtended nffg) throws LoadBalancerDefaultRouteNotFoundException
	{
		List<FlowRule> flowrules = nffg.getFlowRulesStartingWith("LB_DEFAULT");

		if (flowrules.size() > 0)
		{
			FlowRule flow_rule = flowrules.get(0);
			Action action = flow_rule.getActions().get(0);
			PortUniqueID ret = new PortUniqueID(action.getOutputToPort());
			//logging.info("default output port  %s " % (ret));

			return ret;
		}

		throw new LoadBalancerDefaultRouteNotFoundException();
	}

	public void add_balance(NF_FGExtended nffg, MacAddress host_mac, PortUniqueID out_port) throws LoadBalancerBalanceAlreadyExistForHostException, DuplicateFlowRuleException
	{
	    String flow_rule_name = "LB_" + host_mac.getValue().replace(":", "");

	    //logging.info("setting flow rule : port_in=%s,source_mac=%s,dest_mac=%s -> %s " %(self.port_in,host_mac,self.mac,out_port))

	    if (exist_balance(nffg,host_mac) )
	    {
	    		throw new LoadBalancerBalanceAlreadyExistForHostException();
	    }
	    
		Action action = new Action();
		action.setOutputToPort(out_port.getValue());
		List<Action> actions = new ArrayList<Action>();
		actions.add(action);
		Match match = new Match();
		match.setPortIn(port_in.getValue());
		match.setSourceMac(host_mac.getValue());
		match.setDestMac(mac.getValue());
		FlowRule fr = new FlowRule();
		fr.setId(flow_rule_name+counter);
		fr.setMatch(match);
		fr.setPriority(2);
		fr.setActions(actions);
		
		nffg.addFlowRule(fr);
		
		counter++;

	}

	public PortUniqueID get_port_host(NF_FGExtended nffg, MacAddress macAddress) throws LoadBalancerHostRouteNotFoundException
	{
		String flow_rule_name = "LB_" + macAddress.getValue().replace(":", "");
		FlowRule flow_rule;
		try
		{
			flow_rule = nffg.getFlowRule(flow_rule_name);
		} catch (FlowRuleNotFoundException e)
		{
			throw new LoadBalancerHostRouteNotFoundException();
		}

		Action action = flow_rule.getActions().get(0);
		PortUniqueID ret = new PortUniqueID(action.getOutputToPort());
		// logging.info("Host output port %s " % (ret));

		return ret;
	}

	public boolean exist_balance(NF_FGExtended nffg, MacAddress host_mac)
	{
	    String flow_rule_name = "LB_" + host_mac.getValue().replace(":", "");

		try
		{
			nffg.getFlowRule(flow_rule_name);
		} catch (FlowRuleNotFoundException e)
		{
			return false;
		}

		return true;
	}

	public void dump(NF_FGExtended nffg)
	{
		List<FlowRule> flowrules = nffg.getFlowRulesStartingWith("LB_");

		for (FlowRule flowrule : flowrules)
		{
			System.out.println(flowrule);
		}
	}
	
	public MacAddress get_mac_host_to_vnf(NF_FGExtended nffg, String vnf_id) throws LoadBalancerHostNotFoundException
	{
		List<FlowRule> flowrules = nffg.getFlowRulesStartingWith("LB_");

		for (FlowRule flowrule : flowrules)
		{
			String port = flowrule.getActions().get(0).getOutputToPort();
			
			if ( port.split(":")[1].equals(vnf_id))
			{
				return new MacAddress(flowrule.getMatch().getSourceMac());
			}
		}
		
		throw new LoadBalancerHostNotFoundException();
	}
}


