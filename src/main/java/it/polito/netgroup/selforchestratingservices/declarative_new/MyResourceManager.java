package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.*;

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
	public void newServiceConfiguration() throws Exception {

//TODO questa funzione non esplora tutto lo spazio delle soluzioni
		//HashMap<String,Resource> resourcesUsed = new HashMap<>();

		LOGGER.info("Computing the new service state");
		Infrastructure infrastructure = framework.getInfrastructure();

		LOGGER.info("Updating available resources");
		infrastructure.updateResourceAvailable();
		//LOGGER.info("Releasing all used resources");
		//infrastructure.freeAllResources();

		LOGGER.info("Getting available resources");
		List<Resource> resources = infrastructure.getResources();

		System.out.println("Available resources:");
		for(Resource r: resources)
		{
			System.out.println("Resource ID:"+r.getId()+" TYPE:"+r.getType().name()+" USED:"+r.isUsed());
		}

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
			LOGGER.info("Selecting the best implementation for the elementary service '"+elementaryService.getName()+"'");

			BestImplementation bestImplementation = null;
			//Init with current used resources by this elementaryService
			//bestImplementation = new BestImplementation(elementaryService.getCurrentImplementation(),elementaryService.getResourcesUsed());
//			for(Resource r : elementaryService.getResourcesUsed())
//			{
//					Resource used = resourcesUsed.get(r.getId());
//					if ( used == null)
//					{
//						resourcesUsed.put(r.getId(),r);
//					}
//					else
//					{
//						//Trigger execption
//					}
//			}

			ResourceSet resourceSet = new ResourceSet(resources);

			qosloop:
			for (Integer qos = 100 ; qos >= 0 ; qos--)
			{
				for (Implementation implementation : elementaryService.getImplementations())
				{
					LOGGER.info("Checking the implementation '" + implementation.getName() + "' for '" + elementaryService.getName() + "' with QoS '"+qos+"'");

					ConfigurationSDN actual_config = implementation.getActualConfiguration();
					ConfigurationSDN configuration = implementation.getConfiguration(actual_config,qos);

					Boolean cont= false;
					for (ResourceRequirement resourceRequirement : implementation.getResourceRequirement()) {
						Integer min = resourceRequirement.minimum(configuration);
						LOGGER.info("Min "+min);
						LOGGER.info("Free "+resourceSet.getFreeOfType(resourceRequirement.getResourceType()).size());

						if ( min > resourceSet.getFreeOfType(resourceRequirement.getResourceType()).size() ) {
							LOGGER.info("ResourceRequirement for "+resourceRequirement.getResourceType()+" is not satified.");
							cont = true;
							break;
						}
						resourceSet.setResourceUsed(implementation,resources,min);
					}
					if (cont) {
						resourceSet.removeImplementation(implementation);
						continue;
					}

					Double bestQos = Double.NaN;
					if (bestImplementation != null) {
						bestQos = bestImplementation.getQos();
					}

					Double currentQos = new Double(qos);

					LOGGER.info("The current best qos is " + bestQos.toString() + " current qos is " + currentQos.toString());
					if (bestImplementation == null ||
							bestQos < currentQos ||
							(bestImplementation.getQos().equals(currentQos) && bestImplementation.getResourcesUsed().size() < resourceSet.getResourcesOf(implementation).size()) //TODO migliorare
							) {
						LOGGER.info("New best implementation found");
						bestImplementation = new BestImplementation(currentQos,implementation,configuration,resourceSet.getResourcesOf(implementation));
					} else {
						resourceSet.removeImplementation(implementation);
					}
				}

				if ( bestImplementation != null )
				{
					break;
				}
			}
			if ( bestImplementation == null)
			{
				System.err.println("ERROR: unable to find a valid implementation for the elementary service '"+elementaryService.getName()+"'");
				System.exit(1);
			}
			elementaryService.setCurrentImplementation(bestImplementation.getImplementation());

			LOGGER.info("Setting the resources used by the elementary service '"+elementaryService.getName()+"'");
			infrastructure.useInfrastructureVNF(bestImplementation.getImplementation(),bestImplementation.getConfiguration(),bestImplementation.getResourcesUsed(),elementaryService);
		}
		LOGGER.info("Committing changes to infrastructure");
		infrastructure.commit();
	}
}
