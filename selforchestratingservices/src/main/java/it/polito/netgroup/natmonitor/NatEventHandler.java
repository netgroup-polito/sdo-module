package it.polito.netgroup.natmonitor;

import it.polito.netgroup.configurationorchestrator.VnfForConfiguration;
import it.polito.netgroup.nffg.json.Host;

public interface NatEventHandler
{

	public boolean on_host_new(Host n);

	public boolean on_host_left(Host n);

	public boolean on_nat_fault(VnfForConfiguration nat);

}
