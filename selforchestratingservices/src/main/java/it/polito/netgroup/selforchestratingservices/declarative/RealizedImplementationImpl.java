package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public class RealizedImplementationImpl implements RealizedImplementation
{
	Implementation impl;
	List<InfrastructureResource> resources;
	
	public RealizedImplementationImpl(Implementation impl , List<InfrastructureResource> resources_used)
	{
		this.impl = impl;
		this.resources = resources_used;
	}
	
	@Override
	public Float qos()
	{
		return impl.getQoS(resources);
	}

	@Override
	public void setResourcesUsed(List<InfrastructureResource> resources_used)
	{
		resources = resources_used;
	}

	@Override
	public List<InfrastructureResource> getResourcesUsed()
	{
		return resources;
	}

}
