package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import it.polito.netgroup.selforchestratingservices.declarative_new.Infrastructure;

public class TranscoderInfrastructureVNF extends AbstractBasicInfrastructureVNF
{

	public TranscoderInfrastructureVNF(Infrastructure infra, String id)
	{
		super(infra,id);
	}

	/*
	public Boolean getStatus() throws Exception {
		ConfigurationSDN configuration = infrastructure.getConfigurationModel(this);
		if (configuration instanceof TranscoderConfiguration) {
			TranscoderConfiguration cfg = (TranscoderConfiguration) configuration;
			return cfg.getConfigurationModel().getConfigTranscoderTranscoder().getEnabled();
		}
		throw new Exception("Unable to read the configuration");
	}


	public void setStatus(Boolean status) throws Exception {
		ConfigurationSDN configuration = infrastructure.getConfigurationModel(this);
		if (configuration instanceof TranscoderConfiguration) {
			TranscoderConfiguration cfg = (TranscoderConfiguration) configuration;
			cfg.getConfigurationModel().getConfigTranscoderTranscoder().setEnabled(status);
		}
		throw new Exception("Unable to read the configuration");
	}
	*/

	public String getDatastoreTemplate()
	{
		return "transcoder";
	}

	@Override
	public String getFunctionalCapability() {
		return "transcoder";
	}
}
