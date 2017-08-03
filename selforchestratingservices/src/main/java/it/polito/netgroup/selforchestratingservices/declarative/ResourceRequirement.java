package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;

public interface ResourceRequirement
{
	public String getType();
	public boolean isVNF();
	public ConfigurationSDN getDefaultConfiguration(); 
	public List<DeclarativeFlowRule> getDefaultFlowRules();
	public boolean checkConstraint(List<Resource> r);

}