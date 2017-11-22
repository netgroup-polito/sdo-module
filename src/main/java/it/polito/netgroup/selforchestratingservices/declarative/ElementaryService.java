package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface ElementaryService
{
	List<Implementation> getImplementations();
	String getName();

	void setCurrentImplementation(Implementation implementation);

	Implementation getCurrentImplementation();
}