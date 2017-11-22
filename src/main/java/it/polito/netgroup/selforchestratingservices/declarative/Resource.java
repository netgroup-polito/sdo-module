package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

public interface Resource
{
	String getId();
	Boolean isUsed();

	//void setUsed(InfrastructureVNF ivnf);
	//InfrastructureVNF getUsedBy();
	void setUsed(String ivnf);
	String getUsedBy();

	void unsetUsed();

	ResourceType getType();

}
