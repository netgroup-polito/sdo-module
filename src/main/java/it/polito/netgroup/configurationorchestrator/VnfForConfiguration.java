package it.polito.netgroup.configurationorchestrator;

import it.polito.netgroup.nffg.json.VNFExtended;

public class VnfForConfiguration implements VnfForConfigurationInterface
{
	private String tenant_id;
	private String nffg_id;
	private String vnf_id;
	private String fc;
	
	public VnfForConfiguration(String _tenant_id, String _nffg_id, VNFExtended vnf)
	{
		tenant_id = _tenant_id;
		nffg_id = _nffg_id;
		vnf_id = vnf.getId();
		fc = vnf.getFunctionalCapability();
	}	

	public VnfForConfiguration(String _tenant_id, String _nffg_id, NatConfiguration nat_cfg)
	{
		tenant_id = _tenant_id;
		nffg_id = _nffg_id;
		vnf_id = nat_cfg.getId();
		fc = nat_cfg.getFunctionalCapability();
	}

	public VnfForConfiguration(String _tenant_id, String _nffg_id, String _vnf_id , String _functional_capability)
	{
		tenant_id = _tenant_id;
		nffg_id = _nffg_id;
		vnf_id = _vnf_id;
		fc = _functional_capability;
	}
	
	@Override
	public String getTenantId()
	{
		return tenant_id;
	}

	@Override
	public String getNffgId()
	{
		return nffg_id;
	}

	@Override
	public String getId()
	{
		return vnf_id;
	}

	@Override
	public String getFunctionalCapability()
	{
		return fc;
	}
}
