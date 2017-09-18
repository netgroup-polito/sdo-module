package it.polito.netgroup.selforchestratingservices.auto.prova;

import java.util.ArrayList;
import java.util.stream.Collectors;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;

public class On_new_nat_sessionEventHandler extends AbstractEventHandler
{

	public On_new_nat_sessionEventHandler(Variables _var)
	{
		super(_var);
	}


	public void fire(NatInfrastructureResource nat, NatSession session)
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

		 Host session_host = ( new Host(session.getSrc_address(),(nat.getArpTable().stream().filter(x -> x.getIp_address().equals(session.getSrc_address())).collect(Collectors.toCollection(ArrayList::new)) ).get(0).getMac_address()) );
		active_hosts.put(session.getSrc_address(),  ( new HostNat(session_host,nat) ));

	}

}