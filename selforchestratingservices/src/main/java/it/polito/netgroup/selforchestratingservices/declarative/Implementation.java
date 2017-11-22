package it.polito.netgroup.selforchestratingservices.declarative;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative_new.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

import java.util.List;

public interface Implementation
{
	void updateActualConfiguration(ConfigurationSDN configuration);

	String getName();
	List<ResourceRequirement> getResourceRequirement();

	VNFTemplate getTemplate(Class<? extends InfrastructureVNF> aClass);

	InfrastructureVNF getInfrastructureVNF(Infrastructure infrastructure);

	ConfigurationSDN getConfiguration(ConfigurationSDN actual_config,Integer qos);

	ConfigurationSDN getActualConfiguration();
}
