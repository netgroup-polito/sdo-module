package it.polito.netgroup.natmonitor;

import it.polito.netgroup.configurationorchestrator.VnfForConfigurationInterface;
import it.polito.netgroup.nffg.json.Host;

public interface NatEventHandler
{

	public boolean on_host_new(VnfForConfigurationInterface nat,Host host);

	public boolean on_host_left(VnfForConfigurationInterface nat,Host host);

	public boolean on_nat_fault(VnfForConfigurationInterface nat);

}
