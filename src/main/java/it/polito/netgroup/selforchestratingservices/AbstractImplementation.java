package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

//AUTO
public abstract class AbstractImplementation implements Implementation
{
	protected Variables var;
	protected List<ResourceRequirement> resources;
	protected List<Class<? extends VNFTemplate>> resourceTemplates;
	protected String name;
	protected ConfigurationSDN lastConfiguration;
	
	public AbstractImplementation(Variables var,List<Class<? extends VNFTemplate>> _resourceTemplates, ConfigurationSDN startupConfiguration)
	{
		this.var = var;
		
		resources = new ArrayList<>();
		resourceTemplates = _resourceTemplates;
		lastConfiguration = startupConfiguration;
	}

	@Override
	public ConfigurationSDN getActualConfiguration()
	{
		return lastConfiguration;
	}

	@Override
	public void updateActualConfiguration(ConfigurationSDN configuration)
	{
		lastConfiguration = configuration;
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
	public VNFTemplate getTemplate(Class<? extends InfrastructureVNF> aClass)
	{
		for(Class<? extends VNFTemplate> resourceTemplateClass : resourceTemplates)
		{
			try
			{
				VNFTemplate m = resourceTemplateClass.newInstance();

				if ( m.getType().equals(aClass) ) return m;
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		return null;
	}
}
