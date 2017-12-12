package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.Pool;
import it.polito.netgroup.selforchestratingservices.declarative.*;

public abstract class AbstractElementaryService implements ElementaryService
{	
	protected List<Implementation> implementations;
	protected String name;
	protected Implementation currentImplementation;
	
	public AbstractElementaryService(Variables var)
	{
		implementations = new ArrayList<>();
		currentImplementation = null;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public List<Implementation> getImplementations()
	{
		return implementations;
	}
	
	@Override
	public void setCurrentImplementation(Implementation implementation)
	{
		currentImplementation = implementation;
	}

	@Override
	public Implementation getCurrentImplementation()
	{
		return currentImplementation;
	}
}
