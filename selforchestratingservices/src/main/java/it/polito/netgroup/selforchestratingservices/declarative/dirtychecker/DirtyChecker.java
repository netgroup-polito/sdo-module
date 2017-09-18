package it.polito.netgroup.selforchestratingservices.declarative.dirtychecker;

public interface DirtyChecker<K,V>
{
	boolean check() throws Exception;
	void setEventHandler(DirtyCheckerEventHandler<K,V> handler);
	String getName();
}

