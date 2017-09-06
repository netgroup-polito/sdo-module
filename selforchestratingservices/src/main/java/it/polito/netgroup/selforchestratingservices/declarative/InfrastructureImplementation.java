package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorFrog4;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorAuthenticationException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorConfigurationNotFoundException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorHTTPException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorNotAuthenticatedException;
import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.datastoreclient.DatastoreClient;
import it.polito.netgroup.datastoreclient.DatastoreClientAuthenticationException;
import it.polito.netgroup.datastoreclient.DatastoreClientHTTPException;
import it.polito.netgroup.datastoreclient.DatastoreClientNotAuthenticatedException;
import it.polito.netgroup.datastoreclient.DatastoreClientTemplateNotFoundException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestrator;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorAuthenticationException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorHTTPException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorNF_FGNotFoundException;
import it.polito.netgroup.infrastructureOrchestrator.InfrastructureOrchestratorNotAuthenticatedException;
import it.polito.netgroup.nffg.json.Action;
import it.polito.netgroup.nffg.json.DuplicateFlowRuleException;
import it.polito.netgroup.nffg.json.DuplicateVNFException;
import it.polito.netgroup.nffg.json.FlowRule;
import it.polito.netgroup.nffg.json.Match;
import it.polito.netgroup.nffg.json.NF_FGExtended;
import it.polito.netgroup.nffg.json.PortUniqueID;
import it.polito.netgroup.nffg.json.VNFExtended;
import it.polito.netgroup.nffg_template.json.NF_FGTemplateExtended;
import it.polito.netgroup.nffg_template.json.NotImplementedYetException;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.LoadBalancerInfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.monitors.Monitor;

public class InfrastructureImplementation implements Infrastructure
{
	HashMap<String,Monitor> monitors;
	List<InfrastructureResource> resources;
	HashMap<String,List<EventHandler>> handlers;
	
	String nffg_name;
	String tenant_id;
	DatastoreClient datastore;
	ConfigurationOrchestratorFrog4 configurationService;
	InfrastructureOrchestrator orchestrator;
	
	public InfrastructureImplementation(InfrastructureOrchestrator orchestrator,
			DatastoreClient datastore,
			ConfigurationOrchestratorFrog4 configuration_service,
			String nffg_name,
			String tenant_id)
	{
		this.nffg_name = nffg_name;
		this.tenant_id = tenant_id;
		this.configurationService = configuration_service;
		this.orchestrator = orchestrator;
		this.datastore = datastore;
		
		this.monitors = new HashMap<>();
		this.handlers = new HashMap<>();
		this.resources = new ArrayList<>();

        resources.add(new NatInfrastructureResource("NAT_A"));
        resources.add(new NatInfrastructureResource("NAT_B"));
        resources.add(new NatInfrastructureResource("NAT_C"));
        resources.add(new NatInfrastructureResource("NAT_D"));
        resources.add(new NatInfrastructureResource("NAT_E"));
        resources.add(new LoadBalancerInfrastructureResource("LOADBALANCER_A"));

        try
		{
			orchestrator.removeNFFG(nffg_name);
		} catch (InfrastructureOrchestratorHTTPException | InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void freeAllResources()
	{
		for(InfrastructureResource resource : resources)
		{
			resource.unsetUsed();
		}
	}

	@Override
	public void freeResources(List<InfrastructureResource> resourcesUsed)
	{
		for(InfrastructureResource resourceU : resourcesUsed)
		{
			for(InfrastructureResource resource : resources)
			{
				if ( resourceU.equals(resource))
				{
					resource.unsetUsed();
					break;
				}
			}
		}
	}

	@Override
	public void setResourcesUsed(List<InfrastructureResource> resourcesUsed)
	{
		for(InfrastructureResource resourceU : resourcesUsed)
		{
			for(InfrastructureResource resource : resources)
			{
				if ( resourceU.equals(resource))
				{
					resource.setDefaultFlowRules(resourceU.getFlowRules());
					resource.setConfiguration(resourceU.getConfiguration());
					resource.setUsed();
					break;
				}
			}
		}
	}

	@Override
	public void eventLoop()
	{
        while (true)
        	{
	    		if ( monitors.size() == 0 )
	    		{
	    			throw new RuntimeException("Monitor list is empty.");
	    		}
	    		List<Monitor> monitorsCopy = new ArrayList<>(monitors.values());
	    		
        		for(Monitor m : monitorsCopy)
        		{
        			m.checkEvents();
        		}
        		try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				break;
			}
        		

        	}
	}

	@Override
	public void addHandler(String event_name, EventHandler handler)
	{
		if ( handlers.get(event_name) == null)
		{
			handlers.put(event_name, new ArrayList<>());
		}
		handlers.get(event_name).add(handler);
	}
       
	@Override
	public List<InfrastructureResource> getResourcesFreeOfType(String type)
	{
		List<InfrastructureResource> ret = new ArrayList<>();
		
		for(InfrastructureResource resource : resources)
		{
			if ( !resource.isUsed() && resource.getType().equals(type)) ret.add(resource.copy());
		}
		return ret;
	}
	
	@Override
	public void commit()
	{
        //logging.debug("Reading nffg %s" %(self.nffg_name))

        NF_FGExtended nffg=null;
		try
		{
			nffg = orchestrator.getNFFG(nffg_name);
		} catch (InfrastructureOrchestratorHTTPException | InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException
				| InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace();
		}
        //NF_FGExtended nffg = NF_FG(self.nffg_name, self.nffg_name)
        //logging.error("Unable to get NFFG with name '%s' : '%s'" % (self.nffg_name, ex))
         
        List<InfrastructureResource> configurationQueue = new ArrayList<>();
        
        for(InfrastructureResource resource : resources)
        {
    		if (resource.isUsed())
    		{
    			if ( resource.isVNF() && !resource.isInstantiated())
    			{
        			VNFExtended vnf = buildVNF(resource);
        			
        			try
					{
						nffg.addVNF(vnf);
						resource.setInstantiated();
					}
        			catch (DuplicateVNFException e)
					{
						e.printStackTrace();
					}
		    	}
		    			
				for(DeclarativeFlowRule dfr : resource.getFlowRules())
				{
					if ( dfr.isNew() )
					{
						try
						{
							for(FlowRule flowRule : buildFlowRules(nffg,resource,dfr))
							{
								try
								{
									nffg.addFlowRule(flowRule);
									dfr.unsetNew();
								}
								catch (DuplicateFlowRuleException e)
								{
									e.printStackTrace();
								}	
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					if ( dfr.toRemove() )
					{
						nffg.removeFlowRulesStartingWith(dfr.getId());
					}
		    	}
        		
				if ( ! resource.isConfigured() )
				{
					if ( resource.getConfiguration() != null )
					{
						configurationQueue.add(resource);
					}
				}
	        }
        }

        try
		{
			orchestrator.addNFFG(nffg);
		}
        catch (JsonProcessingException | InfrastructureOrchestratorHTTPException
				| InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace();
		}
     
        for (InfrastructureResource resource : configurationQueue)
        {
        
            ConfigurationSDN config = resource.getConfiguration();
            config.setTenantId(tenant_id);
            config.setNffgid(nffg_name);
            config.setVnfId(resource.getId());

            //logging.debug("Updating configuration of %s/%s/%s" % (config.tenant_id, config.nffg_id, config.vnf_id))
            try
			{
				configurationService.waitUntilStarted(config);
				configurationService.setConfiguration(config);

			}
            catch (InterruptedException | ConfigurationOrchestratorHTTPException
					| ConfigurationOrchestratorAuthenticationException
					| ConfigurationOrchestratorConfigurationNotFoundException
					| ConfigurationOrchestratorNotAuthenticatedException e)
			{
				e.printStackTrace();
			}
            catch (JsonProcessingException e)
			{
				e.printStackTrace();
			}
            
            raiseEvent("on_"+resource.getType()+"_added","INFRASTRUCTURE",this,resource,resource);

            if ( monitors.get(resource.getType()) == null )
            	{
            		Monitor monitor = Monitor.getMonitor(resource.getType(), this);
            		monitor.setTenantId(tenant_id);
            		monitor.setNFFGName(nffg_name);
            		monitor.setConfigurationService(configurationService);
            		if (monitor != null )
            		{
            			monitors.put(resource.getType(),monitor);
            		}
            	}
            monitors.get(resource.getType()).addResource(resource);
            resource.isConfigured();
        }
	}
	
	
	public void raiseEvent(String event_name, String from_name, Object fromobj, InfrastructureResource on_resource, Object args)
	{
		if (handlers.get(event_name) != null)
		{
			for(EventHandler handler : handlers.get(event_name))
			{
				handler.fire(event_name,from_name,fromobj,on_resource,args);
			}
		}
	}
	
	private List<FlowRule> buildFlowRules(NF_FGExtended nffg, InfrastructureResource resource, DeclarativeFlowRule dfr) throws Exception
	{
		String port_from = dfr.getMatchPortIn();
		PortUniqueID vnf_port_from = null;

		if ( port_from != null )
		{
			String[] arr = port_from.split(":");
			String port_from_vnf = null;
			String port_from_label = null;
			String port_from_number = null;
			if (arr.length== 1)
			{
				if ( ! resource.isVNF() ) throw new Exception("Invalid flow rule for non-VNF resource");
				port_from_vnf = resource.getId();
				port_from_label = port_from;
				VNFExtended vnf = nffg.getVNF(port_from_vnf);
				vnf_port_from = vnf.getFirstFreeFullnamePortByLabel(nffg, port_from_label);
			}
			else if (arr.length == 2 )
			{
				port_from_vnf = arr[0];
				port_from_label = arr[1];
				VNFExtended vnf = nffg.getVNF(port_from_vnf);
				vnf_port_from = vnf.getFirstFreeFullnamePortByLabel(nffg, port_from_label);		
			}
			else if (arr.length == 3)
			{
				port_from_vnf = arr[0];
				port_from_label = arr[1];
				port_from_number = arr[3];
				vnf_port_from = new PortUniqueID("vnf:"+port_from_vnf + ":" + port_from_label + ":" + port_from_number);
			}
			else
			{
				throw new Exception("Invalid Resource FlowRule From Port "+ port_from);
			}
		}
		
		String port_to = dfr.getActionOutputToPort();
		PortUniqueID vnf_port_to = null;

		if (port_to != null)
		{
			String[] arr2 = port_to.split(":");
			String port_to_vnf =  null;
			String port_to_label = null;
			String port_to_number = null;
			if (arr2.length== 1)
			{
				if ( ! resource.isVNF() ) throw new Exception("Invalid flow rule for non-VNF resource");
	
				port_to_vnf = resource.getId();
				port_to_label = port_to;
				VNFExtended vnf = nffg.getVNF(port_to_vnf);
				vnf_port_to = vnf.getFirstFreeFullnamePortByLabel(nffg, port_to_label);
			}
			else if (arr2.length == 2 )
			{
				port_to_vnf = arr2[0];
				port_to_label = arr2[1];
				VNFExtended vnf = nffg.getVNF(port_to_vnf);
				vnf_port_to = vnf.getFirstFreeFullnamePortByLabel(nffg, port_to_label);
			}
			else if (arr2.length == 3)
			{
				port_to_vnf = arr2[0];
				port_to_label = arr2[1];
				port_to_number = arr2[3];
				vnf_port_to = new PortUniqueID("vnf:"+port_to_vnf + ":" + port_to_label + ":" + port_to_number);
			}
			else
			{
				throw new Exception("Invalid Resource FlowRule From Port "+ port_from);
			}
		}       

		if ( !nffg.existPort(vnf_port_from) )
		{
			throw new Exception("Port with id:"+vnf_port_from.getValue()+" is invalid");
		}
		if ( !nffg.existPort(vnf_port_to) )
		{
			throw new Exception("Port with id:"+vnf_port_to.getValue()+" is invalid");
		}

		Action actionFr1 = new Action();
		
		if ( vnf_port_to != null)
		{
			actionFr1.setOutputToPort(vnf_port_to.getValue());
		}
		List<Action> actionsFr1 = new ArrayList<Action>();
		actionsFr1.add(actionFr1);
		Match matchFr1 = new Match();
		
		if ( vnf_port_from != null )
		{
			matchFr1.setPortIn(vnf_port_from.getValue());
		}
		
		FlowRule fr1 = new FlowRule();
		fr1.setId(dfr.getId());
		fr1.setMatch(matchFr1);
		
		if ( dfr.getPriority() != null )
		{
			fr1.setPriority(dfr.getPriority());
		}
		else
		{
			fr1.setPriority(1);
		}
		
		fr1.setActions(actionsFr1);
		
		
		List<FlowRule> ret = new ArrayList<>();
		ret.add(fr1);
		
		if ( dfr.isBidirectional() )
		{
			Action actionFr2 = new Action();
			
			if ( vnf_port_from != null)
			{
				actionFr2.setOutputToPort(vnf_port_from.getValue());
			}
			List<Action> actionsFr2 = new ArrayList<Action>();
			actionsFr2.add(actionFr2);
			Match matchFr2 = new Match();
			
			if ( vnf_port_to != null )
			{
				matchFr2.setPortIn(vnf_port_to.getValue());
			}
			
			FlowRule fr2 = new FlowRule();
			fr2.setId(dfr.getId()+"_2");
			fr2.setMatch(matchFr2);
			
			if ( dfr.getPriority() != null )
			{
				fr2.setPriority(dfr.getPriority());
			}
			else
			{
				fr2.setPriority(2);
			}
			
			fr2.setActions(actionsFr2);
			
			
			ret.add(fr2);
			
		}
		return ret;
	}
	
	private VNFExtended buildVNF(InfrastructureResource resource)
	{
		NF_FGTemplateExtended template;
		try
		{
			template = datastore.getTemplate(resource.getType());
		}
		catch (DatastoreClientHTTPException | DatastoreClientNotAuthenticatedException
				| DatastoreClientTemplateNotFoundException | DatastoreClientAuthenticationException e1)
		{
			e1.printStackTrace();
			return null;
		}
		
		try
		{
			return template.buildVNF(resource.getId(), resource.getId());
		}
		catch (NotImplementedYetException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
