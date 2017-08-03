package it.polito.netgroup.selforchestratingservices.declarative;

public interface DeclarativeFlowRule
{

	void linkPorts(String id, String port1, String port2);

	String getId();

	String getPort1();

	String getPort2();
}
