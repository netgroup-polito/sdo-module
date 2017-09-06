package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;

public abstract class AbstractBasicInfrastructureResource implements InfrastructureResource
{
	String id;
	String type;
	Boolean used;
	Boolean instantiated;
	Boolean isConfigured;
	
	List<DeclarativeFlowRule> default_flowrules;
	List<DeclarativeFlowRule> custom_flowrules;

	ConfigurationSDN configuration;
	
	public AbstractBasicInfrastructureResource(String _id, String _type)
	{
		used = false;
		instantiated = false;
		id = _id;
		type = _type;
		default_flowrules = new ArrayList<>();
		custom_flowrules = new ArrayList<>();
		isConfigured = false;
	}
	
	@Override
	public String getId()
	{
		return id;
	}
	
	@Override
	public String getType()
	{
		return type;
	}

	@Override
	public boolean isUsed()
	{
		return used;
	}

	@Override
	public void setUsed()
	{
		used = true;
	}

	@Override
	public void unsetUsed()
	{
		used = false;
	}

	@Override
	public boolean isInstantiated()
	{
		return instantiated;
	}

	@Override
	public void setInstantiated()
	{
		instantiated = true;
	}

	@Override
	public void unsetInstantiated()
	{
		instantiated = false;
	}
	
	@Override
	public List<DeclarativeFlowRule> getFlowRules()
	{
		List<DeclarativeFlowRule> ret = new ArrayList<>(default_flowrules);
		ret.addAll(custom_flowrules);
		return ret;
	}

	
	@Override
	public void setDefaultFlowRules(List<DeclarativeFlowRule> defaultFlowRules)
	{
		default_flowrules = new ArrayList<>(defaultFlowRules);
	}

	@Override
	public void setConfiguration(ConfigurationSDN configuration)
	{
		this.configuration = configuration;
	}

	@Override
	public ConfigurationSDN getConfiguration()
	{
		return configuration;
	}
	
	
	@Override
	public void addFlowRules(List<DeclarativeFlowRule> flowRules)
	{
		custom_flowrules.addAll(flowRules);
	}


	@Override
	public void addFlowRule(DeclarativeFlowRule flowRules)
	{
		custom_flowrules.add(flowRules);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public boolean isConfigured()
	{
		return isConfigured;
	}
	
	@Override
	public void setConfigured()
	{
		isConfigured = true;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractBasicInfrastructureResource other = (AbstractBasicInfrastructureResource) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
