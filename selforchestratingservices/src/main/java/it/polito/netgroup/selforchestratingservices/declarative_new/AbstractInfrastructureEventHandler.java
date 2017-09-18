package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.Variables;

public abstract class AbstractInfrastructureEventHandler implements InfrastructureEventHandler {

	protected Variables var;

	public AbstractInfrastructureEventHandler(Variables _var)
	{
		var = _var;
	}
}
