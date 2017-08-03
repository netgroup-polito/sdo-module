package it.polito.netgroup.configurationorchestrator;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConfigurationSDN extends VnfForConfigurationInterface
{

	public String getVnfId();

	public String getNffgId();

	public String getTenantId();
	
	public String getJson() throws JsonProcessingException;

	public void setTenantId(String tenant_id);

	public void setNffgid(String nffg_name);

	public void setVnfId(String id);
}
