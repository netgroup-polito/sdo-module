package it.polito.netgroup.nffg.json;

public class IpAddressAndNetmask
{
	private String ip;
	private String netmask;

	public IpAddressAndNetmask(String _ip, String _netmask)
	{
		ip = _ip;
		netmask = _netmask;
	}

	public String getIp()
	{
		return ip;
	}

	public String getNetmask()
	{
		return netmask;
	}

}
