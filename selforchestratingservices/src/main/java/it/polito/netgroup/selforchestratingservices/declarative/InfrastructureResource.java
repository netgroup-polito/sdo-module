package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;

public interface InfrastructureResource extends Resource
{
	public boolean isUsed();
	public void setUsed();
	public void unsetUsed();
	public boolean isInstantiated();
	public void setInstantiated();
	public void unsetInstantiated();
	public void setConfiguration(ConfigurationSDN configuration);
	public ConfigurationSDN getConfiguration();
	public boolean equals(Object obj);
	public InfrastructureResource copy();
	//public List<DeclarativeFlowRule> getDefaultFlowRules();
	public void setDefaultFlowRules(List<DeclarativeFlowRule> defaultFlowRules);
	public List<DeclarativeFlowRule> getFlowRules();
	public void addFlowRules(List<DeclarativeFlowRule> flowRules);
	public void addFlowRule(DeclarativeFlowRule flowRules);

	public boolean isVNF();
	public boolean isConfigured();
	void setConfigured();
}