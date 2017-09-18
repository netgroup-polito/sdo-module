package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.Resource;

public interface InfrastructureEventHandler
{
	void on_resource_added(Resource r) throws Exception;
	void on_resource_removing(Resource r) throws Exception;
	void on_resource_removed(Resource r) throws Exception;
}
