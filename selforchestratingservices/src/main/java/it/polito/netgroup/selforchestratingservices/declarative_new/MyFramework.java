package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.DirtyChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MyFramework implements Framework {

	List<DirtyChecker> dirtyCheckers;

	SelfOrchestrator selfOrchestrator;
	Infrastructure infrastructure;
	ResourceManager resourceManager;

	private static final Logger LOGGER = Logger.getGlobal();

	public MyFramework()
	{
		dirtyCheckers = new ArrayList<>();
		selfOrchestrator = null;
		infrastructure = null;
		resourceManager = null;
	}

	@Override
	public void setSelfOrchestrator(SelfOrchestrator _selfOrchestrator)
	{
		selfOrchestrator = _selfOrchestrator;
	}

	@Override
	public SelfOrchestrator getSelfOrchestrator() {
		return selfOrchestrator;
	}

	@Override
	public void setResourceManager(ResourceManager _resourceManager) {
		resourceManager = _resourceManager;
		resourceManager.setFramework(this);

	}
	@Override
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	public void setInfrastructure(Infrastructure _infrastructure) {
		infrastructure = _infrastructure;
	}
	@Override
	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	@Override
	public void mainLoop() {
		while (true) {
			Boolean masterSomeChange = false;
			Boolean someChanges;

			do {
				someChanges = false;
				for (DirtyChecker dc : dirtyCheckers) {
					LOGGER.info("Check DirtyChecker " + dc.getName());

					try {
						if (dc.check()) {
							someChanges = true;
							masterSomeChange = true;
							LOGGER.info("Some change are detected inside the DirtyChecker " + dc.getName());
						}
					} catch (Exception e) {
						LOGGER.warning("Exception catched during DirtyChecker '" + dc.getName() + "' check: " + e.getMessage());
						e.printStackTrace();
					}

				}
			}while(someChanges);

			if (masterSomeChange)
			{
				try {
					LOGGER.info("Computing the new service state");
					resourceManager.newServiceState();
				}
				catch(Exception e)
				{
					LOGGER.severe("Error during the computation of the new service state: "+e.getMessage());
					e.printStackTrace();
					System.exit(1);
				}
			}

			LOGGER.info("Going to sleep ");
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
	}

	@Override
	public void addDirtyChecker(DirtyChecker dirtyChecker) {
		dirtyCheckers.add(dirtyChecker);
	}

}
