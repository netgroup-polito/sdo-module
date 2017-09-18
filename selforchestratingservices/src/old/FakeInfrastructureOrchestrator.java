package it.polito.netgroup.infrastructureOrchestrator;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.polito.netgroup.nffg.json.NF_FGExtended;

public class FakeInfrastructureOrchestrator implements InfrastructureOrchestrator
{
	HashMap<String,NF_FGExtended> nffgs = new HashMap<>();
	
	
	@Override
	public boolean getNFFGStatus(String nffg_name)
	{
		return false;
	}

	@Override
	public NF_FGExtended getNFFG(String nffg_id)
			throws InfrastructureOrchestratorHTTPException, InfrastructureOrchestratorAuthenticationException,
			InfrastructureOrchestratorNotAuthenticatedException, InfrastructureOrchestratorNF_FGNotFoundException
	{
		return nffgs.get(nffg_id);
	}

	@Override
	public void removeNFFG(String nffg_id) throws InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		nffgs.remove(nffg_id);
	}

	@Override
	public void addNFFG(NF_FGExtended nffg) throws JsonProcessingException, InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		nffgs.put(nffg.getId(), nffg);
	}

	@Override
	public void addNFFG(String id, String nffg_json)
			throws JsonProcessingException, InfrastructureOrchestratorHTTPException,
			InfrastructureOrchestratorAuthenticationException, InfrastructureOrchestratorNotAuthenticatedException
	{
		try
		{
			nffgs.put(id, NF_FGExtended.getFromJson(nffg_json));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InfrastructureOrchestratorHTTPException(e.getMessage());
		}
	}

}
