package it.polito.netgroup.selforchestratingservices.auto.prova;

import it.polito.netgroup.selforchestratingservices.AbstractElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.*;

//Autogenerted class
public class NatElementaryService extends AbstractElementaryService
{
	public NatElementaryService(Variables var)
	{
		super(var);
		implementations.add(new NatImplementation1(var));
		name = "nat";
	}
}