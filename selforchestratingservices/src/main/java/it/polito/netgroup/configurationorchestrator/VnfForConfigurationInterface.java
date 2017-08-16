package it.polito.netgroup.configurationorchestrator;

import it.polito.netgroup.nffg.json.VNF;

public interface VnfForConfigurationInterface
{

	public String getNffgId();

	public String getTenantId();

	public String getId();
	
	public String getFunctionalCapability();

}
