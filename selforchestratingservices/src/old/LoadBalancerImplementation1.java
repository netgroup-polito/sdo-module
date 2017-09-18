package it.polito.netgroup.selforchestratingservices.old;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementationImpl;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

public class LoadBalancerImplementation1 implements Implementation
{
	Variables var;
	List<ResourceRequirement> resources;
	
	public LoadBalancerImplementation1(Variables var)
	{
		this.var = var;
		resources = new ArrayList<>();
		resources.add(new LoadBalancerResourceRequirement(var));
	}
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setResoucesUsed(List<InfrastructureResource> r)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<InfrastructureResource> getResourcesUsed()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public List<Resource> getResources()
	{
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
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
	
	@Override
	public Float getQoS(List<InfrastructureResource> r)
	{
		return (float) 1;
	}
	
	@Override
	public Float getQoS()
	{
		return (float) 1;
	}
	
	@Override
	public String getName()
	{
		return "LB";
	}
}
