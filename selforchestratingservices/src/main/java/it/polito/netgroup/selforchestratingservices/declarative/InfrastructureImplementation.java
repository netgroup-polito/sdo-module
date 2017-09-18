package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.configurationorchestrator.*;
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
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.nffg_template.json.NF_FGTemplateExtended;
import it.polito.netgroup.nffg_template.json.NotImplementedYetException;
import it.polito.netgroup.selforchestratingservices.RandomString;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.NatInfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative_new.Framework;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureEventHandler;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureResource;
// import it.polito.netgroup.selforchestratingservices.declarative.monitors.Monitor;

public class InfrastructureImplementation implements Infrastructure
{
	List<InfrastructureResource> resources;

	String nffg_name;
	String tenant_id;
	DatastoreClient datastore;
	ConfigurationOrchestratorFrog4 configurationService;
	InfrastructureOrchestrator orchestrator;
	Framework framework;
	Variables variables;
	InfrastructureEventHandler infrastructureEventHandler;

	private static final Logger LOGGER = Logger.getGlobal();

	public InfrastructureImplementation(Framework framework,
	                                    Variables variables,
	                                    InfrastructureEventHandler eventHandler,
	                                    InfrastructureOrchestrator orchestrator,
	                                    DatastoreClient datastore,
	                                    ConfigurationOrchestratorFrog4 configuration_service,
	                                    String nffg_name,
	                                    String tenant_id)
	{
		this.infrastructureEventHandler = eventHandler;
		this.variables = variables;
		this.framework = framework;
		this.nffg_name = nffg_name;
		this.tenant_id = tenant_id;
		this.configurationService = configuration_service;
		this.orchestrator = orchestrator;
		this.datastore = datastore;
		
		this.resources = new ArrayList<>();

		String randomstring = RandomString.generate(3);

        resources.add(new NatInfrastructureResource(this,"NATA"+randomstring));
        resources.add(new NatInfrastructureResource(this,"NATB"+randomstring));
        //resources.add(new NatInfrastructureResource(this,"NATC"+randomstring));
        //resources.add(new NatInfrastructureResource(this,"NATD"+randomstring));
        //resources.add(new NatInfrastructureResource(this,"NATE"+randomstring));

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
	public void freeAllResources() {
		for(InfrastructureResource resource : resources)
		{
			resource.unsetUsed();
		}
	}

	@Override
	public ConfigurationSDN getConfiguration(InfrastructureResource resource) {
		VnfForConfiguration vfc = new VnfForConfiguration(tenant_id, nffg_name, resource.getId() , resource.getFunctionalCapability() );
		try {
			return configurationService.getConfiguration(vfc);
		} catch (ConfigurationOrchestratorHTTPException |
				ConfigurationOrchestratorConfigurationNotFoundException |
				ConfigurationOrchestratorNotAuthenticatedException |
				ConfigurationOrchestratorAuthenticationException |
				ConfigurationorchestratorUnsupportedFunctionalCapabilityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void addFlowRule(FlowRule flowRule) throws Exception
	{
		NF_FGExtended nffg;

		try
		{
			nffg = orchestrator.getNFFG(nffg_name);
		} catch (InfrastructureOrchestratorHTTPException | InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException
				| InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("Unable to read the graph"+e.getMessage());
		}

		nffg.addFlowRule(flowRule);

		try
		{
			orchestrator.addNFFG(nffg);
		}
		catch (JsonProcessingException | InfrastructureOrchestratorHTTPException
				| InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException e)
		{
			//e.printStackTrace();
			throw new Exception("Unable to update the graph"+e.getMessage());
		}

	}

	@Override
	public void removeFlowRuleStartingWith(String prefix) throws Exception {
		NF_FGExtended nffg;

		try
		{
			nffg = orchestrator.getNFFG(nffg_name);
		} catch (InfrastructureOrchestratorHTTPException | InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException
				| InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("Unable to read the graph"+e.getMessage());
		}

		nffg.removeFlowRulesStartingWith(prefix);

		try
		{
			orchestrator.addNFFG(nffg);
		}
		catch (JsonProcessingException | InfrastructureOrchestratorHTTPException
				| InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException e)
		{
			//e.printStackTrace();
			throw new Exception("Unable to update the graph"+e.getMessage());
		}
	}

	@Override
	public void useResource(Resource usedResource, ElementaryService elementaryService) {
		for(InfrastructureResource resource : resources)
		{
			if ( usedResource.getId().equals(resource.getId()))
			{
				resource.setUsed(elementaryService);
				resource.applyTemplate(elementaryService.getCurrentImplementation().getTemplate(resource.getClass()));
			}
		}
	}

	@Override
	public void commit() throws Exception
	{
		List<InfrastructureResource> listAdded = new ArrayList<>();
		List<InfrastructureResource> listRemoved = new ArrayList<>();

		LOGGER.info("Commit changes to infrastructure");

        NF_FGExtended nffg;
		try
		{
			LOGGER.info("Getting current NFFG");
			nffg = orchestrator.getNFFG(nffg_name);
		} catch (InfrastructureOrchestratorHTTPException | InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException
				| InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			LOGGER.warning("Unable to get current NFFG: "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
         
        List<InfrastructureResource> configurationQueue = new ArrayList<>();


		for(InfrastructureResource resource : resources)
        {
        	LOGGER.info("Checking Resource '"+resource.getId()+"'");

    		if (resource.isUsed())
    		{
			    LOGGER.info("Resource '"+resource.getId()+"' is used");

    			if ( resource.isVNF() && !resource.isInstantiated())
    			{
				    LOGGER.info("Resource '"+resource.getId()+"' is not instantiated");

				    VNFExtended vnf = buildVNF(resource);
        			
        			try
					{
						LOGGER.info("Adding Resource '"+resource.getId()+"' to NFFG");
						nffg.addVNF(vnf);
					}
        			catch (DuplicateVNFException e)
					{
						LOGGER.warning("Unable to add Resource '"+resource.getId()+"' to NFFG: "+e.getMessage());
						throw e;
					}

					LOGGER.info("Reading flowrule for Resource '"+resource.getId()+"'");
				    for(DeclarativeFlowRule dfr : resource.getFlowRules())
				    {
					    LOGGER.info("DeclarativeFlowRule '"+dfr.getId()+"' Resource '"+resource.getId()+"'");

					    try
					    {
						    for(FlowRule flowRule : buildFlowRules(nffg,resource,dfr))
						    {
							    try
							    {
								    LOGGER.info("Adding FlowRule '"+flowRule.getId()+"'");
								    nffg.addFlowRule(flowRule);
							    }
							    catch (DuplicateFlowRuleException e)
							    {
								    LOGGER.info("Unable to add FlowRule '"+flowRule.getId()+"':"+e.getMessage());
								    throw e;
							    }
						    }
					    }
					    catch (Exception e)
					    {
						    LOGGER.info("Unable to build FlowRule from DeclarativeFlowRule '"+dfr.getId()+"' Resource '"+resource.getId()+"':"+e.getMessage());
						    throw e;
					    }
				    }

				    if ( resource.getConfiguration() != null )
				    {
					    LOGGER.info("Adding Resource '"+resource.getId()+"' inside the configuration Queue");
					    configurationQueue.add(resource);
				    }
				    listAdded.add(resource);
		    	}
	        }
	        else if ( !resource.isUsed() && resource.isInstantiated() )
		    {
			    LOGGER.info("Removing Resource '"+resource.getId()+"' from NFFG");

			    infrastructureEventHandler.on_resource_removing(resource);

			    if ( resource.isVNF() )
			    {
					nffg.removeVNF(resource.getId());
			    }

			    listRemoved.add(resource);
		    }
        }

        try
		{
			LOGGER.info("Updating the NFFG");
			orchestrator.addNFFG(nffg);
		}
        catch (JsonProcessingException | InfrastructureOrchestratorHTTPException
				| InfrastructureOrchestratorAuthenticationException
				| InfrastructureOrchestratorNotAuthenticatedException e)
		{
			LOGGER.info("Unable to update the NFFG: "+e.getMessage());
			throw e;
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
            catch (InterruptedException | ConfigurationOrchestratorHTTPException | ConfigurationOrchestratorAuthenticationException | ConfigurationOrchestratorConfigurationNotFoundException | ConfigurationOrchestratorNotAuthenticatedException | JsonProcessingException e)
			{
				e.printStackTrace();
				throw e;
			}

	        while (true) {
            	try {
		            resource.getTemplate().init(variables, resource, framework);
		            break;
	            }catch(Exception ex)
	            {
	            	ex.printStackTrace();
	            	System.out.println("Unable set the configuration for the resource with id '"+resource.getId()+"', try again");
	            	try {
			            Thread.sleep(1000);
		            }catch(Exception ex2)
		            {
		            	ex2.printStackTrace();
		            	break;
		            }
	            }

			}
            resource.setInstantiated();
        }

        for(InfrastructureResource resource : listAdded)
        {
	        infrastructureEventHandler.on_resource_added(resource);
	        resource.setInstantiated();
        }

		for(InfrastructureResource resource : listRemoved)
		{
			infrastructureEventHandler.on_resource_removed(resource);
			resource.unsetInstantiated();
		}
	}

	@Override
	public void updateResourceAvailable() {
		//TODO
	}

	@Override
	public List<Resource> getResources() {
		List<Resource> ret = new ArrayList<>();

		ret.addAll(resources);

		return ret;
	}


	private PortUniqueID getPortUniqueId(String port_from,InfrastructureResource resource , NF_FGExtended nffg) throws Exception {
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

		return vnf_port_from;
	}

	private List<FlowRule> buildFlowRules(NF_FGExtended nffg, InfrastructureResource resource, DeclarativeFlowRule dfr) throws Exception
	{
		PortUniqueID vnf_port_from = getPortUniqueId(dfr.getMatchPortIn(),resource,nffg);
		PortUniqueID vnf_port_to = getPortUniqueId(dfr.getActionOutputToPort(),resource,nffg);

		if ( vnf_port_from == null)
		{
			throw new Exception("'vnf_port_from' is null for "+dfr.getMatchPortIn());
		}
		if ( vnf_port_to == null)
		{
			throw new Exception("'vnf_port_to' is null for "+dfr.getActionOutputToPort());
		}

		if (!nffg.existPort(vnf_port_from) )
		{
			throw new Exception("Port with id:"+vnf_port_from.getValue()+" is invalid");
		}
		if (!nffg.existPort(vnf_port_to) )
		{
			throw new Exception("Port with id:"+vnf_port_to.getValue()+" is invalid");
		}

		Action actionFr1 = new Action();
		
		if ( vnf_port_to != null)
		{
			actionFr1.setOutputToPort(vnf_port_to.getValue());
		}
		List<Action> actionsFr1 = new ArrayList<>();
		actionsFr1.add(actionFr1);
		Match matchFr1 = new Match();
		
		if ( vnf_port_from != null )
		{
			matchFr1.setPortIn(vnf_port_from.getValue());
		}
		
		FlowRule fr1 = new FlowRule();
		fr1.setId(resource.getId()+"_"+dfr.getId()+"_1");
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
			List<Action> actionsFr2 = new ArrayList<>();
			actionsFr2.add(actionFr2);
			Match matchFr2 = new Match();
			
			if ( vnf_port_to != null )
			{
				matchFr2.setPortIn(vnf_port_to.getValue());
			}
			
			FlowRule fr2 = new FlowRule();
			fr2.setId(resource.getId()+"_"+dfr.getId()+"_2");
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
			template = datastore.getTemplate(resource.getDatastoreTemplate());
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
