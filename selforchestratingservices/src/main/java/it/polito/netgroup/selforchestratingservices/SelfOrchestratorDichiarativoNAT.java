package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.jmx.LayoutDynamicMBean;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.configurationorchestrator.InvalidInterfaceLabel;
import it.polito.netgroup.configurationorchestrator.NatConfiguration;
import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.loadbalancer.LoadBalancer;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.InterfaceLabel;
import it.polito.netgroup.nffg.json.IpAddressAndNetmask;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRuleImpl;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.EventHandler;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarNameException;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarTypeException;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementationImpl;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.AbstractSelfOrchestrator;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;
import it.polito.netgroup.selforchestratingservices.declarative.VariablesImplementation;


//AUTO_FILE

public class SelfOrchestratorDichiarativoNAT extends AbstractSelfOrchestrator
{
	Variables variables = new VariablesImplementation();
	LoadBalancerElementaryService loadBalancerElementaryService;
	NatElementaryService natElementaryService;

	public SelfOrchestratorDichiarativoNAT(Infrastructure _infrastructure)
	{
		MacAddress nat_mac = new MacAddress("02:01:02:03:04:05");

		variables.setVar("hosts", new ArrayList<Host>());
		
        natElementaryService = new NatElementaryService(variables);
        
        loadBalancerElementaryService = new LoadBalancerElementaryService(variables);
        
        
        _infrastructure.addHandler("on_host_new", new EventHandler()
		{			
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource, Object args)
			{
				Host host = (Host) args;
				try
				{
					variables.getVar("hosts", List.class).remove(host);
					loadBalancerElementaryService.removeHost(host);
				} catch (InvalidVarNameException e)
				{
					e.printStackTrace();
				} catch (InvalidVarTypeException e)
				{
					e.printStackTrace();
				}
			}
		});

        _infrastructure.addHandler("on_host_left", new EventHandler()
		{
			
			@SuppressWarnings("unchecked")
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
				Host host = (Host) args;
				try
				{
					variables.getVar("hosts", List.class).add(host);
					loadBalancerElementaryService.addHost(host);
				}
				catch (InvalidVarNameException e)
				{
					e.printStackTrace();
				}
				catch (InvalidVarTypeException e)
				{
					e.printStackTrace();
				}
			}
		});
        _infrastructure.addHandler("on_NAT_crashed", new EventHandler()
		{	
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
				loadBalancerElementaryService.removeNat((VnfForConfiguration) args);
			}
		});
        _infrastructure.addHandler("on_NAT_removed", new EventHandler()
		{
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
				loadBalancerElementaryService.removeNat((VnfForConfiguration) args);
			}
		});
        _infrastructure.addHandler("on_NAT_new", new EventHandler()
		{
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
				loadBalancerElementaryService.addNat((VnfForConfiguration) args);
			}
		});

        HashMap<String,ElementaryService> elementaryServices = new HashMap<>();
        elementaryServices.put(natElementaryService.getName(),natElementaryService);
        elementaryServices.put(loadBalancerElementaryService.getName(),loadBalancerElementaryService);
        
        init("NAT_LB",elementaryServices,_infrastructure);
    }


	@Override
	public void commit()
	{
		//TODO
		super.commit();
	}
}