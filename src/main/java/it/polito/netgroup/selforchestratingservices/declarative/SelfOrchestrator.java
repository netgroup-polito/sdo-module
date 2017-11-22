package it.polito.netgroup.selforchestratingservices.declarative;

import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureEventHandler;

import java.util.List;

public interface SelfOrchestrator
{
	List<ElementaryService> getElementaryServices();
	InfrastructureEventHandler getInfrastructureEventhandler();
	String getName();
	Variables getVariables();

	String getInitNFFGFilename();
}
