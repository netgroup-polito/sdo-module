package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;

public interface ResourceTemplate
{
	Class<? extends Resource> getType();
	void init(Variables var, Resource resource, Framework framework) throws Exception;

	List<DeclarativeFlowRule> getDefaultFlowRules();

	ConfigurationSDN getDefaultConfiguration();
}
