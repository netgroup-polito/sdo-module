package it.polito.netgroup.selforchestratingservices.auto.prova;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;

//Autogenerated file
public class NatInfrastructureResourceResourceTemplate implements ResourceTemplate
{
	public NatInfrastructureResourceResourceTemplate()
	{
	}

	@Override
	public String getType()
	{
		return "NatInfrastructureVNF";
	}

	@Override
	public List<DeclarativeFlowRule> getDefaultFlowRules()
	{
		List<DeclarativeFlowRule> default_flowrules = new ArrayList<DeclarativeFlowRule>();
		
		DeclarativeFlowRule dfr0 = new DeclarativeFlowRuleImpl();
		dfr0.linkPorts("WAN_Switch_WAN:Port","WAN","Switch_WAN:Port");
		default_flowrules.add(dfr0);
		DeclarativeFlowRule dfr1 = new DeclarativeFlowRuleImpl();
		dfr1.linkPorts("MAN_Switch_MAN:Port","MAN","Switch_MAN:Port");
		default_flowrules.add(dfr1);
		return default_flowrules;
	}

	@Override
	public ConfigurationSDN getDefaultConfiguration()
	{
		NatConfiguration default_cfg = new NatConfiguration(null, null, null,null);
		try
			{
				default_cfg.setIP(new InterfaceLabel("User"), new IpAddressAndNetmask("192.168.10.1", "255.255.255.0") , new MacAddress("02:01:02:03:04:05"));
		}catch(InvalidInterfaceLabel e)
		{
			e.printStackTrace();
			return null;
		}
		return default_cfg;
	}
	@Override
	public void init(Variables var, NatInfrastructureResource resource, Infrastructure infrastructure)
	{
		infrastructure.addDirtyChecker(new ListDirtyChecker<NatInfrastructureResource,NatSession>(resource,resource.getNatSession(),"100ms", new DirtyCheckerEventHandler<NatInfrastructureResource,NatSession>()
		{
		
			@Override
				public void on_new(NatInfrastructureResource object, NatSession value)
			{
					 (new On_new_nat_sessionEventHandler(var)).fire(object,value);
			}
				public void on_del(NatInfrastructureResource object, NatSession value)
			{
					 (new On_del_nat_sessionEventHandler(var)).fire(object,value);
			}
			@Override
			public void on_change(NatInfrastructureResource object, NatSession old, NatSession now)
			{
			}
		}));
		
	}}