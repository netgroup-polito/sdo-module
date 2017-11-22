package it.polito.netgroup.selforchestratingservices.declarative.dirtychecker;

public abstract class EqualKeyObject
{
	public String getKey()
	{
		return "";
	}
	
	public abstract Boolean equalKey(EqualKeyObject obj);
	public abstract EqualKeyObject copy();
}
