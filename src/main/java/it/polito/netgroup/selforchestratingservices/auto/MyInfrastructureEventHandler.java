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

public class MyInfrastructureEventHandler extends AbstractInfrastructureEventHandler {

	public MyInfrastructureEventHandler(Variables var)
	{
		super(var);
	};

	@Override
	public void on_resource_added(Resource resource) throws Exception {
	}

	@Override
	public void on_resource_removing(Resource resource) throws Exception {
	}

	@Override
	public void on_resource_removed(Resource resource) throws Exception {
	}
	public void on_vnf_added(InfrastructureVNF ivnf) throws Exception {
	}

	@Override
	public void on_vnf_removing(InfrastructureVNF ivnf) throws Exception {
	}

	@Override
	public void on_vnf_removed(InfrastructureVNF ivnf) throws Exception {
	}
}