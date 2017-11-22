package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import java.util.List;

import it.polito.netgroup.configurationorchestrator.ConfigurationSDN;
import it.polito.netgroup.configurationorchestrator.NatConfiguration;
import it.polito.netgroup.configurationorchestrator.json.nat.ArpEntry;
import it.polito.netgroup.configurationorchestrator.json.nat.NatSession;
import it.polito.netgroup.selforchestratingservices.declarative_new.Infrastructure;

public class NatInfrastructureVNF extends AbstractBasicInfrastructureVNF
{

	public NatInfrastructureVNF(Infrastructure infra, String id)
	{
		super(infra,id);
	}

	public List<NatSession> getNatSession() throws Exception {
		ConfigurationSDN configuration = infrastructure.getConfiguration(this);
		if (configuration instanceof NatConfiguration) {
			NatConfiguration cfg = (NatConfiguration) configuration;
			return cfg.getConfigurationModel().getConfigNatNat().getNatTable().getNatSession();
		}
		throw new Exception("Unable to read the configuration");
	}


	public List<ArpEntry> getArpTable() throws Exception {
		ConfigurationSDN configuration = infrastructure.getConfiguration(this);
		if (configuration instanceof NatConfiguration) {
			NatConfiguration cfg = (NatConfiguration) configuration;
			return cfg.getConfigurationModel().getConfigNatNat().getArpTable().getArpEntry();
		}
		throw new Exception("Unable to read the configuration");
	}

	public String getDatastoreTemplate()
	{
		return "nat";
	}

	@Override
	public String getFunctionalCapability() {
		return "nat";
	}
}
