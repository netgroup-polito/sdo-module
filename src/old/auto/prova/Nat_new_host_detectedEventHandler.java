package it.polito.netgroup.selforchestratingservices.auto.prova;

import java.util.ArrayList;

import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;

public class Nat_new_host_detectedEventHandler extends AbstractEventHandler
{

	public Nat_new_host_detectedEventHandler(Variables _var)
	{
		super(_var);
	}


	public void fire(String hostIp, HostNat hostNat)
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


	}

}