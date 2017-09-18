package it.polito.netgroup.selforchestratingservices;

import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.DirtyChecker;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.DirtyCheckerEventHandler;

import java.security.InvalidParameterException;
import java.util.*;

public class TimeoutMap<K, V> implements DirtyChecker<K,V>
{
	private DirtyCheckerEventHandler timeoutEventHandler;
	private HashMap<K,TimeoutEntry<V>> hashmap;
	private int expirems;
	private String name;

	public TimeoutMap(String _name,int _expirems)
	{
		hashmap = new HashMap<>();
		expirems = _expirems;
		timeoutEventHandler = null;
		name = _name;
	}

	public void setEventHandler(DirtyCheckerEventHandler _handler)
	{
		timeoutEventHandler = _handler;
	}

	public void put(K key, V value)
	{
		TimeoutEntry<V> entry = hashmap.get(key);
		
		if ( entry == null)
		{
			hashmap.put(key, new TimeoutEntry<>(value,expirems));
		}
		else
		{
			entry.setValue(value);
			entry.setCountOff();
		}
	}

	public void startCounter(String key)
	{
		TimeoutEntry<V> entry = hashmap.get(key);
		if ( key != null && !entry.isDeleted())
		{
				entry.setCountOn();
		}
//		throw new InvalidParameterException("Unable to find element with key equal to '"+key+"'");
	}

	@Override
	public boolean check() throws Exception {
		Boolean ret = false;
		for(Map.Entry<K, TimeoutEntry<V>> entry : hashmap.entrySet())
		{
			TimeoutEntry<V> timeoutEntry = entry.getValue();
			if ( timeoutEntry.isExpired() || timeoutEntry.isDeleted() )
			{
				if ( timeoutEventHandler.on_del(entry.getKey(),timeoutEntry.getValue()) )
				{
					ret=true;
				}

				hashmap.remove(entry.getKey());

			}
			else if ( timeoutEntry.isNew() )
			{
				if ( timeoutEventHandler.on_new(entry.getKey(),timeoutEntry.getValue()) )
				{
					ret=true;
				}
				timeoutEntry.setNotNew();
			}
		}

		return ret;
	}

	@Override
	public String getName() {
		return name;
	}

	public List<V> values() {
		List<V> ret = new ArrayList<>();
		for(TimeoutEntry<V> entry : hashmap.values())
		{
			ret.add(entry.getValue());
		}

		return ret;
	}
}
