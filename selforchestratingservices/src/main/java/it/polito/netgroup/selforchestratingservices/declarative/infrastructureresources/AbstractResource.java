package it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceType;
import it.polito.netgroup.selforchestratingservices.declarative_new.InfrastructureVNF;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = CPUResource.class, name = "CPU"),
})
public abstract class AbstractResource implements Resource
{
	@JsonProperty("type")
	ResourceType type;
	@JsonProperty("id")
	String id;
	@JsonProperty("isUsed")
	Boolean isUsed;
	@JsonProperty("usedBy")
	String usedBy;

	public AbstractResource(String _id, ResourceType _type) {
		this(_id,false,null,_type);
	}

	public AbstractResource(String _id,Boolean _isUsed , String _usedBy, ResourceType _type) {
		id=_id;
		isUsed=_isUsed;
		usedBy=_usedBy;
		type=_type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Boolean isUsed() {
		return isUsed;
	}

	@Override
	public void setUsed(String ivnf) {
		isUsed=true;
		usedBy=ivnf;
	}

	@Override
	public void unsetUsed() {
		usedBy=null;
		isUsed=false;
	}

	@Override
	public String getUsedBy() {
		return usedBy;
	}

	@Override
	public ResourceType getType() {
		return type;
	}
}
