package it.polito.netgroup.nffg.json;

public class PortUniqueID
{

	private String port_nffg_unique_id;

	public PortUniqueID(String string)
	{
		port_nffg_unique_id = string;
	}

	public PortUniqueID(VNF vnf, Port port)
	{
		port_nffg_unique_id = "vnf:"+vnf.getId()+":"+port.getName()+":"+port.getId();
	}

	public String getValue()
	{
		return port_nffg_unique_id;
	}

	public String getVnfId()
	{
		if (getType().equals("vnf"))
		{
			return port_nffg_unique_id.split(":")[1];
		}
		return "";
	}

	public String getType()
	{
		return port_nffg_unique_id.split(":")[0];
	}

	public VnfPort getVnfPort()
	{
		if (port_nffg_unique_id.split(":")[0].equals("vnf"))
		{
			return new VnfPort(port_nffg_unique_id.split(":", 3)[2]);
		}
		return null;
	}

	public String getEndpointName()
	{
		if (getType().equals("endpoint"))
		{
			return port_nffg_unique_id.split(":")[1];
		}
		return "";
	}
}
