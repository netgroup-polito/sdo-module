package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.selforchestratingservices.auto.NatResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.*;

//AUTO
public abstract class AbstractImplementation implements Implementation
{
	protected Variables var;
	protected List<ResourceRequirement> resources;
	protected List<InfrastructureResource> infraResourcesUsed;
	protected String name;
	
	public AbstractImplementation(Variables var)
	{
		this.var = var;
		
		resources = new ArrayList<ResourceRequirement>();
		infraResourcesUsed = null;
		
	    resources.add(new NatResourceRequirement(var));
	}
	
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void setResoucesUsed(List<InfrastructureResource> r)
	{
		infraResourcesUsed = new ArrayList<>(r);
	}
	
	@Override
	public List<InfrastructureResource> getResourcesUsed()
	{
		return infraResourcesUsed;
	}
		
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public List<ResourceRequirement> getResourceRequirement()
	{
		return resources;
	}

	@Override
	public RealizedImplementation getRealizedImplementation(List<InfrastructureResource> resources_used)
	{
		return new RealizedImplementationImpl(this,resources_used);
	}
		
}
