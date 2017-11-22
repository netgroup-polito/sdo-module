package it.polito.netgroup.selforchestratingservices.declarative_new;


import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

import java.util.List;

public interface Infrastructure {

	void commit() throws Exception;

	void updateResourceAvailable();

	List<Resource> getResources();

	void useResource(Resource usedResource, InfrastructureVNF ivnf);

	void freeAllResources();

	ConfigurationSDN getConfiguration(InfrastructureVNF resource);

	void addFlowRule(FlowRule flowRule) throws Exception;

	void removeFlowRuleStartingWith(String prefix) throws Exception;

	//void setVariables(Variables variables);

	//void setInfrastructureEventHandler(InfrastructureEventHandler infrastructureEventhandler);

	void setFramework(Framework framework);

	List<InfrastructureVNF> getInfrastructureVNF();

	void useInfrastructureVNF(Implementation implementation, ConfigurationSDN configuration , List<Resource> resourcesUsed, ElementaryService elementaryService);

	Boolean checkEvents();

	void updateImplementationStatus(Implementation implementation);
}