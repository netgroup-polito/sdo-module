package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarNameException;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarTypeException;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementationImpl;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

//AUTO
public class NatImplementation1 implements Implementation
{
	Variables var;
	List<ResourceRequirement> resources;
	
	public NatImplementation1(Variables var)
	{
		this.var = var;
		
		resources = new ArrayList<ResourceRequirement>();

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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<InfrastructureResource> getResourcesUsed()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Resource> getResources()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Float getQoS(List<InfrastructureResource> resources)
	{
		List<Host> hosts;
		try
		{
			hosts = var.getVar("hosts", List.class);
		} catch (InvalidVarNameException | InvalidVarTypeException e)
		{
			e.printStackTrace();
			return Float.NaN;
		}
		
		if ( hosts.size() == 0 ) return (float) 0.0;
		
		int i=0;
		for (Resource resource : resources)
		{
			if ( resource.isUsed() && resource.getType().equals("NAT"))
			{
				i++;
			}
		}
		return (float) (i / resources.size());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Float getQoS()
	{
		List<Host> hosts;
		try
		{
			hosts = var.getVar("hosts", List.class);
		} catch (InvalidVarNameException | InvalidVarTypeException e)
		{
			e.printStackTrace();
			return Float.NaN;
		}
		List<Resource> resources;
		try
		{
			resources = var.getVar("resources", List.class);
		} catch (InvalidVarNameException | InvalidVarTypeException e)
		{
			e.printStackTrace();
			return Float.NaN;
		}
		
		if ( hosts.size() == 0 ) return (float) 0;
		
		int i=0;
		for (Resource resource : resources)
		{
			if ( resource.isUsed() && resource.getType().equals("NAT"))
			{
				i++;
			}
			}
			return (float) (i / resources.size());
		}
		
		@Override
		public String getName()
		{
			return "ImplementationNat1";
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
