package it.polito.netgroup.nffg.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VNFExtended extends VNF
{

	public static VNFExtended fromVNF(VNF vnf)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			String json = mapper.writeValueAsString(vnf);
			return mapper.readValue(json, VNFExtended.class);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	@JsonIgnore
	public PortUniqueID getPortUniqueID(Port p)
	{
		return new PortUniqueID("vnf:" + getId() + ":" + p.getId());
	}
	
	@JsonIgnore
	public PortUniqueID getFullnamePortByLabel(String label) throws PortNotFoundException
	{
		for (Port p : getPorts())
		{
			if (p.getId().split(":")[0].equals(label))
			{
				return getPortUniqueID(p);
			}
		}
		throw new PortNotFoundException("Unable to find port on VNF " + getName() + " with label " + label);
	}

	@JsonIgnore
	@Deprecated
	public String getFreePortLabel(NF_FGExtended nffg, String label) throws NoFreePortsException
	{
		for (Port port : getPorts())
		{
			if (port.getId().split(":")[0].equals(label) || port.getId().split(":")[0].startsWith(label))
			{
				List<FlowRule> flowrules = nffg.getFlowRulesSendingTrafficToPort(getPortUniqueID(port));
				if (flowrules.size() <= 0)
				{
					flowrules = nffg.getFlowRulesSendingTrafficFromPort(getPortUniqueID(port));
					if (flowrules.size() <= 0)
					{
						return port.getId().split(":")[0];
					}
				}
			}
		}
		throw new NoFreePortsException("All the ports with label "+label+" are busy on the VNF "+getName());
	}

	@JsonIgnore
	public PortUniqueID getFirstFreeFullnamePortByLabel(NF_FGExtended nffg, String label)
	{
		for (Port port : getPorts())
		{
			if (port.getId().split(":")[0].equals(label) || port.getId().split(":")[0].startsWith(label))
			{
				List<FlowRule> flowrules = nffg.getFlowRulesSendingTrafficToPort(getPortUniqueID(port));
				if (flowrules.size() <= 0)
				{
					flowrules = nffg.getFlowRulesSendingTrafficFromPort(getPortUniqueID(port));
					if (flowrules.size() <= 0)
					{
						return new PortUniqueID("vnf:" + getId() + ":" + port.getId());
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@JsonIgnore
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@JsonIgnore
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VNF other = (VNF) obj;
		if (getId() == null)
		{
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@JsonIgnore
	public boolean existPort(VnfPort vnfPort)
	{
		for (Port p : getPorts())
		{
			if (p.getId().equals(vnfPort.getValue()))
			{
				return true;
			}
		}

		return false;
	}
}
