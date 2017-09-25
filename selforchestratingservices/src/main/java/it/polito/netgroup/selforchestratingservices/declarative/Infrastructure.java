package it.polito.netgroup.selforchestratingservices.declarative;


import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorAuthenticationException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorHTTPException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorNF_FGNotFoundException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorNotAuthenticatedException;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureEventHandler;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative_new.MyFramework;

import java.util.List;

public interface Infrastructure {

	void commit() throws Exception;

	void updateResourceAvailable();

	List<Resource> getResources();

	void useResource(Resource usedResource, ElementaryService elementaryService);

	void freeAllResources();

	ConfigurationSDN getConfiguration(InfrastructureResource resource);

	void addFlowRule(FlowRule flowRule) throws Exception;

	void removeFlowRuleStartingWith(String prefix) throws Exception;

	//void setVariables(Variables variables);

	//void setInfrastructureEventHandler(InfrastructureEventHandler infrastructureEventhandler);

	void setFramework(Framework framework);
}