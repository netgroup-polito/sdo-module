package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.HashMap;

public class VariablesImplementation implements Variables
{
	HashMap<String,Object> map;
	
	public VariablesImplementation()
	{
		map = new HashMap<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getVar(String name, Class<T> type) throws InvalidVarNameException, InvalidVarTypeException
	{
		Object var = map.get(name);
		if ( var == null)
		{
			throw new InvalidVarNameException();
		}
		if ( ! type.isInstance(var) )
		{
			throw new InvalidVarTypeException();
		}
		
		return (T) var;
	}

	@Override
	public <T> void setVar(String name, T obj)
	{
		map.put(name, obj);		
	}

}
