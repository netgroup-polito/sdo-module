package it.polito.netgroup.selforchestratingservices.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.selforchestratingservices.declarative.AbstractSelfOrchestrator;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRuleImpl;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.EventHandler;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarNameException;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarTypeException;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;
import it.polito.netgroup.selforchestratingservices.declarative.VariablesImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;


//AUTO_FILE

public class SelfOrchestratorDichiarativoNAT extends AbstractSelfOrchestrator
{
	Variables variables = new VariablesImplementation();
	//LoadBalancerElementaryService loadBalancerElementaryService;
	NatElementaryService natElementaryService;

	public SelfOrchestratorDichiarativoNAT(Infrastructure _infrastructure)
	{
		MacAddress nat_mac = new MacAddress("02:01:02:03:04:05");
		String port = "vnf:SWITCH_LAN:Port0";
		variables.setVar("hosts", new ArrayList<Host>());
		variables.setVar("counter", new Integer(0));
		
        natElementaryService = new NatElementaryService(variables);
        
        //loadBalancerElementaryService = new LoadBalancerElementaryService(variables);
        
        
        _infrastructure.addHandler("on_host_new", new EventHandler()
		{			
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource, Object args)
			{
				Host host = (Host) args;
				try
				{
					variables.getVar("hosts", List.class).remove(host);
					Integer counter = variables.getVar("counter", Integer.class);
					
					NatInfrastructureResource resource = (NatInfrastructureResource) on_resource;
					
					DeclarativeFlowRule dfr = new DeclarativeFlowRuleImpl();
					dfr.isNew();
					dfr.setId("LB_"+host.getMacAddress().getValue()+counter);
					dfr.setMatchPortIn(port);
					dfr.setMatchSourceMac(host.getMacAddress().getValue());
					dfr.setMatchDestMac(nat_mac.getValue());
					dfr.setActionOutputToPort("vnf:"+resource.getId()+":User");
					dfr.setPriority(2);
					
					resource.addFlowRule(dfr);

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
//					loadBalancerElementaryService.addHost(host);
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
        _infrastructure.addHandler("on_NAT_fault", new EventHandler()
		{	
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
//				loadBalancerElementaryService.removeNat((VnfForConfiguration) args);
			}
		});
        _infrastructure.addHandler("on_NAT_removed", new EventHandler()
		{
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
//				loadBalancerElementaryService.removeNat((VnfForConfiguration) args);
			}
		});
        _infrastructure.addHandler("on_NAT_new", new EventHandler()
		{
			@Override
			public void fire(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource,
					Object args)
			{
//				loadBalancerElementaryService.addNat((VnfForConfiguration) args);
			}
		});

        HashMap<String,ElementaryService> elementaryServices = new HashMap<>();
        elementaryServices.put(natElementaryService.getName(),natElementaryService);
//        elementaryServices.put(loadBalancerElementaryService.getName(),loadBalancerElementaryService);
        
        init("NAT_LB",elementaryServices,_infrastructure);
    }


	@Override
	public void commit()
	{
		super.commit();
	}
}