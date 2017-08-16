package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.loadbalancer.LoadBalancer;

public interface ElementaryService
{
	public List<Implementation> getImplementations();
	public String getName();
	public void show();
	public void commit();
	public void setRelizedImplementation(RealizedImplementation realizedImplementation);
	//public ConfigurationSDN getConfiguration();
}