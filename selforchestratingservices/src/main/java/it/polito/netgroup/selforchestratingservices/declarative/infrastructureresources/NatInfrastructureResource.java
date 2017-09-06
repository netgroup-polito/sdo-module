package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;


public class NatInfrastructureResource extends AbstractBasicInfrastructureResource
{

	public NatInfrastructureResource(String _id)
	{
		super(_id, "NAT");
	}

	
	@Override
	public NatInfrastructureResource copy()
	{
		return new NatInfrastructureResource(id);
	}

	@Override
	public boolean isVNF()
	{
		return true;
	}
}
