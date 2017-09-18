package it.polito.netgroup.selforchestratingservices.declarative;

public interface Variables
{
	<T> void setVar(String name, T obj);

	<T> T getVar(String name, Class<T> type) throws InvalidVarNameException, InvalidVarTypeException;
}
