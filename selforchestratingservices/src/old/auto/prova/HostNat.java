package it.polito.netgroup.selforchestratingservices.auto.prova;

import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;

//Autogenerated code
public class HostNat
{
	Host host;
	NatInfrastructureResource nat;

	public HostNat(Host _host, NatInfrastructureResource _nat)
	{
		host= _host;
		nat= _nat;
	}

	public Host getHost(){
		return host;
	}
	public NatInfrastructureResource getNat(){
		return nat;
	}
}
