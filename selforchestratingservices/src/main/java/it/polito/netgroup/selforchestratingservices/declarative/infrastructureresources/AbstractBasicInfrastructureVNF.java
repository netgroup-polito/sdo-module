package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.VNFTemplate;
import it.polito.netgroup.selforchestratingservices.declarative_new.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;

public abstract class AbstractBasicInfrastructureVNF implements InfrastructureVNF
{
	//TODO rivedere la questione delle flowrules !!!!!!!!!!!!!!!!!!!!!!


	String id;
	Boolean used;
	Boolean instantiated;
	VNFTemplate template;
	protected Infrastructure infrastructure;
	
	List<DeclarativeFlowRule> default_flowrules;
	List<DeclarativeFlowRule> custom_flowrules;
	Implementation usedBy;

	ConfigurationSDN configuration;
	Boolean updateConfiguration;
	Boolean modified;

	public AbstractBasicInfrastructureVNF(Infrastructure _infrastructure, String _id)
	{
		infrastructure = _infrastructure;
		used = false;
		instantiated = false;
		modified = false;
		id = _id;
		default_flowrules = new ArrayList<>();
		custom_flowrules = new ArrayList<>();
		template = null;
		updateConfiguration=false;
	}

	@Override
	public String getId()
	{
		return id;
	}


	@Override
	public Boolean isUsed()
	{
		return used;
	}

	@Override
	public void setModified()
	{
		modified=true;
	}

	@Override
	public void setUsed(Implementation implementation)
	{
		used = true;
		usedBy = implementation;
	}

	@Override
	public Implementation getUsedBy()
	{
		return usedBy;
	}

	@Override
	public void unsetUsed()
	{
		used = false;
		usedBy = null;
	}

	@Override
	public Boolean isInstantiated()
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
	public Boolean updateConfigurationNeeded()
	{
		return updateConfiguration;
	}

	@Override
	public void setConfiguration(ConfigurationSDN configuration)
	{
		updateConfiguration = true;
		this.configuration = configuration;
	}

	@Override
	public void resetUpdateConfiguration()
	{
		updateConfiguration = false;
	}

	@Override
	public ConfigurationSDN getConfiguration()
	{
		return configuration;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public void applyTemplate(VNFTemplate _template)
	{
		template = _template;
		default_flowrules = template.getDefaultFlowRules();
		//configuration = template.getDefaultConfiguration();
	}

	@Override
	public VNFTemplate getTemplate()
	{
		return template;
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
		AbstractBasicInfrastructureVNF other = (AbstractBasicInfrastructureVNF) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Infrastructure getInfrastructure() {
		return infrastructure;
	}
}
