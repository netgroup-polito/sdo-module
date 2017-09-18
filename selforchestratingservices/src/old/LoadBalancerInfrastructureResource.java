package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;

public class LoadBalancerInfrastructureResource extends AbstractBasicInfrastructureResource
{
	public LoadBalancerInfrastructureResource(String _id)
	{
		super(_id, "LOADBALANCER");
	}

	@Override
	public LoadBalancerInfrastructureResource copy()
	{
		return new LoadBalancerInfrastructureResource(id);
	}

	@Override
	public Boolean isVNF()
	{
		return false;
	}
	
	@Override
	public List<DeclarativeFlowRule> getFlowRules()
	{
		List<DeclarativeFlowRule> ret =  new ArrayList<>(default_flowrules);
		ret.addAll(custom_flowrules);
		
		return ret;
	}


	
}
