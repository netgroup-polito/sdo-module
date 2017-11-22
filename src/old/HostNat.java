package it.polito.netgroup.selforchestratingservices.old;

import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;

//AUTO
public class HostNat
{
	Host host;
	NatInfrastructureResource nat;
	
	public HostNat(Host _host , NatInfrastructureResource _nat)
	{
		host = _host;
		nat = _nat;
	}

	public NatInfrastructureResource getNat()
	{
		return nat;
	}
	
	public Host getHost()
	{
		return host;
	}
}
