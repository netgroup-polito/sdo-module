package it.polito.netgroup.selforchestratingservices.declarative_new;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.*;

public interface InfrastructureVNF
{
	String getId();

	Implementation getUsedBy();
	void setUsed(Implementation implementation);
	void unsetUsed();

	Boolean isInstantiated();
	void setInstantiated();
	void setConfiguration(ConfigurationSDN configuration);
	ConfigurationSDN getConfiguration();
	boolean equals(Object obj);
	List<DeclarativeFlowRule> getFlowRules();

	void applyTemplate(VNFTemplate template);

	VNFTemplate getTemplate();
	String getDatastoreTemplate();

	String getFunctionalCapability();

	Infrastructure getInfrastructure();

	void unsetInstantiated();

	Boolean isUsed();

	void setModified();

	Boolean updateConfigurationNeeded();
	void resetUpdateConfiguration();
}