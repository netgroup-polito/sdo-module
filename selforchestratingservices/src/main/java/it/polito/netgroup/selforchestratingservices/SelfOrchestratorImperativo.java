package it.polito.netgroup.selforchestratingservices;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.datastoreclient.*;
import it.polito.netgroup.infrastructureOrchestrator.*;
import it.polito.netgroup.loadbalancer.LoadBalancer;
import it.polito.netgroup.loadbalancer.LoadBalancerBalanceAlreadyExistForHostException;
import it.polito.netgroup.loadbalancer.LoadBalancerDefaultRouteNotFoundException;
import it.polito.netgroup.loadbalancer.LoadBalancerHostNotFoundException;
import it.polito.netgroup.loadbalancer.LoadBalancerHostRouteNotFoundException;
import it.polito.netgroup.natmonitor.NatEventHandler;
import it.polito.netgroup.natmonitor.NatMonitor;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.nffg_template.json.*;

public class SelfOrchestratorImperativo
{
	private int counter = 1;
	private String nffg_id = "1";
	private String nffg_name = "test_nffg_nat";
	private String tenant_id = "2";
	private MacAddress nat_mac = new MacAddress("02:01:02:03:04:05");
	private IpAddressAndNetmask nat_ip;
	private IpNetwork lan_network;
	private String random_str;

	private DatastoreClient datastore;
	private String datastore_url = "http://127.0.0.1:8081";
	private String datastore_username = "admin";
	private String datastore_password = "admin";

	private InfrastructureOrchestratorUniversalNode orchestrator;
	private String controller_url = "http://127.0.0.1:8080";
	private String controller_username = "admin";
	private String controller_password = "admin";

	private ConfigurationOrchestratorFrog4 configurationService;
	private String configuration_url = "http://127.0.0.1:8082";
	private String configuration_username = "admin";
	private String configuration_password = "admin";

	private int timeout_ms = 240000;

	private NatMonitor nat_monitor;
	private LoadBalancer load_balancer;
	private NF_FGTemplateExtended template_nat;

	private static final Logger LOGGER = Logger.getGlobal();

	public SelfOrchestratorImperativo()
	{
		try
		{
			nat_ip = new IpAddressAndNetmask("192.168.10.1", "255.255.255.0");
			lan_network = new IpNetwork("192.168.10.0", "255.255.255.0");
			random_str = RandomString.generate(3);

			datastore = new DatastoreClient(datastore_username, datastore_password, datastore_url, timeout_ms);
			orchestrator = new InfrastructureOrchestratorUniversalNode(controller_username, controller_password, controller_url,
					timeout_ms);
			configurationService = new ConfigurationOrchestratorFrog4(configuration_username, configuration_password,
					configuration_url, timeout_ms);

			LOGGER.log(Level.INFO,"Removing old NFFG with id '"+nffg_id+"'");

			orchestrator.removeNFFG(nffg_id);
			
			LOGGER.log(Level.FINE,"Getting template from the datastore");

			template_nat = datastore.getTemplate("nat");
			NF_FGTemplateExtended template_switch = datastore.getTemplate("switch");
			// template_orch = datastore.getTemplate("serviceorch");
			NF_FGTemplateExtended template_dhcpserver = datastore.getTemplate("dhcpserver");
			NF_FGTemplateExtended template_dhcpserver_man = datastore.getTemplate("dhcpserverman");
			NF_FGTemplateExtended template_dhcpserver_wan = datastore.getTemplate("dhcpserverwan");

			LOGGER.log(Level.FINE,"Building the VNFs");

			VNFExtended nat_vnf = template_nat.buildVNF("NAT" + random_str, "NAT" + random_str);
			VNFExtended dhcpserver_vnf = template_dhcpserver.buildVNF("DHCPLAN", "DHCPLAN");
			VNFExtended dhcpserver_man_vnf = template_dhcpserver_man.buildVNF("DHCPMAN", "DHCPMAN");

			VNFExtended dhcpserver_wan_vnf = template_dhcpserver_wan.buildVNF("DHCPWAN", "DHCPWAN");

			VNFExtended switch_man_vnf = template_switch.buildVNF("SWITCH_MAN", "SWITCH_MAN");
			VNFExtended switch_lan_vnf = template_switch.buildVNF("SWITCH_LAN", "SWITCH_LAN");
			VNFExtended switch_wan_vnf = template_switch.buildVNF("SWITCH_WAN", "SWITCH_WAN");
			// orch_vnf =template_orch.buildVNF("SELFORCH","serviceorch");

			EndPoint management_endpoint = new EndPoint();
			management_endpoint.setId("MANAGEMENT_ENDPOINT");
			management_endpoint.setName("MANAGEMENT_ENDPOINT");
			management_endpoint.setType(EndPoint.Type.HOST_STACK);
			management_endpoint.setHostStack(new HostStack());
			management_endpoint.getHostStack().setConfiguration(HostStack.Configuration.STATIC);
			management_endpoint.getHostStack().setIPv4("192.168.40.1/24");

			EndPoint internet_endpoint = new EndPoint();
			internet_endpoint.setId("INTERNET_ENDPOINT");
			internet_endpoint.setName("INTERNET_ENDPOINT");
			internet_endpoint.setType(EndPoint.Type.HOST_STACK);
			internet_endpoint.setHostStack(new HostStack());
			internet_endpoint.getHostStack().setConfiguration(HostStack.Configuration.STATIC);
			internet_endpoint.getHostStack().setIPv4("192.168.11.1/24");

			LOGGER.log(Level.FINE,"Creating the NFFG");
			NF_FGExtended nffg = new NF_FGExtended(nffg_id, nffg_name);

			LOGGER.log(Level.FINE,"Adding VNF to NFFG");

			// nffg.addVNF(orch_vnf);
			nffg.addVNF(nat_vnf);
			nffg.addVNF(dhcpserver_vnf);
			nffg.addVNF(dhcpserver_man_vnf);
			nffg.addVNF(dhcpserver_wan_vnf);
			nffg.addVNF(switch_man_vnf);
			nffg.addVNF(switch_lan_vnf);
			nffg.addVNF(switch_wan_vnf);
			nffg.addEndPoint(management_endpoint);
			nffg.addEndPoint(internet_endpoint);

			LOGGER.log(Level.FINE,"Creating link between VNF");

			// Connect SWITCH_MAN to DHCP MAN
			nffg.link_ports("DHCP_SWITCH_MAN", "SWITCH_MAN_DHCP", dhcpserver_man_vnf.getFullnamePortByLabel("inout"),
					switch_man_vnf.getFirstFreeFullnamePortByLabel(nffg, "port"));

			// Connect SWITCH_WAN to DHCP WAN
			nffg.link_ports("DHCP_SWITCH_WAN", "SWITCH_WAN_DHCP", dhcpserver_wan_vnf.getFullnamePortByLabel("inout"),
					switch_wan_vnf.getFullnamePortByLabel(switch_wan_vnf.getFreePortLabel(nffg, "port")));

			// Connect SWITCH_LAN to DHCP
			nffg.link_ports("DHCP_SWITCH_LAN", "SWITCH_LAN_DHCP", dhcpserver_vnf.getFullnamePortByLabel("inout"),
					switch_lan_vnf.getFullnamePortByLabel(switch_lan_vnf.getFreePortLabel(nffg, "port")));

			// Connect Switch_WAN to NAT
			nffg.link_ports("NAT_SWITCH_WAN", "SWITCH_WAN_NAT", nat_vnf.getFullnamePortByLabel("WAN"),
					switch_wan_vnf.getFullnamePortByLabel(switch_wan_vnf.getFreePortLabel(nffg, "port")));
			// Connect Switch_WAN to INTERNET ENDPOINT
			nffg.link_ports("INTERNET_SWITCH_WAN", "SWITCH_WAN_INTERNET",
					new PortUniqueID("endpoint:INTERNET_ENDPOINT"),
					switch_wan_vnf.getFullnamePortByLabel(switch_wan_vnf.getFreePortLabel(nffg, "port")));
			// Connect Endpoint management to Switch_MAN
			nffg.link_ports("SWITCH_MAN_ENDPOINT", "ENDPOINT_SWITCH_MAN",
					switch_man_vnf.getFullnamePortByLabel(switch_man_vnf.getFreePortLabel(nffg, "port")),
					new PortUniqueID("endpoint:MANAGEMENT_ENDPOINT"));
			// Connect management port of NAT to Switch_MAN
			nffg.link_ports("SWITCH_MAN_NAT", "NAT_SWITCH_MAN",
					switch_man_vnf.getFullnamePortByLabel(switch_man_vnf.getFreePortLabel(nffg, "port")),
					nat_vnf.getFullnamePortByLabel("management"));

			// Connect management port of SELFORCH to Switch_MAN
			// linkPorts(deploy_nffg,"SWITCH_MAN_SELFORCH","SELFORCH_SWITCH_MAN",SWITCH_MAN_vnf.getFullnamePortByLabel(SWITCH_MAN_vnf.getFreePort(deploy_nffg,"port")),orch_vnf.getFullnamePortByLabel("inout"))

			LOGGER.log(Level.FINE,"Creating LoadBalancer L2");

			load_balancer = new LoadBalancer(nat_mac, switch_lan_vnf.getFullnamePortByLabel("port2"));

			load_balancer.set_default(nffg, nat_vnf.getFullnamePortByLabel("User"));
			LOGGER.log(Level.INFO,"Sending initial NFFG");

			orchestrator.addNFFG(nffg);

			NatConfiguration nat_cfg = new NatConfiguration(tenant_id, nffg_id, nat_vnf);

			LOGGER.log(Level.FINE,"Configuring the nat VNF");

			nat_cfg.setIP(new InterfaceLabel("User"), nat_ip, nat_mac);

			LOGGER.log(Level.INFO,"Trying to put the configuration for the VNF '"+nat_vnf.getId()+"'");

			configurationService.waitUntilStarted(nat_cfg);
			configurationService.setConfiguration(nat_cfg);

			LOGGER.log(Level.FINE,"Configuring the NatMonitor");

			nat_monitor = new NatMonitor(configurationService, new NatEventHandler()
			{


				@Override
				public boolean on_host_new(VnfForConfigurationInterface nat,Host host)
				{
					return on_host_newA(host, null);
				}

				@Override
				public boolean on_host_left(VnfForConfigurationInterface nat,Host host)
				{
					return on_host_leftA(host);
				}

				@Override
				public boolean on_nat_fault(VnfForConfigurationInterface nat)
				{
					return on_nat_faultA(nat);
				}
			}, 1000, 30000);

			// LOGGER.log(Level.FINE,"Adding the NAT %s in the NatMonitor" %
			// nat_cfg.vnf_id)

			nat_monitor.addNat(new VnfForConfiguration(tenant_id, nffg_id, nat_cfg));

			// LOGGER.log(Level.INFO,"Waiting for one host")

		} catch (InfrastructureOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DatastoreClientHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DatastoreClientNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DatastoreClientTemplateNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DatastoreClientAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (NotImplementedYetException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (JsonProcessingException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (PortNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorConfigurationNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateFlowRuleException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateVNFException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateEndPointException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InvalidInterfaceLabel e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (NoFreePortsException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean on_nat_faultA(VnfForConfigurationInterface nat)
	{
		try
		{
			// LOGGER.log(Level.INFO,"The NAT %s does not respond. Removing it from NFFG
			// and re-route traffic (nffg_name=%s)" % (nat["vnf_id"],
			// self.nffg_name))

			nat_monitor.removeNat(nat);
			// Re-route traffic now over nat to default route and add a new nat

			// LOGGER.log(Level.INFO,"Trying to get the NFFG with name '%s'" %
			// self.nffg_name)
			NF_FGExtended nffg = orchestrator.getNFFG(nffg_id);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Unable to get NFFG with name '%s' : '%s'" %
			// (self.nffg_name, ex))
			// exit(1);

			String nat_name = nat.getId();

			MacAddress mac;

			try
			{
				mac = load_balancer.get_mac_host_to_vnf(nffg, nat_name);
			} catch (LoadBalancerHostNotFoundException e)
			{
				// LOGGER.log(Level.INFO,"'%s' is the default NAT" % nat_name)

				nffg.removeVNF(nat.getId());

				String vnf_name = "NAT" + random_str + counter;
				counter++;

				String vnf_id = vnf_name;
				nat_name = vnf_name;

				VNFExtended nat_vnf = template_nat.buildVNF(vnf_id, vnf_name);

				nffg.addVNF(nat_vnf);

				load_balancer.set_default(nffg, nat_vnf.getFullnamePortByLabel("User"));

				// LOGGER.log(Level.FINE,"Trying to get VNF");

				VNFExtended switch_man_vnf = nffg.getVNF("SWITCH_MAN");
				VNFExtended switch_wan_vnf = nffg.getVNF("SWITCH_WAN");

				nffg.link_ports(vnf_id + "_SWITCH_WAN", "SWITCH_WAN_" + vnf_id, nat_vnf.getFullnamePortByLabel("WAN"),
						switch_wan_vnf.getFullnamePortByLabel(switch_wan_vnf.getFreePortLabel(nffg, "port")));

				nffg.link_ports(vnf_id + "_SWITCH_MAN", "SWITCH_MAN_" + vnf_id,
						nat_vnf.getFullnamePortByLabel("management"),
						switch_man_vnf.getFullnamePortByLabel(switch_man_vnf.getFreePortLabel(nffg, "port")));

				// LOGGER.log(Level.INFO,"Sending the modified NFFG")
				orchestrator.addNFFG(nffg);
				// except Exception as ex:
				// LOGGER.log(Level.SEVERE,"Error during the add of the NFFG with name %s"
				// % ex)
				// exit(1);

				NatConfiguration nat_cfg = new NatConfiguration(tenant_id, nffg_id, nat_vnf);

				// LOGGER.log(Level.FINE,"Configuring the nat VNF")

				nat_cfg.setIP(new InterfaceLabel("User"), nat_ip, nat_mac);

				// LOGGER.log(Level.INFO,"Trying to put the configuration for the VNF
				// '%s'" % nat_vnf.id)
				// try:
				configurationService.waitUntilStarted(nat_cfg);
				configurationService.setConfiguration(nat_cfg);
				// except Exception as ex:
				// LOGGER.log(Level.SEVERE,"Error during the put of the configuration for
				// '%s' : '%s'" % (nat_vnf.id, ex))
				// exit(1);

				// LOGGER.log(Level.FINE,"Adding the NAT %s in the NatMonitor" %
				// nat_cfg.vnf_id)

				nat_monitor.addNat(new VnfForConfiguration(tenant_id, nffg_id, nat_cfg));

				// LOGGER.log(Level.INFO,"Waiting for host")
				return true;
			}

			// LOGGER.log(Level.INFO,"Remove and add again host with Mac-Address
			// '%s'" % mac)

			Host host = new Host("X.Z.Y.K", mac);

			// LOGGER.log(Level.FINE,"Configuring the LoadBalancer")
			load_balancer.remove_host(nffg, mac);

			// LOGGER.log(Level.FINE,"Removing the VNF with name '%s'" % nat_name)
			nffg.removeVNF(nat.getId());

			return on_host_newA(host, nffg);
		}catch (JsonProcessingException e)
		{
			e.printStackTrace();
			System.exit(1);
		}catch (NoFreePortsException e)
		{
			e.printStackTrace();
			System.exit(1);
		}catch (ConfigurationOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (PortNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (NotImplementedYetException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (VNFNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorConfigurationNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateFlowRuleException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateVNFException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InvalidInterfaceLabel e)
		{
			e.printStackTrace(); System.exit(1);
		}
		return false;
	}

	public boolean on_host_newA(Host host, NF_FGExtended nffg)
	{
		try
		{
			// LOGGER.log(Level.INFO,"New host found '%s' -> '%s' (nffg_name=%s)" %
			// (host["ip"], host["mac-address"], self.nffg_name))

			if (nffg == null)
			{
				// LOGGER.log(Level.INFO,"Trying to get the NFFG with name '%s'" %
				// self.nffg_name)
				// try:
				nffg = orchestrator.getNFFG(nffg_id);
				// except Exception as ex:
				// LOGGER.log(Level.SEVERE,"Unable to get NFFG with name '%s' : '%s'" %
				// (self.nffg_name,ex))
				// exit(1);

				if (load_balancer.exist_balance(nffg, host.getMacAddress()))
				{
					// LOGGER.log(Level.SEVERE,"Already exist a load-balance rule for
					// '%s'" % host["mac-address"])
					return true;
				}
			}

			// LOGGER.log(Level.FINE,"Trying to get VNF")

			VNFExtended switch_man_vnf = nffg.getVNF("SWITCH_MAN");
			VNFExtended switch_wan_vnf = nffg.getVNF("SWITCH_WAN");

			// # BUG, l'ON non accetta vnf_name lunghi
			// # vnf_name="NAT_"+nat_wan['ip'].replace(".","_")

			String vnf_name = "NAT" + random_str + counter;
			counter++;

			String vnf_id = vnf_name;
			VNFExtended nat_vnf = template_nat.buildVNF(vnf_id, vnf_name);

			nffg.addVNF(nat_vnf);

			// LOGGER.log(Level.FINE,"Add link for the new VNF");

			nffg.link_ports(vnf_id + "_SWITCH_WAN", "SWITCH_WAN_" + vnf_id, nat_vnf.getFullnamePortByLabel("WAN"),
					switch_wan_vnf.getFullnamePortByLabel(switch_wan_vnf.getFreePortLabel(nffg, "port")));
			nffg.link_ports(vnf_id + "_SWITCH_MAN", "SWITCH_MAN_" + vnf_id,
					nat_vnf.getFullnamePortByLabel("management"),
					switch_man_vnf.getFullnamePortByLabel(switch_man_vnf.getFreePortLabel(nffg, "port")));

			// LOGGER.log(Level.FINE,"Configuring the NAT VNF '%s'" % nat_vnf.id)

			NatConfiguration nat_cfg = new NatConfiguration(tenant_id, nffg_id, nat_vnf);

			nat_cfg.setIP(new InterfaceLabel("User"), nat_ip, nat_mac);

			// LOGGER.log(Level.FINE,"Updating the LoadBalancer configuration")
			load_balancer.add_balance(nffg, host.getMacAddress(), load_balancer.get_default_port(nffg));

			load_balancer.set_default(nffg, nat_vnf.getFullnamePortByLabel("User"));

			// LOGGER.log(Level.INFO,"Sending updated NFFG")
			orchestrator.addNFFG(nffg);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Error during the add of the NFFG: '%s'" % ex)
			// exit(1);

			// LOGGER.log(Level.INFO,"Trying to put the configuration for the VNF '%s'" %
			// nat_vnf.id)

			// try:
			configurationService.waitUntilStarted(nat_cfg);
			configurationService.setConfiguration(nat_cfg);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Error during the put of the configuration for
			// '%s':'%s'" % (nat_vnf.id, ex))
			// exit(1);

			// LOGGER.log(Level.INFO,"Adding the NAT '%s' in the NatMonitor" %
			// nat_cfg.vnf_id)
			nat_monitor.addNat(new VnfForConfiguration(tenant_id, nffg_id, nat_cfg));

			// LOGGER.log(Level.INFO,"New host found '%s': '%s' (nffg_name='%s') :
			// MANAGED" % (host["ip"], host["mac-address"], self.nffg_name))

			// LOGGER.log(Level.INFO,"Waiting for one host")

			return true;
		} catch (IOException e)
		{
		} catch (InfrastructureOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (PortNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (VNFNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (NotImplementedYetException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorConfigurationNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateVNFException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (DuplicateFlowRuleException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (LoadBalancerBalanceAlreadyExistForHostException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (LoadBalancerDefaultRouteNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InvalidInterfaceLabel e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (NoFreePortsException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public Boolean on_host_leftA(Host host)
	{
		try
		{
			// LOGGER.log(Level.INFO,"The host %s (%s) is left " % (host["ip"],
			// host["mac-address"]))

			// LOGGER.log(Level.INFO,"Trying to get the NFFG with name '%s'" %
			// self.nffg_name)
			// try:
			NF_FGExtended nffg = orchestrator.getNFFG(nffg_id);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Unable to get NFFG with name '%s' : '%s'" %
			// (self.nffg_name,ex))
			// exit(1);

			// LOGGER.log(Level.FINE,"Configuring the LoadBalancer")
			PortUniqueID port;
			try
			{
				port = load_balancer.get_port_host(nffg, host.getMacAddress());
			}
			catch(LoadBalancerHostRouteNotFoundException ex)
			{
				LOGGER.log(Level.SEVERE, "No LoadBalanceing route found for "+host.getIp()+"/"+host.getMacAddress().getValue());
				return false;
			}
			load_balancer.remove_host(nffg, host.getMacAddress());

			PortUniqueID default_port = load_balancer.get_default_port(nffg);
			if (port == default_port)
			{
				// LOGGER.log(Level.INFO,"Unable to remove the VNF associated with port
				// '%s', is the last NAT VNF" % port)
				return true;
			}

			String nat_id = port.getVnfId();

			Nat nat = (Nat) nffg.getVNF(nat_id);
			// LOGGER.log(Level.FINE,"Removing the VNF with name '%s'" % nat_name)
			nffg.removeVNF(nat);

			// LOGGER.log(Level.INFO,"Sending updated NFFG")
			// try:
			orchestrator.addNFFG(nffg);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Error during the add of the NFFG: '%s'" % ex)
			// exit(1);

			// LOGGER.log(Level.INFO,"Trying to remove the configuration for the VNF
			// '%s'" % nat_name)

			// try:
			configurationService.removeConfiguration(tenant_id, nffg_id, nat_id);
			// except Exception as ex:
			// LOGGER.log(Level.SEVERE,"Error during the remove of the configuration for
			// '%s' : '%s'" % (nat_name, ex))
			// exit(1);

			// LOGGER.log(Level.FINE,"Removing from NAT monitoring the VNF '%s'" %
			// nat_name);
			nat_monitor.removeNat(new VnfForConfiguration(tenant_id, nffg_id, nat));

			// LOGGER.log(Level.INFO,"The host '%s' ('%s') is left: MANAGED" %
			// (host["ip"], host["mac-address"]))

			// LOGGER.log(Level.INFO,"Waiting for one host")
			return true;
		} catch (IOException e)
		{

		} catch (InfrastructureOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (InfrastructureOrchestratorNF_FGNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (VNFNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorHTTPException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorAuthenticationException e)
		{
			e.printStackTrace(); System.exit(1);
		} catch (ConfigurationOrchestratorNotAuthenticatedException e)
		{
			e.printStackTrace(); System.exit(1);
		}catch (LoadBalancerDefaultRouteNotFoundException e)
		{
			e.printStackTrace(); System.exit(1);
		}
		return false;
	}

	public void run()
	{
		LOGGER.log(Level.FINE,"Starting the NatMonitor on "+ lan_network.getValue());
		try
		{
			LOGGER.log(Level.FINE,"Running...");
			nat_monitor.run(lan_network);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}