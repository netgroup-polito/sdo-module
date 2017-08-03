package it.polito.netgroup.nffg.json;

public class Host
{

	private String ip;
	private MacAddress mac;

	public Host(String _ip, MacAddress _mac)
	{
		ip = _ip;
		mac = _mac;
	}

	public MacAddress getMacAddress()
	{
		return mac;
	}

	public String getIp()
	{
		return ip;
	}
}
