package it.polito.netgroup.selforchestratingservices.auto;

import java.util.List;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;

//AUTO
public class NatImplementation1 extends AbstractImplementation
{
	
	public NatImplementation1(Variables var)
	{
		super(var);
		name = "NatImplementation1";
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
}
