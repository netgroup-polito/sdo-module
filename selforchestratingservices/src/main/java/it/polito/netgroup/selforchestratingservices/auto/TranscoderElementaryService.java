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
public class TranscoderElementaryService extends AbstractElementaryService
{
	public TranscoderElementaryService(Variables var)
	{
		super(var);
		List<Class<? extends VNFTemplate>> resourceTemplates = new ArrayList<>();
		resourceTemplates.add(TranscoderInfrastructureVNFInfrastructureVNFTemplate.class);
		implementations.add(new Transcodertrcd1Implementation(var,resourceTemplates));
		name = "transcoder";
	}
}