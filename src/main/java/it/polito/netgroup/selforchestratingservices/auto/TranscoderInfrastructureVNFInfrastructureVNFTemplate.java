package it.polito.netgroup.selforchestratingservices.auto;

import java.security.*;
import java.util.*;
import java.util.stream.*;
import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.declarative_new.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.*;

//Autogenerated file
public class TranscoderInfrastructureVNFInfrastructureVNFTemplate implements VNFTemplate
{
	public TranscoderInfrastructureVNFInfrastructureVNFTemplate()
	{
	}

	@Override
	public List<DeclarativeFlowRule> getDefaultFlowRules() {
		List<DeclarativeFlowRule> default_flowrules = new ArrayList<DeclarativeFlowRule>();
		
		DeclarativeFlowRule dfr0 = new DeclarativeFlowRuleImpl();
		dfr0.linkPorts("Port_SWLAN:port","Port","SWLAN:port");
		default_flowrules.add(dfr0);
		DeclarativeFlowRule dfr1 = new DeclarativeFlowRuleImpl();
		dfr1.linkPorts("management_SWMAN:port","management","SWMAN:port");
		default_flowrules.add(dfr1);
		return default_flowrules;
	}

	@Override
	public Class<? extends InfrastructureVNF> getType()
	{
		return TranscoderInfrastructureVNF.class;
	}

	@Override
	public void init(Variables var, InfrastructureVNF infrastructureVNF, Framework framework) throws Exception
	{
		
	}
}