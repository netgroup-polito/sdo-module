package it.polito.netgroup.selforchestratingservices.declarative;

public interface DeclarativeFlowRule
{

	void linkPorts(String _id, String port1, String port2);

	String getId();

	void setId(String string);

	void setMatchSourceMac(String value);
	
	String getMatchSourceMac();

	void setPriority(int i);
	
	Integer getPriority();

	void setMatchDestMac(String value);
	
	String getMatchDestMac();

	void setActionOutputToPort(String string);
	
	String getActionOutputToPort();

	void setMatchPortIn(String port);
	
	String getMatchPortIn();

	void setToRemove();
	boolean toRemove();

	boolean isBidirectional();
	
}
