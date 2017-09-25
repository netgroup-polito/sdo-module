package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.Collection;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;

public interface ResourceRequirement
{
	Class getResourceClass();
	boolean checkConstraint(Collection<Resource> r);
	Double removeCost(Resource r);

}