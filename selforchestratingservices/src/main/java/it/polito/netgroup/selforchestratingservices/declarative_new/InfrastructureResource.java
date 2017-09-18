package it.polito.netgroup.selforchestratingservices.declarative_new;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.*;

public interface InfrastructureResource extends Resource
{
	void setUsed(ElementaryService elementaryService);
	void unsetUsed();
	Boolean isInstantiated();
	void setInstantiated();
	void setConfiguration(ConfigurationSDN configuration);
	ConfigurationSDN getConfiguration();
	boolean equals(Object obj);
	List<DeclarativeFlowRule> getFlowRules();

	Boolean isVNF();

	void applyTemplate(ResourceTemplate template);

	ResourceTemplate getTemplate();
	String getDatastoreTemplate();

	String getFunctionalCapability();

	Infrastructure getInfrastructure();

	void unsetInstantiated();
}