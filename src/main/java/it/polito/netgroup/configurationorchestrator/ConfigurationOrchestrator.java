package it.polito.netgroup.configurationorchestrator;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConfigurationOrchestrator
{
	ConfigurationSDN getConfiguration(VnfForConfigurationInterface vnf)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorConfigurationNotFoundException,
			ConfigurationOrchestratorAuthenticationException, ConfigurationOrchestratorNotAuthenticatedException, ConfigurationorchestratorUnsupportedFunctionalCapabilityException;

	void setConfiguration(ConfigurationSDN configuration)
			throws JsonProcessingException, ConfigurationOrchestratorHTTPException,
			ConfigurationOrchestratorNotAuthenticatedException, ConfigurationOrchestratorAuthenticationException;

	void removeConfiguration(String tenant_id, String nffg_id, String vnf_id)
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorNotAuthenticatedException;
	List<StartedVNF> getStartedVNF()
			throws ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException;

	
	void waitUntilStarted(VnfForConfigurationInterface vnfC) throws InterruptedException,
			ConfigurationOrchestratorHTTPException, ConfigurationOrchestratorAuthenticationException,
			ConfigurationOrchestratorConfigurationNotFoundException, ConfigurationOrchestratorNotAuthenticatedException;
}
