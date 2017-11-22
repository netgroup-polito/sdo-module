package it.polito.netgroup.configurationorchestrator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.netgroup.configurationorchestrator.json.transcoder.TranscoderModel;

public abstract class AbstractConfigurationSDN implements ConfigurationSDN {
	protected String tenant_id;
	protected String nffg_id;
	protected String vnf_id;
	protected String fc;


	public abstract Object getConfigurationModel();

	public AbstractConfigurationSDN(String _tenant_id,String _nffg_id, String _vnf_id, String _fc)
	{
		tenant_id=_tenant_id;
		nffg_id=_nffg_id;
		vnf_id=_vnf_id;
		fc=_fc;
	}

	@JsonIgnore
	@Override
	public String getId()
	{
		return vnf_id;
	}

	@JsonIgnore
	@Override
	public String getFunctionalCapability()
	{
		return fc;
	}

	@JsonIgnore
	@Override
	public String getVnfId()
	{
		return vnf_id;
	}

	@JsonIgnore
	@Override
	public String getNffgId()
	{
		return nffg_id;
	}

	@JsonIgnore
	@Override
	public String getTenantId()
	{
		return tenant_id;
	}

	@JsonIgnore
	@Override
	public String getJson() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getConfigurationModel());
	}

	@Override
	public void setTenantId(String tenant_id)
	{
		this.tenant_id = tenant_id;
	}

	@Override
	public void setNffgid(String nffg_name)
	{
		this.nffg_id = nffg_name;

	}

	@Override
	public void setVnfId(String id)
	{
		this.vnf_id = id;
	}

	@Override
	public void setFunctionalCapability(String functionalCapability) {
		fc = functionalCapability;
	}

}
