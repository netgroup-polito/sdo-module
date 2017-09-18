package it.polito.netgroup.selforchestratingservices.auto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;

public class Nat_new_host_detectedEventHandler 
{
	public static boolean fire(Variables var,String hostIp, HostNat hostNat) throws Exception
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

		Integer counter = null;
		try
		{
			counter = var.getVar("counter",Integer.class);
		}catch(Exception e){
			e.printStackTrace();
		}


		return true;
	}

}