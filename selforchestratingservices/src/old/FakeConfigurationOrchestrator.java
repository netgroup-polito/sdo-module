package it.polito.netgroup.configurationorchestrator;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public class FakeConfigurationOrchestrator implements ConfigurationOrchestrator
{
	@Override
	public ConfigurationSDN getConfiguration(VnfForConfigurationInterface vnf)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorConfigurationNotFoundException,
			ConfigurationOrchestratorAuthenticationException, ConfigurationOrchestratorNotAuthenticatedException,
			ConfigurationorchestratorUnsupportedFunctionalCapabilityException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(ConfigurationSDN configuration)
			throws JsonProcessingException, ConfigurationOrchestratorHTTPException,
			ConfigurationOrchestratorNotAuthenticatedException, ConfigurationOrchestratorAuthenticationException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConfiguration(String tenant_id, String nffg_id, String vnf_id)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorNotAuthenticatedException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<StartedVNF> getStartedVNF()
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void waitUntilStarted(VnfForConfigurationInterface vnfC) throws InterruptedException,
			ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException
	{
		// TODO Auto-generated method stub
		
	}

}
