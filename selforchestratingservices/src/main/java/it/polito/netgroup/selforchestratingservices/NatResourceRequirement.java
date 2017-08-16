package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.configurationorchestrator.InvalidInterfaceLabel;
import it.polito.netgroup.configurationorchestrator.NatConfiguration;
import it.polito.netgroup.nffg.json.InterfaceLabel;
import it.polito.netgroup.nffg.json.IpAddressAndNetmask;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRuleImpl;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarNameException;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarTypeException;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;


//AUTO_FILE

public class NatResourceRequirement implements ResourceRequirement
{
	Variables var;
	
	public NatResourceRequirement(Variables var)
	{
		this.var = var;
	}
	@Override
	public String getType()
	{
		return "NAT";
	}
	
	@Override
	public List<DeclarativeFlowRule> getDefaultFlowRules()
	{
        List<DeclarativeFlowRule> default_flowrules = new ArrayList<DeclarativeFlowRule>();
        
        DeclarativeFlowRule dfr1 = new DeclarativeFlowRuleImpl();
        dfr1.linkPorts("linkWAN","WAN","SWITCH_WAN:port");
        DeclarativeFlowRule dfr2 = new DeclarativeFlowRuleImpl();
        dfr2.linkPorts("linkMAN","management","SWITCH_MAN:port");		        
        default_flowrules.add(dfr1);
        default_flowrules.add(dfr2);
        
        return default_flowrules;
	}
	
	@Override
	public ConfigurationSDN getDefaultConfiguration()
	{
		NatConfiguration default_cfg = new NatConfiguration(null, null, null,null);
        try
		{
			default_cfg.setIP(new InterfaceLabel("User"), new IpAddressAndNetmask("192.168.10.1", "255.255.255.0") , new MacAddress("02:01:02:03:04:05"));
		} catch (InvalidInterfaceLabel e)
		{
			e.printStackTrace();
			return null;
		}
        return default_cfg;
	}
	
	@Override
	public boolean checkConstraint(List<Resource> r)
	{
		r = r.stream().filter(x -> x.getType() == this.getType()).collect(Collectors.toCollection(ArrayList::new));

		try
		{
			return ( r.size() > 1 && r.size() < var.getVar("hosts",List.class).size()+1 );
		} catch (InvalidVarNameException e)
		{
			e.printStackTrace();
			return false;
		} catch (InvalidVarTypeException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isVNF()
	{
		return true;
	}
}
