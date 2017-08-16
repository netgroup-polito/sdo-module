package it.polito.netgroup.selforchestratingservices.declarative;

public class DeclarativeFlowRuleImpl implements DeclarativeFlowRule
{
	String port1;
	String port2;
	String id;
	@Override
	public void linkPorts(String id, String port1, String port2)
	{
		this.id = id;
		this.port1 = port1;
		this.port2 = port2;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getPort1()
	{
		return port1;
	}

	@Override
	public String getPort2()
	{
		return port2;
	}

}
