package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface Infrastructure
{

	List<InfrastructureResource> getResourcesFreeOfType(String type);

	void freeAllResources();

	void freeResources(List<InfrastructureResource> resourcesUsed);

	void setResourcesUsed(List<InfrastructureResource> resourcesUsed);

	void eventLoop();

	void commit();
	
	void addHandler(String event_name, EventHandler handler);

	void raiseEvent(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource);

}
