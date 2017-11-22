package it.polito.netgroup.selforchestratingservices.declarative_new;

import it.polito.netgroup.selforchestratingservices.declarative.Implementation;
import it.polito.netgroup.selforchestratingservices.declarative.Resource;
import it.polito.netgroup.selforchestratingservices.declarative.ResourceType;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceSet {
	HashMap<Resource, ResourceSetEntry> map;

	public ResourceSet(List<Resource> resources)
	{
		map = new HashMap<>();

		for(Resource r : resources)
		{
			map.put(r,new ResourceSetEntry(r));
		}
	}

	public void setResourceUsed(Implementation implementation, List<Resource> resources,Integer min) {
		for(Resource r : resources)
		{
			map.get(r).setUsedBy(implementation);
			min--;

			if ( min == 0 )
			{
				break;
			}
		}

		if ( min > 0 )
		{
			throw new InvalidParameterException("Error: unable to find all the resources required.");
		}
	}

	public void removeImplementation(Implementation implementation) {
		for(ResourceSetEntry entry : map.values())
		{
			if ( entry.isUsed() && entry.usedBy().equals(implementation))
			{
				entry.unsetUsed();
			}
		}
	}

	public List<Resource> getResourcesOf(Implementation implementation) {
		List<Resource> ret = new ArrayList<>();

		for(ResourceSetEntry entry : map.values())
		{
			if ( entry.isUsed() && entry.usedBy().equals(implementation))
			{
				ret.add(entry.getResource());
			}
		}
		return ret;
	}

	public List<Resource> getFreeOfType(ResourceType resourceType) {
		List<Resource> ret = new ArrayList<>();

		for(ResourceSetEntry entry : map.values())
		{
			if ( !entry.isUsed() && entry.getResource().getType() == resourceType)
			{
				ret.add(entry.getResource());
			}
		}
		return ret;
	}

}


class ResourceSetEntry
{
	Resource r;
	Boolean used;
	Implementation implementation;

	public ResourceSetEntry(Resource _r)
	{
		r=_r;
		used=false;
		implementation=null;
	}

	public void setUsedBy(Implementation _implementation) {
		used=true;
		implementation=_implementation;
	}

	public boolean isUsed() {
		return used;
	}

	public Implementation usedBy() {
		return implementation;
	}

	public void unsetUsed() {
		used=false;
		implementation=null;
	}

	public Resource getResource() {
		return r;
	}
}
