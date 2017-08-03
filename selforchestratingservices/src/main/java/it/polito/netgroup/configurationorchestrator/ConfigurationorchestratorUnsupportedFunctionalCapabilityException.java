package it.polito.netgroup.configurationorchestrator;

public class ConfigurationorchestratorUnsupportedFunctionalCapabilityException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigurationorchestratorUnsupportedFunctionalCapabilityException(String vnf_type)
	{
		super(vnf_type);
	}

}
