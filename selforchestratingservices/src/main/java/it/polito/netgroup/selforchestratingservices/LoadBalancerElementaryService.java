package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

public class LoadBalancerElementaryService implements ElementaryService
{
	List<Implementation> implementations;
	
	public LoadBalancerElementaryService(Variables var)
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
		//TODO
	}

	@Override
	public void setRelizedImplementation(RealizedImplementation realizedImplementation)
	{
		// TODO Auto-generated method stub
		
	}

	public void removeHost(Host host)
	{
		// TODO Auto-generated method stub
		
	}

	public void addHost(Host host)
	{
		// TODO Auto-generated method stub
		
	}

	public void removeNat(VnfForConfiguration args)
	{
		// TODO Auto-generated method stub
		
	}

	public void addNat(VnfForConfiguration args)
	{
		// TODO Auto-generated method stub
		
	}
}
