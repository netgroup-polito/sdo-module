package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.Collection;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.TranscoderInfrastructureVNF;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;
import it.polito.netgroup.selforchestratingservices.declarative_new.ResourceSet;

public interface ResourceRequirement
{
	ResourceType getResourceType();

	Integer minimum(ConfigurationSDN configuration);
}