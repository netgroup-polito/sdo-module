package it.polito.netgroup.selforchestratingservices.declarative_new;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.datastoreclient.DatastoreClient;
import it.polito.netgroup.datastoreclient.DatastoreClientAuthenticationException;
import it.polito.netgroup.datastoreclient.DatastoreClientHTTPException;
import it.polito.netgroup.datastoreclient.DatastoreClientNotAuthenticatedException;
import it.polito.netgroup.datastoreclient.DatastoreClientTemplateNotFoundException;
import it.polito.netgroup.infrastructureOrchestrator.*;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.nffg_template.json.NF_FGTemplateExtended;
import it.polito.netgroup.nffg_template.json.NotImplementedYetException;
import it.polito.netgroup.selforchestratingservices.RandomString;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.AbstractResource;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
// import it.polito.netgroup.selforchestratingservices.declarative.monitors.Monitor;

public class MyInfrastructure implements Infrastructure
{
	List<InfrastructureVNF> ivnfs;
	List<Resource> resources;

	String nffg_name;
	String tenant_id;
	DatastoreClient datastore;
	ConfigurationOrchestratorFrog4 configurationService;
	InfrastructureOrchestrator orchestrator;
	Framework framework;
	String random_key;
	String resourceManager_url;
	//Variables variables;
	//InfrastructureEventHandler infrastructureEventHandler;

	private static final Logger LOGGER = Logger.getGlobal();

	public MyInfrastructure(InfrastructureOrchestrator orchestrator,
	                        DatastoreClient datastore,
	                        ConfigurationOrchestratorFrog4 configuration_service,
	                        String resourceManager_url,
	                        String nffg_name,
	                        String tenant_id)
	{
		this.resourceManager_url = resourceManager_url;
		//this.infrastructureEventHandler = null;
		//this.variables = null;
		this.framework = null;
		this.nffg_name = nffg_name;
		this.tenant_id = tenant_id;
		this.configurationService = configuration_service;
		this.orchestrator = orchestrator;
		this.datastore = datastore;

		ivnfs = new ArrayList<>();
		random_key = RandomString.generate(10);

		this.resources = new ArrayList<>();

		String randomstring = RandomString.generate(3);

        //resources.add(new NatInfrastructureVNF(this,"NATA"+randomstring));
        //resources.add(new NatInfrastructureVNF(this,"NATB"+randomstring));
        //resources.add(new NatInfrastructureVNF(this,"NATC"+randomstring));
        //resources.add(new NatInfrastructureVNF(this,"NATD"+randomstring));
        //resources.add(new NatInfrastructureVNF(this,"NATE"+randomstring));
/*
		resources.add(new CPUResource("CPU1"));
		resources.add(new CPUResource("CPU2"));
		resources.add(new CPUResource("CPU3"));
		resources.add(new CPUResource("CPU4"));
		resources.add(new CPUResource("CPU5"));
		resources.add(new CPUResource("CPU6"));
		resources.add(new CPUResource("CPU7"));
*/
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
		for(Resource resource : resources)
		{
			resource.unsetUsed();
		}
	}

	@Override
	public ConfigurationSDN getConfiguration(InfrastructureVNF resource) {
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
	public void setFramework(Framework _framework) {
		framework = _framework;
	}

	@Override
	public void useResource(Resource usedResource, InfrastructureVNF ivnf) {
		for(Resource resource : resources)
		{
			if ( usedResource.getId().equals(resource.getId()))
			{
				resource.setUsed(ivnf.getId());
			}
		}
	}

	@Override
	public void useInfrastructureVNF(Implementation implementation, ConfigurationSDN configuration, List<Resource> resourcesUsed, ElementaryService elementaryService)
	{
		InfrastructureVNF infrastructureVNF = null;
		for(InfrastructureVNF ivnf : ivnfs)
		{
			if ( ivnf.getUsedBy().equals(implementation))
			{
				ivnf.setModified();

				infrastructureVNF = ivnf;
				break;
			}
		}

		if ( infrastructureVNF == null ) {
			//Create infrastructureVNF
			infrastructureVNF = implementation.getInfrastructureVNF(this);
		}

		infrastructureVNF.setUsed(implementation);
		infrastructureVNF.applyTemplate(implementation.getTemplate(infrastructureVNF.getClass()));
		infrastructureVNF.setConfiguration(configuration);

		for(Resource r : resourcesUsed)
		{
			r.setUsed(infrastructureVNF.getId());
		}
		for(Resource r: resources)
		{
			if ( r.isUsed() && r.getUsedBy().equals(infrastructureVNF.getId()) && !resourcesUsed.contains(r))
			{
				r.unsetUsed();
			}
		}

		ivnfs.add(infrastructureVNF);
	}

	@Override
	public Boolean checkEvents() {
		String json = http_client(resourceManager_url+"/events.php");

		ObjectMapper mapper = new ObjectMapper();
		InfrastructureEvent event = null;
		try {
			event = mapper.readValue(json, InfrastructureEvent.class);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(json);
			throw new InvalidParameterException();
		}
		Boolean ret=false;

		if ( event.eventType == InfrastructureEvent.EventType.NONE)
		{

		}
		else if ( event.eventType == InfrastructureEvent.EventType.UPDATE_RESOURCES)
		{
			//updateResourceAvailable();
			ret=true;
		}

		return ret;

	}

	@Override
	public void updateImplementationStatus(Implementation implementation) {
		try {
			InfrastructureVNF ivnf = implementation.getInfrastructureVNF(this);

			VnfForConfiguration vnfC = new VnfForConfiguration(tenant_id,nffg_name,ivnf.getId(),ivnf.getFunctionalCapability());

			configurationService.waitUntilStarted(vnfC);
			ConfigurationSDN configuration = configurationService.getConfiguration(vnfC);

			implementation.updateActualConfiguration(configuration);

		} catch (InterruptedException | ConfigurationorchestratorUnsupportedFunctionalCapabilityException | ConfigurationOrchestratorHTTPException | ConfigurationOrchestratorAuthenticationException | ConfigurationOrchestratorConfigurationNotFoundException | ConfigurationOrchestratorNotAuthenticatedException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}


	private String http_client(String url)
	{
		Integer timeout_ms = 10000;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.setConfig(config);

		HttpResponse response;
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.warning("HTTP IO Error : " + e.getMessage());
			throw new InvalidParameterException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.fine("HTTP STATUS:"+http_status);

		if (http_status == 200)
		{
			try {
				return HttpResponseReader.getContent(response);
			}
			catch(IOException e)
			{
				throw new InvalidParameterException(e.getMessage());
			}
		}
		throw new InvalidParameterException("Invalid HTTP Status : " + http_status);
	}

	private String http_client(String url, String upload )
	{
		Integer timeout_ms = 60000;

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout_ms)
				.setConnectTimeout(timeout_ms).setSocketTimeout(timeout_ms).build();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		request.setConfig(config);

		request.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
		request.addHeader("cache-control", "no-cache");


		StringEntity entity = new StringEntity(upload, "UTF-8");
		request.setEntity(entity);
		HttpResponse response;
		try
		{
			response = client.execute(request);
		} catch (IOException e)
		{
			LOGGER.warning("HTTP IO Error : " + e.getMessage());
			throw new InvalidParameterException("HTTP IO Error : " + e.getMessage());
		}

		int http_status = response.getStatusLine().getStatusCode();
		LOGGER.fine("HTTP STATUS:"+http_status);

		if (http_status == 200)
		{
			try {
				return HttpResponseReader.getContent(response);
			}
			catch(IOException e)
			{
				throw new InvalidParameterException(e.getMessage());
			}
		}
		throw new InvalidParameterException("Invalid HTTP Status : " + http_status);
	}


	//TODO
	@Override
	public void commit() throws Exception
	{
		List<InfrastructureVNF> listAdded = new ArrayList<>();
		List<InfrastructureVNF> listRemoved = new ArrayList<>();

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

		for(InfrastructureVNF ivnf : ivnfs)
        {
        	LOGGER.info("Checking VNF '"+ivnf.getId()+"'");

    		if (ivnf.isUsed())
    		{
			    LOGGER.info("VNF '"+ivnf.getId()+"' is used");

    			if ( !ivnf.isInstantiated())
    			{
				    LOGGER.info("VNF '"+ivnf.getId()+"' is not instantiated");

				    VNFExtended vnf = buildVNF(ivnf);
        			
        			try
					{
						LOGGER.info("Adding VNF '"+vnf.getId()+"' to NFFG");
						nffg.addVNF(vnf);
					}
        			catch (DuplicateVNFException e)
					{
						LOGGER.warning("Unable to add VNF '"+vnf.getId()+"' to NFFG: "+e.getMessage());
						throw e;
					}

					LOGGER.info("Reading flowrule for VNF '"+vnf.getId()+"'");
				    for(DeclarativeFlowRule dfr : ivnf.getFlowRules())
				    {
					    LOGGER.info("DeclarativeFlowRule '"+dfr.getId()+"' VNF '"+vnf.getId()+"'");

					    try
					    {
						    for(FlowRule flowRule : buildFlowRules(nffg,ivnf,dfr))
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
						    LOGGER.info("Unable to build FlowRule from DeclarativeFlowRule '"+dfr.getId()+"' VNF '"+vnf.getId()+"':"+e.getMessage());
						    throw e;
					    }
				    }


				    LOGGER.info("Adding VNF '"+vnf.getId()+"' inside the configuration Queue");
				    listAdded.add(ivnf);
		    	}
	        }
	        else if ( !ivnf.isUsed() && ivnf.isInstantiated() )
		    {
			    LOGGER.info("Removing VNF '"+ivnf.getId()+"' from NFFG");

			    framework.getSelfOrchestrator().getInfrastructureEventhandler().on_vnf_removing(ivnf);

				nffg.removeVNF(ivnf.getId());

			    listRemoved.add(ivnf);
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
     
        for (InfrastructureVNF ivnf : ivnfs) {
	        if (ivnf.updateConfigurationNeeded()) {
		        ConfigurationSDN config = ivnf.getConfiguration();
		        config.setTenantId(tenant_id);
		        config.setNffgid(nffg_name);
		        config.setVnfId(ivnf.getId());
		        config.setFunctionalCapability(ivnf.getFunctionalCapability());

		        LOGGER.info("Updating configuration of " + tenant_id + "/" + nffg_name + "/" + ivnf.getId());
		        try {
			        configurationService.waitUntilStarted(config);
			        configurationService.setConfiguration(config);

		        } catch (InterruptedException | ConfigurationOrchestratorHTTPException | ConfigurationOrchestratorAuthenticationException | ConfigurationOrchestratorConfigurationNotFoundException | ConfigurationOrchestratorNotAuthenticatedException | JsonProcessingException e) {
			        e.printStackTrace();
			        throw e;
		        }

		        while (true) {
			        try {
				        ivnf.getTemplate().init(framework.getSelfOrchestrator().getVariables(), ivnf, framework);
				        break;
			        } catch (Exception ex) {
				        ex.printStackTrace();
				        System.out.println("Unable set the configuration for the InfrastructureVNF with id '" + ivnf.getId() + "', try again");
				        try {
					        Thread.sleep(1000);
				        } catch (Exception ex2) {
					        ex2.printStackTrace();
					        break;
				        }
			        }

		        }
		        ivnf.resetUpdateConfiguration();
	        }
        }

		for (InfrastructureVNF ivnf : listAdded)
		{
            ivnf.setInstantiated();
	        framework.getSelfOrchestrator().getInfrastructureEventhandler().on_vnf_added(ivnf);
        }

		for(InfrastructureVNF ivnf : listRemoved)
		{
			framework.getSelfOrchestrator().getInfrastructureEventhandler().on_vnf_removed(ivnf);
			ivnf.unsetInstantiated();
		}

		uploadResourceUsed();
	}

	private void uploadResourceUsed() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			String upload = mapper.writeValueAsString(resources);
			String json = http_client(resourceManager_url+"/update_resources.php",upload);
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException();
		}
	}

	@Override
	public void updateResourceAvailable() {
		String json = http_client(resourceManager_url+"/resources_available.php?key="+random_key);

		ObjectMapper mapper = new ObjectMapper();
		try {
			resources = mapper.readValue(json, new TypeReference<List<AbstractResource>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException();
		}
	}

	@Override
	public List<InfrastructureVNF> getInfrastructureVNF() {
		List<InfrastructureVNF> ret = new ArrayList<>();

		ret.addAll(ivnfs);

		return ret;
	}

	@Override
	public List<Resource> getResources() {
		List<Resource> ret = new ArrayList<>();

		ret.addAll(resources);

		return ret;
	}


	private PortUniqueID getPortUniqueId(String port_from, InfrastructureVNF ivnf , NF_FGExtended nffg) throws Exception {
		PortUniqueID vnf_port_from = null;

		if ( port_from != null )
		{
			String[] arr = port_from.split(":");
			String port_from_vnf = null;
			String port_from_label = null;
			String port_from_number = null;
			if (arr.length== 1)
			{
				port_from_vnf = ivnf.getId();
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

	private List<FlowRule> buildFlowRules(NF_FGExtended nffg, InfrastructureVNF ivnf, DeclarativeFlowRule dfr) throws Exception
	{
		PortUniqueID vnf_port_from = getPortUniqueId(dfr.getMatchPortIn(),ivnf,nffg);
		PortUniqueID vnf_port_to = getPortUniqueId(dfr.getActionOutputToPort(),ivnf,nffg);

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
		fr1.setId(ivnf.getId()+"_"+dfr.getId()+"_1");
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
			fr2.setId(ivnf.getId()+"_"+dfr.getId()+"_2");
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
	
	private VNFExtended buildVNF(InfrastructureVNF vnf)
	{
		NF_FGTemplateExtended template;
		try
		{
			template = datastore.getTemplate(vnf.getDatastoreTemplate());
		}
		catch (DatastoreClientHTTPException | DatastoreClientNotAuthenticatedException
				| DatastoreClientTemplateNotFoundException | DatastoreClientAuthenticationException e1)
		{
			e1.printStackTrace();
			return null;
		}
		
		try
		{
			return template.buildVNF(vnf.getId(), vnf.getId());
		}
		catch (NotImplementedYetException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
