package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceType;

public class CPUResource extends AbstractResource {

	public CPUResource(String _id) {
		super(_id,ResourceType.CPU);
	}

	public CPUResource(@JsonProperty("id") String _id,@JsonProperty("isUsed")Boolean _isUsed ,@JsonProperty("usedBy") String _usedBy) {
		super(_id,_isUsed,_usedBy,ResourceType.CPU);
	}

}
