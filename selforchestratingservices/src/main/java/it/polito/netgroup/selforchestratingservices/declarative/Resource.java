package it.polito.netgroup.selforchestratingservices.declarative;


public interface Resource
{
	public String getType();
	public String getId();
	public boolean isUsed();
}
