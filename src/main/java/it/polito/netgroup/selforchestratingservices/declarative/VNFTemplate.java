package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

public interface VNFTemplate
{
	Class<? extends InfrastructureVNF> getType();
	void init(Variables var, InfrastructureVNF infrastructureVNF, Framework framework) throws Exception;

	List<DeclarativeFlowRule> getDefaultFlowRules();
}
