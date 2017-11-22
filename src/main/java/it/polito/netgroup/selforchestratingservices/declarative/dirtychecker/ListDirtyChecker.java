package it.polito.netgroup.selforchestratingservices.declarative.dirtychecker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ListDirtyChecker<K,V extends EqualKeyObject> implements DirtyChecker<K, V>
{
	String name;
	K object;
	List<V> listOld;
	DirtyExecute<List<V>> executeMethod;
	DirtyCheckerEventHandler<K,V> eventHandler;

	private static final Logger LOGGER = Logger.getGlobal();

	public ListDirtyChecker(String _name,K _object, DirtyExecute<List<V>> _executeMethod, String check_time,DirtyCheckerEventHandler<K,V> _eventHandler)
	{
		executeMethod = _executeMethod;

		try {
			listOld = new ArrayList<>(executeMethod.execute());
		} catch (Exception e) {
			e.printStackTrace();
			listOld = null;
		}

		eventHandler = _eventHandler;
		object = _object;
		name = _name;
	}


	private List<V> copyList(List<V> orig)
	{
		List<V> ret = new ArrayList<>();

		for(V element : orig)
		{
			V new_element = (V) element.copy();

			ret.add(new_element);
		}

		return ret;
	}

	//TODO scatenare gli eventi al termine della funzione
	public boolean check() throws Exception
	{
		Boolean ret = false;

		List<V> list = new ArrayList<>();

		try
		{
			LOGGER.info("Executing the execute method");
			list = copyList(executeMethod.execute());
			LOGGER.info("list size is now "+list.size());
		}
		catch(Exception e)
		{
			LOGGER.warning("Exception during execute : "+e.getMessage());
			e.printStackTrace();
			throw e;
		}

		for(V elementOld : listOld)
		{
			Boolean found = false;
			for(V elementNow: list)
			{
				if ( elementNow.equals(elementOld))
				{
					found = true;
					break;
				}
				
				if ( elementNow.equalKey(elementOld))
				{
					//Stessa chiave ma non sono uguali
					LOGGER.info("Changed element found : "+elementOld);
					if ( eventHandler.on_change(object, elementOld,elementNow) )
					{
						ret = true;
					}
				}
			}
			if (!found)
			{
				LOGGER.info("Deleted element found : "+elementOld);
				if ( eventHandler.on_del(object, elementOld) )
				{
					ret = true;
				}
			}		
		}
		if ( list.size() == 0 && listOld.size() != 0 )
		{
			for(V elementOld: listOld)
			{
				LOGGER.info("Deleted element found : "+elementOld);
				if ( eventHandler.on_del(object, elementOld) )
				{
					ret = true;
				}
			}
		}
		
		for(V elementNow : list)
		{
			Boolean found = false;
			for(V elementOld: listOld)
			{
				if ( elementOld.equals(elementNow))
				{
					found = true;
					break;
				}
			}
			if (!found)
			{
				LOGGER.info("New element found : "+elementNow);
				if ( eventHandler.on_new(object, elementNow) )
				{
					ret = true;
				}
			}		
		}

		listOld = copyList(list);

		return ret;
	}

	@Override
	public void setEventHandler(DirtyCheckerEventHandler<K, V> handler) {
		eventHandler = handler;
	}

	@Override
	public String getName() {
		return name;
	}

}

