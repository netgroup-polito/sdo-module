package it.polito.netgroup.selforchestratingservices.auto;

import java.security.*;
import java.util.*;
import java.util.stream.*;
import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.declarative_new.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.*;

//Autogenerted class
public class NatElementaryService extends AbstractElementaryService
{
	public NatElementaryService(Variables var)
	{
		super(var);
		List<Class<? extends ResourceTemplate>> resourceTemplates = new ArrayList<>();
		resourceTemplates.add(NatInfrastructureResourceResourceTemplate.class);
		implementations.add(new NatImplementation1Implementation(var,resourceTemplates));
		name = "nat";
	}
}