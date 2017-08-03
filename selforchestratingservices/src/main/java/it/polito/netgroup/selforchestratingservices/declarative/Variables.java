package it.polito.netgroup.selforchestratingservices.declarative;

public interface Variables
{
	public <T extends Object> void setVar(String name, T obj);

	public <T extends Object> T getVar(String name, Class<T> type) throws InvalidVarNameException, InvalidVarTypeException;
}
