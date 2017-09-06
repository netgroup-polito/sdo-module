package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface ElementaryService
{
	public List<Implementation> getImplementations();
	public String getName();
	public void show();
	public void commit();
	public void setRelizedImplementation(RealizedImplementation realizedImplementation);
	//public ConfigurationSDN getConfiguration();
}