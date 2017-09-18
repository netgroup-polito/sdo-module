package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import java.util.ArrayList;
import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceTemplate;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureResource;

public abstract class AbstractBasicInfrastructureResource implements InfrastructureResource
{
	//TODO rivedere la questione delle flowrules !!!!!!!!!!!!!!!!!!!!!!


	String id;
	Boolean used;
	Boolean instantiated;
	ResourceTemplate template;
	Infrastructure infrastructure;
	
	List<DeclarativeFlowRule> default_flowrules;
	List<DeclarativeFlowRule> custom_flowrules;
	ElementaryService usedBy;

	ConfigurationSDN configuration;
	
	public AbstractBasicInfrastructureResource(Infrastructure _infrastructure, String _id)
	{
		infrastructure = _infrastructure;
		used = false;
		instantiated = false;
		id = _id;
		default_flowrules = new ArrayList<>();
		custom_flowrules = new ArrayList<>();
		template = null;
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
	public void setUsed(ElementaryService elementaryService)
	{
		used = true;
		usedBy = elementaryService;
	}

	@Override
	public ElementaryService getUsedBy()
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
	public void setConfiguration(ConfigurationSDN configuration)
	{
		this.configuration = configuration;
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
	public void applyTemplate(ResourceTemplate _template)
	{
		template = _template;
		default_flowrules = template.getDefaultFlowRules();
		configuration = template.getDefaultConfiguration();
	}

	@Override
	public ResourceTemplate getTemplate()
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
		AbstractBasicInfrastructureResource other = (AbstractBasicInfrastructureResource) obj;
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
