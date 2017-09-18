package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.DirtyChecker;

import java.util.List;

public interface Framework {

	void setInfrastructure(Infrastructure infrastructure);
	Infrastructure getInfrastructure();

	void setSelfOrchestrator(SelfOrchestrator _My_selfOrchestrator);
	SelfOrchestrator getSelfOrchestrator();

	void setResourceManager(ResourceManager resourceManager);
	ResourceManager getResourceManager();

	void addDirtyChecker(DirtyChecker dirtyChecker);

	void mainLoop();
}
