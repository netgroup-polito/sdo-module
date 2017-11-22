package it.polito.netgroup.selforchestratingservices.auto;

import it.polito.netgroup.configurationorchestrator.json.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;

public class NewSessionOnSessionBlaBlabal extends AbstractEventHandler
{

	public NewSessionOnSessionBlaBlabal(Variables _var)
	{
		super(_var);
	}
	
	
	public void fire (NatInfrastructureResource nat , NatSession nat_session)
	{
		
	}

}
