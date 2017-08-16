package it.polito.netgroup.selforchestratingservices.declarative.monitors;

import java.util.HashMap;
import java.util.Map.Entry;

import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestrator;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorFrog4;
import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.configurationorchestrator.VnfForConfigurationInterface;
import it.polito.netgroup.natmonitor.NatEventHandler;
import it.polito.netgroup.natmonitor.NatMonitor;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.IpNetwork;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;

public class NatMonitorV2 implements Monitor
{
	NatMonitor natMonitor;
	String tenant_id;
	String nffg_name;
	ConfigurationOrchestrator configurationService;
	NatEventHandler event;
	IpNetwork lan;
	Infrastructure infrastructure;
	HashMap<InfrastructureResource,VnfForConfigurationInterface> map;
	
	public NatMonitorV2()
	{
		map = new HashMap<>();
		
		natMonitor = null ; 
		tenant_id = null;
		nffg_name = null;
		configurationService = null;
		infrastructure = null;
		NatMonitorV2 thiz = this;
		lan = new IpNetwork("0.0.0.0", "0.0.0.0");
		event = new NatEventHandler()
		{
			
			@Override
			public boolean on_nat_fault(VnfForConfigurationInterface nat)
			{
				InfrastructureResource r = thiz.getResourceFor(nat);
				infrastructure.raiseEvent("on_NAT_fault", "NatMonitorV2", thiz, r,r);
				return true;
			}


			@Override
			public boolean on_host_new(VnfForConfigurationInterface nat,Host host)
			{
				InfrastructureResource r = thiz.getResourceFor(nat);
				infrastructure.raiseEvent("on_host_new", "NatMonitorV2", thiz, r,host);
				return true;
			}
			
			@Override
			public boolean on_host_left(VnfForConfigurationInterface nat,Host host)
			{
				InfrastructureResource r = thiz.getResourceFor(nat);
				infrastructure.raiseEvent("on_host_left", "NatMonitorV2", thiz, r,host);
				return true;
			}
		};
	}
	
	protected InfrastructureResource getResourceFor(VnfForConfigurationInterface nat)
	{
		for(Entry<InfrastructureResource, VnfForConfigurationInterface> entry : map.entrySet())
		{
			if ( entry.getValue().getId().equals(nat.getId()))
			{
				return entry.getKey();
			}
		}
		return null;
	}

	private void tryInit()
	{
		if ( natMonitor == null )
		{
			if ( tenant_id != null && nffg_name != null && configurationService != null && lan != null )
			{
				natMonitor = new NatMonitor(configurationService, event, 10000, 60000);
			}
		}
	}
	
	@Override
	public boolean isValidForType(String type)
	{
		return type.equals("NAT");
	}

	@Override
	public void checkEvents()
	{
		natMonitor.check_events(lan);
	}

	@Override
	public void addResource(InfrastructureResource resource)
	{
		VnfForConfiguration vfc = new VnfForConfiguration(tenant_id, nffg_name, resource.getId() , "nat");
		map.put(resource, vfc);
		natMonitor.addNat(vfc);
	}

	@Override
	public void removeResource(InfrastructureResource resource)
	{
		natMonitor.removeNat(map.get(resource));
		map.remove(resource);
	}

	@Override
	public void setTenantId(String tenant_id)
	{
		this.tenant_id = tenant_id;
		tryInit();
	}

	@Override
	public void setNFFGName(String nffg_name)
	{
		this.nffg_name = nffg_name;
		tryInit();
	}

	@Override
	public void setConfigurationService(ConfigurationOrchestratorFrog4 configurationService)
	{
		this.configurationService = configurationService;
		tryInit();
	}

	@Override
	public void setInfrastructure(Infrastructure infrastructure)
	{
		this.infrastructure = infrastructure;
	}
}
