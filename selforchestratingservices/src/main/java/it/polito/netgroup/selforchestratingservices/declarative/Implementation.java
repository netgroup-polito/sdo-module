package it.polito.netgroup.selforchestratingservices.declarative;

import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureResource;

import java.util.Collection;
import java.util.List;

public interface Implementation
{
	String getName();
	Double getQoS(Collection<Resource> r);
	List<ResourceRequirement> getResourceRequirement();

	ResourceTemplate getTemplate(Class<? extends Resource> aClass);
}
