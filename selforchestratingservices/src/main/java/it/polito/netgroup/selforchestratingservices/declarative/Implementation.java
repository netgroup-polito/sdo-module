package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface Implementation
{
	public String getName();
	public void setResoucesUsed(List<InfrastructureResource> r);
	public List<Resource> getResources();
	public List<InfrastructureResource> getResourcesUsed();
	public Float getQoS();
	public Float getQoS(List<InfrastructureResource> r);
	//public boolean checkConstraint(List<Resource> r);
	public void show();
	public List<ResourceRequirement> getResourceRequirement();
	public RealizedImplementation getRealizedImplementation(List<InfrastructureResource> resources_used);

}
