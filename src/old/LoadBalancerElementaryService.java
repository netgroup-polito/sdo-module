package it.polito.netgroup.selforchestratingservices.old;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.nffg.json.PortUniqueID;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

public class LoadBalancerElementaryService implements ElementaryService
{
	List<Implementation> implementations;
	RealizedImplementation ri;
	
	public LoadBalancerElementaryService(MacAddress virtual_mac,PortUniqueID port_in,Variables var)
	{
		implementations = new ArrayList<>();
		implementations.add(new LoadBalancerImplementation1(var));
		
	}
	
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName()
	{
		return "loadbalancer";
	}
	
	@Override
	public List<Implementation> getImplementations()
	{
		return implementations;
	}
	
	@Override
	public void commit()
	{
	}

	@Override
	public void setRelizedImplementation(RealizedImplementation realizedImplementation)
	{
		// TODO Auto-generated method stub
		
	}

	public void removeHost(Host host)
	{
	}

	public void addHost(Host host)
	{
	}

	public void removeNat(VnfForConfiguration args)
	{
	}

	public void addNat(VnfForConfiguration args)
	{
	}
}
