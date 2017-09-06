package it.polito.netgroup.selforchestratingservices.old;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

public class LoadBalancerResourceRequirement implements ResourceRequirement
{
	Variables var;
	
	public LoadBalancerResourceRequirement(Variables var)
	{
		this.var = var;
	}
		
	@Override
	public String getType()
	{
		return "LOADBALANCER";
	}
	
	@Override
	public List<DeclarativeFlowRule> getDefaultFlowRules()
	{
		return new ArrayList<>();
	}
	
	@Override
	public ConfigurationSDN getDefaultConfiguration()
	{
		return null;
	}
	
	@Override
	public boolean checkConstraint(List<Resource> r)
	{
		r = r.stream().filter(x -> x.getType() == this.getType()).collect(Collectors.toCollection(ArrayList::new));
	
		return r.size() == 1;
	}
	
	@Override
	public boolean isVNF()
	{
		return false;
	}
}
