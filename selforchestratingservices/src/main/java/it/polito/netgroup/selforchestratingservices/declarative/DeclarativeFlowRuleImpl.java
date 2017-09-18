package it.polito.netgroup.selforchestratingservices.declarative;

public class DeclarativeFlowRuleImpl implements DeclarativeFlowRule
{
	String id;
	String matchPortIn;
	String matchDstMac;
	String matchSrcMac;
	String outputPort;
	Integer priority;
	Boolean toremove=false;
	Boolean isnew=false;
	Boolean bidirectional=false;
	
	
	@Override
	public void linkPorts(String _id, String port1, String port2)
	{
		isnew=true;
		bidirectional=true;
		id = _id;
		matchPortIn = port1;
		outputPort = port2;
		priority = 1;
	}
	

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void setId(String _id)
	{
		id= _id;
	}

	@Override
	public void setMatchSourceMac(String value)
	{
		matchSrcMac = value;
	}

	@Override
	public void setPriority(int i)
	{
		priority = i;
	}

	@Override
	public void setMatchDestMac(String value)
	{
		matchDstMac = value;
	}

	@Override
	public void setActionOutputToPort(String string)
	{
		outputPort = string;
	}

	@Override
	public void setMatchPortIn(String port)
	{
		matchPortIn = port;
	}

	@Override
	public String getMatchSourceMac()
	{
		return matchSrcMac;
	}

	@Override
	public Integer getPriority()
	{
		return priority;
	}

	@Override
	public String getMatchDestMac()
	{
		return matchDstMac;
	}

	@Override
	public String getActionOutputToPort()
	{
		return outputPort;
	}

	@Override
	public String getMatchPortIn()
	{
		return matchPortIn;
	}


	@Override
	public void setToRemove()
	{
		toremove = true;
	}

	@Override
	public boolean toRemove()
	{
		return toremove;
	}

	
	@Override
	public boolean isBidirectional()
	{
		return bidirectional;
	}

}
