package it.polito.netgroup.selforchestratingservices;

import java.security.InvalidParameterException;
import java.util.Date;

public class TimeoutEntry<V>
{
	private Date lastUpdate;
	private int expirems;
	private Boolean countOn;
	private Boolean isDeleted;
	private Boolean isNew;
	private V value;
	
	public TimeoutEntry(V _value, int _expirems)
	{
		value = _value;
		lastUpdate = new Date();
		countOn = false;
		expirems = _expirems;
		isDeleted = false;
		isNew = true;
	}
	
	public V getValue()
	{
		return value;
	}
	
	public void setValue(V _value)
	{
		if (!isDeleted) {
			value = _value;
			lastUpdate = new Date();
		}
		else
		{
			throw new InvalidParameterException("Unable to set value on deleted entry");
		}
	}
	
	public void setCountOn()
	{
		countOn = true;
	}
	
	public void setCountOff()
	{
		countOn = false;
	}
	
	public Boolean getCountStatus()
	{
		return countOn;
	}


	public Boolean isExpired()
	{
		if ( countOn )
		{
			Date expireDate = new Date(System.currentTimeMillis() + expirems);

			if ( lastUpdate.compareTo(expireDate) >= 0 )
			{
				return true;
			}
		}
		return false;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNotNew() {
		isNew = false;
	}
}
