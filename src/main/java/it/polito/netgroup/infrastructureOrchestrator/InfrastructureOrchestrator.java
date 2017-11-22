package it.polito.netgroup.infrastructureOrchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.nffg.json.NF_FGExtended;

public interface InfrastructureOrchestrator
{
	public boolean getNFFGStatus(String nffg_name);
	
	public NF_FGExtended getNFFG(String nffg_id)
			throws InfrastructureOrchestratorHTTPException, InfrastructureOrchestratorAuthenticationException,
			InfrastructureOrchestratorNotAuthenticatedException, InfrastructureOrchestratorNF_FGNotFoundException;

	public void removeNFFG(String nffg_name) throws InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException;
	
	public void addNFFG(NF_FGExtended nffg) throws JsonProcessingException, InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException;

}
