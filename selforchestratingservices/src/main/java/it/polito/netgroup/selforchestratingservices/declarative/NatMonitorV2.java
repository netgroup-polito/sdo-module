package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.HashMap;
import java.util.Map.Entry;

import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestrator;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorFrog4;
import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.natmonitor.NatEventHandler;
import it.polito.netgroup.natmonitor.NatMonitor;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.IpNetwork;

public class NatMonitorV2 implements Monitor
{
	NatMonitor natMonitor;
	String tenant_id;
	String nffg_name;
	ConfigurationOrchestrator configurationService;
	NatEventHandler event;
	IpNetwork lan;
	Infrastructure infrastructure;
	HashMap<InfrastructureResource,VnfForConfiguration> map;
	
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
			public boolean on_nat_fault(VnfForConfiguration nat)
			{
				infrastructure.raiseEvent("on_NAT_fault", "NatMonitorV2", thiz, thiz.getResourceFor(nat));
				return true;
			}


			@Override
			public boolean on_host_new(Host n)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean on_host_left(Host n)
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	protected InfrastructureResource getResourceFor(VnfForConfiguration nat)
	{
		for(Entry<InfrastructureResource, VnfForConfiguration> entry : map.entrySet())
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
