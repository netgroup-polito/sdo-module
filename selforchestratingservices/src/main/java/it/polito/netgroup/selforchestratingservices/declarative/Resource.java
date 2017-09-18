package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;

public interface Resource
{
	String getId();
	Boolean isUsed();
	ElementaryService getUsedBy();
}
