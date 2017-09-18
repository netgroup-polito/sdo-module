package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MyResourceManager implements ResourceManager{

	Framework framework;
	//Infrastructure infrastructure;

	public MyResourceManager()
	{
	}

	private static final Logger LOGGER = Logger.getGlobal();

	@Override
	public void setFramework(Framework _framework) {
		framework=_framework;
	}

	@Override
	public void newServiceState() throws Exception {

//TODO questa funzione non esplora tutto lo spazio delle soluzioni
		HashMap<String,Resource> resourcesUsed = new HashMap<>();

		LOGGER.info("Computing the new service state");
		Infrastructure infrastructure = framework.getInfrastructure();

		LOGGER.info("Updating available resources");
		infrastructure.updateResourceAvailable();
		//LOGGER.info("Releasing all used resources");
		//infrastructure.freeAllResources();

		LOGGER.info("Getting available resources");
		List<Resource> resources = infrastructure.getResources();

/*
		for(Resource resource : resources)
		{
			if (resource.isUsed()) {
				resourcesUsed.put(resource.getId(), resource);
			}
		}
*/

		for (ElementaryService elementaryService : framework.getSelfOrchestrator().getElementaryServices())
		{
			LOGGER.info("Selecting best implementation for the elementary service '"+elementaryService.getName()+"'");

			BestImplementation bestImplementation = null;
			for (Implementation implementation : elementaryService.getImplementations())
			{
				LOGGER.info("Checking implementation '"+implementation.getName()+"' for '"+elementaryService.getName()+"'");

				for (ResourceRequirement resourceRequirement : implementation.getResourceRequirement())
				{
					LOGGER.info("Checking ResourceRequirement for '"+resourceRequirement.getResourceClass().getName()+"'");

					for (Resource resource : resources)
					{
						LOGGER.info("Checking for Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"'");

						if ( resourceRequirement.getResourceClass().isInstance(resource) )
						{
							LOGGER.info("Checking if Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"' is used or not");
							Resource isUsed = resourcesUsed.get(resource.getId());

							if ( isUsed == null )
							{
								LOGGER.info("Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"' is free");
								resourcesUsed.put(resource.getId(), resource);

								LOGGER.info("Checking constraint for Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"'");
								if ( resourceRequirement.checkConstraint(resourcesUsed.values()))
								{
									Double bestQos = Double.NaN;
									if (bestImplementation != null)
									{
										bestQos = bestImplementation.qos();
									}

									Double currentQos = implementation.getQoS(resourcesUsed.values());

									LOGGER.info("Current best qos is "+bestQos.toString()+" current qos is "+currentQos.toString());
									if (bestImplementation == null || bestQos < currentQos) {
										LOGGER.info("New best implementation found");
										bestImplementation = new BestImplementation(implementation, new ArrayList<>(resourcesUsed.values()));
									} else {
										//resourcesUsed.remove(resource.getId());
									}
								}
								else
								{
									LOGGER.info("Checking constraints failed");
								}
							}
							else
							{
								LOGGER.info("Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"' is already used");
							}
						}
						else
						{
							LOGGER.info("Resource '"+resource.getClass().getName()+" with id '"+resource.getId()+"' is not valid for the ResourceRequirement");
						}
					}
				}

				if ( bestImplementation == null)
				{
					System.err.println("ERROR: unable to find a valid implementation for the elementary service '"+elementaryService.getName()+"'");
					System.exit(1);
				}
				elementaryService.setCurrentImplementation(bestImplementation.getImplementation());

				resourcesUsed.clear();
				for(Resource resource : bestImplementation.getResourcesUsed())
				{
					LOGGER.info("Setting the resource with id '"+resource.getId()+"' used by the elementary service '"+elementaryService.getName()+"'");
					infrastructure.useResource(resource,elementaryService);
					resourcesUsed.put(resource.getId(),resource);
				}
			}
		}
		LOGGER.info("Committing changes to infrastructure");
		infrastructure.commit();
	}
}
