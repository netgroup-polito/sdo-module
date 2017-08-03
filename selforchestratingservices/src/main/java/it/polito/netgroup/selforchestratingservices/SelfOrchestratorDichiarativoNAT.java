package it.polito.netgroup.selforchestratingservices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.configurationorchestrator.InvalidInterfaceLabel;
import it.polito.netgroup.configurationorchestrator.NatConfiguration;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.InterfaceLabel;
import it.polito.netgroup.nffg.json.IpAddressAndNetmask;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRule;
import it.polito.netgroup.selforchestratingservices.declarative.DeclarativeFlowRuleImpl;
import it.polito.netgroup.selforchestratingservices.declarative.ElementaryService;
import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Infrastructure;
import it.polito.netgroup.selforchestratingservices.declarative.InfrastructureResource;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarNameException;
import it.polito.netgroup.selforchestratingservices.declarative.InvalidVarTypeException;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementation;
import it.polito.netgroup.selforchestratingservices.declarative.RealizedImplementationImpl;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceRequirement;
import it.polito.netgroup.selforchestratingservices.declarative.SelfOrchestratorDichiarativoAbstract;
import it.polito.netgroup.selforchestratingservices.declarative.Variables;
import it.polito.netgroup.selforchestratingservices.declarative.VariablesImplementation;

public class SelfOrchestratorDichiarativoNAT extends SelfOrchestratorDichiarativoAbstract
{

	
	public SelfOrchestratorDichiarativoNAT(Infrastructure _infrastructure)
	{
		Variables variables = new VariablesImplementation();
		variables.setVar("hosts", new ArrayList<Host>());
		List<ElementaryService> elementaryServices = new ArrayList<ElementaryService>();
        
        final List<ResourceRequirement> resources = new ArrayList<ResourceRequirement>();

        resources.add(new ResourceRequirement()
		{
        		Variables var= variables;
			
			@Override
			public String getType()
			{
				return "NAT";
			}
			
			@Override
			public List<DeclarativeFlowRule> getDefaultFlowRules()
			{
		        List<DeclarativeFlowRule> default_flowrules = new ArrayList<DeclarativeFlowRule>();
		        
		        DeclarativeFlowRule dfr1 = new DeclarativeFlowRuleImpl();
		        dfr1.linkPorts("linkWAN","WAN","SWITCH_WAN:port");
		        DeclarativeFlowRule dfr2 = new DeclarativeFlowRuleImpl();
		        dfr2.linkPorts("linkMAN","management","SWITCH_MAN:port");		        
		        default_flowrules.add(dfr1);
		        default_flowrules.add(dfr2);
		        
		        return default_flowrules;
			}
			
			@Override
			public ConfigurationSDN getDefaultConfiguration()
			{
				NatConfiguration default_cfg = new NatConfiguration(null, null, null,null);
		        try
				{
					default_cfg.setIP(new InterfaceLabel("User"), new IpAddressAndNetmask("192.168.10.1", "255.255.255.0") , new MacAddress("02:01:02:03:04:05"));
				} catch (InvalidInterfaceLabel e)
				{
					e.printStackTrace();
					return null;
				}
		        return default_cfg;
			}
			
			@Override
			public boolean checkConstraint(List<Resource> r)
			{
				r = r.stream().filter(x -> x.getType() == this.getType()).collect(Collectors.toCollection(ArrayList::new));

				try
				{
					return ( r.size() > 1 && r.size() < variables.getVar("hosts",List.class).size()+1 );
				} catch (InvalidVarNameException e)
				{
					e.printStackTrace();
					return false;
				} catch (InvalidVarTypeException e)
				{
					e.printStackTrace();
					return false;
				}
			}

			@Override
			public boolean isVNF()
			{
				return true;
			}
		});
        		
        		
        final List<Implementation> implementations = new ArrayList<Implementation>();

        implementations.add(new Implementation()
		{
	    		Variables var = variables;

			@Override
			public void show()
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setResoucesUsed(List<InfrastructureResource> r)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<InfrastructureResource> getResourcesUsed()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Resource> getResources()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Float getQoS(List<InfrastructureResource> resources)
			{
				List<Host> hosts;
				try
				{
					hosts = var.getVar("hosts", List.class);
				} catch (InvalidVarNameException | InvalidVarTypeException e)
				{
					e.printStackTrace();
					return Float.NaN;
				}
				
				if ( hosts.size() == 0 ) return (float) 0.0;
				
				int i=0;
				for (Resource resource : resources)
				{
					if ( resource.isUsed() && resource.getType().equals("NAT"))
					{
						i++;
					}
				}
				return (float) (i / resources.size());
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Float getQoS()
			{
				List<Host> hosts;
				try
				{
					hosts = var.getVar("hosts", List.class);
				} catch (InvalidVarNameException | InvalidVarTypeException e)
				{
					e.printStackTrace();
					return Float.NaN;
				}
				List<Resource> resources;
				try
				{
					resources = var.getVar("resources", List.class);
				} catch (InvalidVarNameException | InvalidVarTypeException e)
				{
					e.printStackTrace();
					return Float.NaN;
				}
				
				if ( hosts.size() == 0 ) return (float) 0;
				
				int i=0;
				for (Resource resource : resources)
				{
					if ( resource.isUsed() && resource.getType().equals("NAT"))
					{
						i++;
					}
				}
				return (float) (i / resources.size());
			}
			
			@Override
			public String getName()
			{
				return "ImplementationNat1";
			}

			@Override
			public List<ResourceRequirement> getResourceRequirement()
			{
				return resources;
			}

			@Override
			public RealizedImplementation getRealizedImplementation(List<InfrastructureResource> resources_used)
			{
				return new RealizedImplementationImpl(this,resources_used);
			}
			
		});
       
        elementaryServices.add(new ElementaryService()
		{	
			@Override
			public void show()
			{
				// TODO Auto-generated method stub
			}
			
			@Override
			public String getName()
			{
				return "nat";
			}
			
			@Override
			public List<Implementation> getImplementations()
			{
				return implementations;
			}
			
			@Override
			public void commit()
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setRelizedImplementation(RealizedImplementation realizedImplementation)
			{
				// TODO Auto-generated method stub
				
			}
		});
        
        final List<ResourceRequirement> resources2 = new ArrayList<>();

        resources2.add(new ResourceRequirement()
		{
        		Variables var = variables;
			
			@Override
			public String getType()
			{
				return "LOADBALANCER";
			}
			
			@Override
			public List<DeclarativeFlowRule> getDefaultFlowRules()
			{
				return new ArrayList<>();
			}
			
			@Override
			public ConfigurationSDN getDefaultConfiguration()
			{
				return null;
			}
			
			@Override
			public boolean checkConstraint(List<Resource> r)
			{
				r = r.stream().filter(x -> x.getType() == this.getType()).collect(Collectors.toCollection(ArrayList::new));

				return r.size() == 1;
			}

			@Override
			public boolean isVNF()
			{
				return false;
			}
		});
        
        final List<Implementation> implementations2 = new ArrayList<>();
        
        implementations2.add(new 	Implementation()
		{
    			Variables var = variables;

			@Override
			public void show()
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setResoucesUsed(List<InfrastructureResource> r)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<InfrastructureResource> getResourcesUsed()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Resource> getResources()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<ResourceRequirement> getResourceRequirement()
			{
				return resources2;
			}
			
			@Override
			public RealizedImplementation getRealizedImplementation(List<InfrastructureResource> resources_used)
			{
				return new RealizedImplementationImpl(this,resources_used);
			}
			
			@Override
			public Float getQoS(List<InfrastructureResource> r)
			{
				return (float) 1;
			}
			
			@Override
			public Float getQoS()
			{
				return (float) 1;
			}
			
			@Override
			public String getName()
			{
				return "LB";
			}
		});

        elementaryServices.add( new ElementaryService()
		{
			
			@Override
			public void show()
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getName()
			{
				return "loadbalancer";
			}
			
			@Override
			public List<Implementation> getImplementations()
			{
				return implementations2;
			}
			
			@Override
			public void commit()
			{
				//TODO
			}

			@Override
			public void setRelizedImplementation(RealizedImplementation realizedImplementation)
			{
				// TODO Auto-generated method stub
				
			}
		});
        
        
        init("NAT_LB",elementaryServices,_infrastructure);
    }
	
	
	@Override
	public void commit()
	{
		//TODO
		super.commit();
	}
}