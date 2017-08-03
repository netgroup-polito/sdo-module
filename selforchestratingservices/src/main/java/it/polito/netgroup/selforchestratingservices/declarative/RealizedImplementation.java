package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface RealizedImplementation
{

	public Float qos();

	public void setResourcesUsed(List<InfrastructureResource> resources_used);

	public List<InfrastructureResource> getResourcesUsed();

}
