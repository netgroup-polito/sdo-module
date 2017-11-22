package it.polito.netgroup.selforchestratingservices.auto.prova;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;

//Autogenerted class
public class NatImplementation1Implementation extends AbstractImplementation
{
	public NatImplementation1Implementation(Variables var)
	{
		super(var);
		resources.add(new NatResourceRequirement(var));

		name = "Implementation1";
	}

	@Override
	public Float getQoS(List<InfrastructureResource> resources)
	{
		TimeoutMap<String,HostNat> active_hosts = null;
		try
		{
			active_hosts = var.getVar("active_hosts",TimeoutMap.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		ArrayList<Host> hosts = null;
		try
		{
			hosts = var.getVar("hosts",ArrayList.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		String lbport = null;
		try
		{
			lbport = var.getVar("lbport",String.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		String lbmac = null;
		try
		{
			lbmac = var.getVar("lbmac",String.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		//Shadow internal resourcesUsed variable
		List<InfrastructureResource> resourcesUsed = resources.stream().filter(x -> x.isUsed()).collect(Collectors.toCollection(ArrayList::new));

		 return new Float((resourcesUsed.stream().filter(x -> x.getType().equals("NAT")).collect(Collectors.toCollection(ArrayList::new)) ).size()/hosts.size());
	}

	@Override
	public Float getQoS()
	{
		TimeoutMap<String,HostNat> active_hosts = null;
		try
		{
			active_hosts = var.getVar("active_hosts",TimeoutMap.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		ArrayList<Host> hosts = null;
		try
		{
			hosts = var.getVar("hosts",ArrayList.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		String lbport = null;
		try
		{
			lbport = var.getVar("lbport",String.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		String lbmac = null;
		try
		{
			lbmac = var.getVar("lbmac",String.class);
		}catch(Exception e){
			e.printStackTrace();
		}

		return new Float((resourcesUsed.stream().filter(x -> x.getType().equals("NAT")).collect(Collectors.toCollection(ArrayList::new)) ).size()/hosts.size());
	}
}
