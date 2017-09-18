package it.polito.netgroup.selforchestratingservices.declarative.dirtychecker;

public interface DirtyCheckerEventHandler<K, V>
{
	boolean on_new(K object, V value) throws Exception;
	boolean on_del(K object, V value) throws Exception;
	boolean on_change(K object, V old, V now) throws Exception;
}
