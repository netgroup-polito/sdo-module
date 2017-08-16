package it.polito.netgroup.natmonitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestrator;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorAuthenticationException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorConfigurationNotFoundException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorHTTPException;
import it.polito.netgroup.configurationorchestrator.ConfigurationOrchestratorNotAuthenticatedException;
import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.configurationorchestrator.ConfigurationorchestratorUnsupportedFunctionalCapabilityException;
import it.polito.netgroup.configurationorchestrator.NatConfiguration;
import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.configurationorchestrator.VnfForConfigurationInterface;
import it.polito.netgroup.configurationorchestrator.json.nat.ArpEntry;
import it.polito.netgroup.configurationorchestrator.json.nat.NatSession;
import it.polito.netgroup.nffg.json.Host;
import it.polito.netgroup.nffg.json.IpNetwork;
import it.polito.netgroup.nffg.json.MacAddress;

public class NatMonitor
{

	private ConfigurationOrchestrator configurationService;
	private NatEventHandler natEventHandler;
	private int timeout_left_ms;
	private int polling_ms;
	private List<VnfForConfigurationInterface> nats;
	private Map<String,ActiveHostEntry> active_hosts;

	public NatMonitor(ConfigurationOrchestrator _configurationService, NatEventHandler _natEventHandler,
			int _polling_ms, int _timeout_left_ms)
	{
		configurationService = _configurationService;
		natEventHandler = _natEventHandler;
		polling_ms = _polling_ms;
		timeout_left_ms = _timeout_left_ms;
		nats = new ArrayList<>();
		active_hosts = new HashMap<>();
	}

	public void addNat(VnfForConfigurationInterface nat)
	{
		nats.add(nat);
	}

	public void removeNat(VnfForConfigurationInterface nat)
	{
		nats.remove(nat);
	}

	public void run(IpNetwork lan_network) throws InterruptedException
	{

		while (true)
		{
			check_events(lan_network);
			
			Thread.sleep(polling_ms);
		}
	}

	public void check_events(IpNetwork lan_network)
	{
		List<VnfForConfigurationInterface> natscopy = new ArrayList<>(nats);
		
		for (VnfForConfigurationInterface nat : natscopy)
		{
			Map<String,String> arpTable = new HashMap<>();
			
			List<ActiveHostEntry> new_hosts = new ArrayList<>();
			List<ActiveHostEntry> left_hosts = new ArrayList<>();

			ConfigurationSDN configuration;
			try
			{
				configuration = configurationService.getConfiguration(nat);
				if (configuration instanceof NatConfiguration)
				{
					NatConfiguration cfg = (NatConfiguration) configuration;

					if ( cfg.getConfiguration().getConfigNatNat() != null)
					{
						if ( cfg.getConfiguration().getConfigNatNat().getArpTable() != null)
						{
							if (cfg.getConfiguration().getConfigNatNat().getArpTable().getArpEntry() != null )
							{
								for(ArpEntry arpEntry : cfg.getConfiguration().getConfigNatNat().getArpTable().getArpEntry())
								{
									arpTable.put(arpEntry.getIp_address(),arpEntry.getMac_address());
								}
							}
						}
						if (cfg.getConfiguration().getConfigNatNat().getNatTable() != null)
						{
							if ( cfg.getConfiguration().getConfigNatNat().getNatTable().getNatSession() != null)
							{
								for(NatSession natSession : cfg.getConfiguration().getConfigNatNat().getNatTable().getNatSession())
								{
									String host_ip = natSession.getSrc_address();
									
									if ( ! active_hosts.containsKey(host_ip))
									{
										if (lan_network.contains(host_ip))
										{
											MacAddress host_mac= new MacAddress("00:00:00:00:00:00");
											if (arpTable.containsKey(host_ip))
											{
												host_mac = new MacAddress(arpTable.get(host_ip));
											}
											
											ActiveHostEntry ahe = new ActiveHostEntry(new Host(host_ip,host_mac),new Date());
											active_hosts.put(host_ip, ahe );
											new_hosts.add(ahe);
										}
									}
									else
									{
										active_hosts.get(host_ip).setLast_seen(new Date());
									}
								}
							}
						}
						
						for (Entry<String, ActiveHostEntry> entry : active_hosts.entrySet())
						{
							ActiveHostEntry ahe = entry.getValue();
							Date expire_date = new Date(ahe.getLast_seen().getTime() + timeout_left_ms);
							
							if (expire_date.before(new Date()))
							{
								left_hosts.add(ahe);
							}
						}
					}
				}
				for(ActiveHostEntry host_new : new_hosts)
				{
					natEventHandler.on_host_new(configuration,host_new.getHost());
				}
				for(ActiveHostEntry host_left : left_hosts)
				{
					natEventHandler.on_host_left(configuration,host_left.getHost());
					active_hosts.remove(host_left.getHost().getIp());
				}
			}
			catch (ConfigurationOrchestratorHTTPException e)
			{
				e.printStackTrace();
				natEventHandler.on_nat_fault(nat);
			} catch (ConfigurationOrchestratorConfigurationNotFoundException e)
			{
				e.printStackTrace();
				natEventHandler.on_nat_fault(nat);
			} catch (ConfigurationOrchestratorAuthenticationException e)
			{
				e.printStackTrace();
				System.exit(1);
			} catch (ConfigurationOrchestratorNotAuthenticatedException e)
			{
				e.printStackTrace();
				System.exit(1);
			} catch (ConfigurationorchestratorUnsupportedFunctionalCapabilityException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
