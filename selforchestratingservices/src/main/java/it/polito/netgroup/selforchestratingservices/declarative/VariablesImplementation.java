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
		if ( map.get(name) == null)
		{
			throw new InvalidVarNameException();
		}
		if ( ! type.isInstance(map.get(name)) )
		{
			throw new InvalidVarTypeException();
		}
		
		return (T) map.get(name);
	}

	@Override
	public <T> void setVar(String name, T obj)
	{
		map.put(name, obj);		
	}

}
