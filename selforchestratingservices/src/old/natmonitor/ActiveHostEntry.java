package it.polito.netgroup.selforchestratingservices.old.natmonitor;

import java.util.Date;

import it.polito.netgroup.nffg.json.Host;

public class ActiveHostEntry
{
	private Host host;
	private Date last_seen;
	
	public ActiveHostEntry(Host host2, Date date)
	{
		host = host2;
		last_seen = date;
	}

	public Host getHost()
	{
		return host;
	}

	public void setHost(Host host)
	{
		this.host = host;
	}

	public Date getLast_seen()
	{
		return last_seen;
	}

	public void setLast_seen(Date last_seen)
	{
		this.last_seen = last_seen;
	}
}
