package it.polito.netgroup.selforchestratingservices.declarative.dirtychecker;

public interface DirtyExecute<T> {

	T execute() throws Exception;
}
