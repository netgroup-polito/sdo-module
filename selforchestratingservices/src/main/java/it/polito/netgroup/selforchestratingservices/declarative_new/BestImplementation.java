package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BestImplementation {

	Implementation implementation;
	List<Resource> resources;

	public BestImplementation(Implementation _implementation, List<Resource> _resources) {
		implementation = _implementation;
		resources = new ArrayList<>(_resources);
	}



	public Double qos() {
		return implementation.getQoS(resources);
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public List<Resource> getResourcesUsed() {
		return resources;
	}
}
