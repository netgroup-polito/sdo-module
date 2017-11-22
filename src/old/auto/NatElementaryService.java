package it.polito.netgroup.selforchestratingservices.auto;

import it.polito.netgroup.selforchestratingservices.AbstractElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.*;

//AUTO
public class NatElementaryService extends AbstractElementaryService
{	
	public NatElementaryService(Variables var)
	{
		super(var);
		name = "nat";
	}
}
