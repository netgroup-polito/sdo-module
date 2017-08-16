package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;

//AUTO

public class NatElementaryService implements ElementaryService
{	
	List<Implementation> implementations;
	public NatElementaryService(Variables var)
	{
		implementations = new ArrayList<>();
		implementations.add(new NatImplementation1(var));
	}
	
	@Override
	public void show()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getName()
	{
		return "nat";
	}
	
	@Override
	public List<Implementation> getImplementations()
	{
		return implementations;
	}
	
	@Override
	public void commit()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRelizedImplementation(RealizedImplementation realizedImplementation)
	{
		// TODO Auto-generated method stub
		
	}
}
