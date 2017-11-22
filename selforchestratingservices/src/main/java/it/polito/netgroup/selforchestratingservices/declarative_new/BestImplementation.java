package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BestImplementation {

	Implementation implementation;
	List<Resource> resources;
	Double _qos;
	ConfigurationSDN configuration;

	public BestImplementation(Double __qos,Implementation _implementation, ConfigurationSDN _cfg, List<Resource> _resources) {
		implementation = _implementation;
		resources = new ArrayList<>(_resources);
		_qos = __qos;
		configuration = _cfg;
	}



	public Double getQos() {
		return _qos;
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public List<Resource> getResourcesUsed() {
		return resources;
	}

	public ConfigurationSDN getConfiguration() {
		return configuration;
	}
}
