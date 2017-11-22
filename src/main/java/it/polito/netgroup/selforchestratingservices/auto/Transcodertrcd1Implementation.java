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
public class Transcodertrcd1Implementation extends AbstractImplementation
{
	TranscoderInfrastructureVNF ivnf=null;
	public Transcodertrcd1Implementation(Variables var,List<Class<? extends VNFTemplate>> resourceTemplates)
	{
		super(var,resourceTemplates,new TranscoderConfiguration("","","",""));
		resources.add(new Transcodertrcd1CPUResourceRequirement(var));
		name = "trcd1";
	}

	@Override
	public TranscoderInfrastructureVNF getInfrastructureVNF(Infrastructure infrastructure) {
		if ( ivnf == null ) ivnf = new TranscoderInfrastructureVNF(infrastructure,name);
		return ivnf;
	}

	@Override
	public ConfigurationSDN getConfiguration(ConfigurationSDN actualConfiguration, Integer configurations) {
		TranscoderConfiguration configuration = (TranscoderConfiguration)actualConfiguration.copy();

		if ( configurations == 100)
		{
			configuration.setEnabled(true);
			configuration.setTranscoderTemplate("transcode");
		}
		else
		if ( configurations == 50)
		{
			configuration.setEnabled(true);
			configuration.setTranscoderTemplate("TransrateLight");
		}
		else
		{}

		return configuration;
	}}
