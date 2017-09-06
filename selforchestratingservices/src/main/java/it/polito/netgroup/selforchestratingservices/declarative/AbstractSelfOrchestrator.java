package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractSelfOrchestrator implements SelfOrchestratorDichiarativo
{
	private String name;
	private HashMap<String,ElementaryService> elementaryServices;
	private Infrastructure infrastructure;

	protected void init(String name, HashMap<String, ElementaryService> elementaryServices2,
			Infrastructure infrastructure)
	{
		this.name = name;
		this.elementaryServices = elementaryServices2;
		this.infrastructure = infrastructure;
	}
	
	@Override
	public Infrastructure getInfrastructure()
	{
		return infrastructure;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public List<ElementaryService> getElementaryServices()
	{
		return new ArrayList<>(elementaryServices.values());
	}
	
	@Override
	public void newServiceState()
	{
		infrastructure.freeAllResources();
		for (ElementaryService elementaryService : elementaryServices.values())
		{
			RealizedImplementation realizedImplementation = null;
			for (Implementation implementation : elementaryService.getImplementations())
			{
				for (ResourceRequirement resourceRequirement : implementation.getResourceRequirement())
				{
					List<InfrastructureResource> resources_used = new ArrayList<>();
					for (InfrastructureResource infrastructureResource : infrastructure.getResourcesFreeOfType(resourceRequirement.getType()))
					{
						//infrastructureResource.setUsed();
						infrastructureResource.setConfiguration(resourceRequirement.getDefaultConfiguration());
						infrastructureResource.setDefaultFlowRules(resourceRequirement.getDefaultFlowRules());
						resources_used.add(infrastructureResource);
						
						if (realizedImplementation == null || realizedImplementation.qos() < implementation.getQoS(resources_used))
						{
							if (realizedImplementation != null )
							{
								infrastructure.freeResources(resources_used);
							}
							realizedImplementation = implementation.getRealizedImplementation(resources_used);
							infrastructure.setResourcesUsed(resources_used);
						}
					}			
				}
			}
			if (realizedImplementation == null)
			{
				System.out.println("ERRORE");
				System.exit(1);
			}
			elementaryService.setRelizedImplementation(realizedImplementation);
		}
		commit();
	}
	

	@Override
	public void commit()
	{
		infrastructure.commit();
	}
}
