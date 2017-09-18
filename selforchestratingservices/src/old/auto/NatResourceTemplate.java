package it.polito.netgroup.selforchestratingservices.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.configurationorchestrator.json.nat.NatSession;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;

//AUTO_FILE
public class NatResourceTemplate implements ResourceTemplate
{	
	public NatResourceTemplate()
	{
		
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
	public void init(Variables var,NatInfrastructureResource nat, Infrastructure infra)
	{
		infra.addDirtyChecker(new ListDirtyChecker<NatInfrastructureResource,NatSession>(nat,nat.getNatSession(),"1000ms", new DirtyCheckerEventHandler<NatInfrastructureResource, NatSession>()
		{

			@Override
			public void on_new(NatInfrastructureResource object, NatSession value)
			{
//				(new On_new_nat_sessionEventHandler(var)).fire(object,value);
			}

			@Override
			public void on_del(NatInfrastructureResource object, NatSession value)
			{
//				(new On_del_nat_sessionEventHandler(var)).fire(object,value);
			}

			@Override
			public void on_change(NatInfrastructureResource object, NatSession old, NatSession now)
			{				
			}
		}));
	}
}
