package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureResource;
import org.reflections.Reflections;

//AUTO
public abstract class AbstractImplementation implements Implementation
{
	protected Variables var;
	protected List<ResourceRequirement> resources;
	protected List<Class<? extends ResourceTemplate>> resourceTemplates;
	protected String name;
	
	public AbstractImplementation(Variables var,List<Class<? extends ResourceTemplate>> _resourceTemplates)
	{
		this.var = var;
		
		resources = new ArrayList<>();
		resourceTemplates = _resourceTemplates;
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
	public ResourceTemplate getTemplate(Class<? extends Resource> aClass)
	{
		for(Class<? extends ResourceTemplate> resourceTemplateClass : resourceTemplates)
		{
			try
			{
				ResourceTemplate m = resourceTemplateClass.newInstance();

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
