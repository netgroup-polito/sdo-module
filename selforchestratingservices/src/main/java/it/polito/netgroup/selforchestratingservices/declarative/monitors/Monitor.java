package it.polito.netgroup.selforchestratingservices.declarative.monitors;

import java.util.Set;

import org.reflections.Reflections;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorFrog4;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;


public interface Monitor
{
	public boolean isValidForType(String type);
	public void checkEvents();
	public void addResource(InfrastructureResource resource);
	public void removeResource(InfrastructureResource resource);
	public void setTenantId(String tenant_id);
	public void setNFFGName(String nffg_name);
	public void setConfigurationService(ConfigurationOrchestratorFrog4 configurationService);
	public void setInfrastructure(Infrastructure infrastructure);

	public static Monitor getMonitor(String type, InfrastructureImplementation infrastructureImplementation)
	{		
		Reflections reflections = new Reflections(Monitor.class.getPackage());    
		Set<Class<? extends Monitor>> classes = reflections.getSubTypesOf(Monitor.class);
		
		for(Class<? extends Monitor> monitorClass : classes)
		{
			try
			{
				Monitor m = monitorClass.newInstance();
				
				if ( m.isValidForType(type) ) return m;
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
