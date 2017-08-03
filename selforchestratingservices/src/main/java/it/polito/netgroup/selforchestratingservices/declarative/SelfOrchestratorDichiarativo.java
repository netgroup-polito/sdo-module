package it.polito.netgroup.selforchestratingservices.declarative;

import java.util.List;

public interface SelfOrchestratorDichiarativo
{

	void newServiceState();
	void commit();
	Infrastructure getInfrastructure();
	List<ElementaryService> getElementaryServices();
	String getName();
}
