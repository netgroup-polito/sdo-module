package it.polito.netgroup.selforchestratingservices.declarative;

public interface EventHandler
{

	void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource, Object args);

}
