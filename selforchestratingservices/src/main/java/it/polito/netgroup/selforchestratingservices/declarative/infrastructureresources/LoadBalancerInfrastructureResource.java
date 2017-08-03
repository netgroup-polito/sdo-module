package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;


public class LoadBalancerInfrastructureResource extends AbstractBasicInfrastructureResource
{
	public LoadBalancerInfrastructureResource(String _id)
	{
		super(_id, "LOADBALANCER");
	}

	@Override
	public LoadBalancerInfrastructureResource copy()
	{
		return new LoadBalancerInfrastructureResource(id);
	}

	@Override
	public boolean isVNF()
	{
		return false;
	}


}
