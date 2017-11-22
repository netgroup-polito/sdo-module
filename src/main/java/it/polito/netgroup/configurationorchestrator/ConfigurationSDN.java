package it.polito.netgroup.configurationorchestrator;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConfigurationSDN extends VnfForConfigurationInterface
{

	String getVnfId();

	String getNffgId();

	String getTenantId();

	String getFunctionalCapability();

	String getJson() throws JsonProcessingException;

	void setTenantId(String tenant_id);

	void setNffgid(String nffg_name);

	void setVnfId(String id);

	void setFunctionalCapability(String functionalCapability);

	ConfigurationSDN copy();

}
