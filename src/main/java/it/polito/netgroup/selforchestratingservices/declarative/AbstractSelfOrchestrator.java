package it.polito.netgroup.selforchestratingservices.declarative;

import it.polito.netgroup.selforchestratingservices.declarative_new.AbstractInfrastructureEventHandler;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureEventHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractSelfOrchestrator implements SelfOrchestrator
{
	protected String name;
	protected HashMap<String,ElementaryService> elementaryServices;
	protected Variables variables ;
	protected InfrastructureEventHandler infrastructureEventHandler;
	protected String initNFFGFilename;

	public AbstractSelfOrchestrator(Framework _framework, String _initNFFGFilename)
	{
		elementaryServices = new HashMap<>();
		variables = new VariablesImplementation();
		initNFFGFilename = _initNFFGFilename;
	}

	@Override
	public String getName()
	{
		return "test_nffg_nat"; //Workaround: fix before VM metadata creation function inside the UN
	}
	
	@Override
	public List<ElementaryService> getElementaryServices()
	{
		return new ArrayList<>(elementaryServices.values());
	}

	@Override
	public InfrastructureEventHandler getInfrastructureEventhandler() {
		return infrastructureEventHandler;
	}

	public Variables getVariables() {
		return variables;
	}

	@Override
	public String getInitNFFGFilename()
	{
		return initNFFGFilename;
	}
}
