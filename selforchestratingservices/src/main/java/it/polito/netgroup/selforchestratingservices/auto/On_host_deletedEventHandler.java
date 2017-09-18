package it.polito.netgroup.selforchestratingservices.auto;

import java.security.*;
import java.util.*;
import java.util.stream.*;
import it.polito.netgroup.configurationorchestrator.*;
import it.polito.netgroup.configurationorchestrator.json.nat.*;
import it.polito.netgroup.selforchestratingservices.*;
import it.polito.netgroup.nffg.json.*;
import it.polito.netgroup.selforchestratingservices.declarative_new.*;
import it.polito.netgroup.selforchestratingservices.declarative.*;
import it.polito.netgroup.selforchestratingservices.declarative.infrastructureresources.*;
import it.polito.netgroup.selforchestratingservices.declarative.dirtychecker.*;

public class On_host_deletedEventHandler 
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

		Host host = hostNat.getHost();
		hosts.remove(host);

		Optional<Map.Entry<NatInfrastructureResource, Long>> ret = active_hosts.values().stream().map(HostNat::getNat).collect(Collectors.toCollection(ArrayList::new)).stream().collect(Collectors.groupingBy(n->n,Collectors.counting())).entrySet().stream().min(Map.Entry.comparingByValue());
		if ( ret.isPresent() == true ) { 
			NatInfrastructureResource mynat = ret.get().getKey();
			Infrastructure infrastructure = mynat.getInfrastructure();
			infrastructure.removeFlowRuleStartingWith("LB_DEFAULT_");
			infrastructure.addFlowRule(new FlowRule("LB_DEFAULT_" + mynat.getId() ,"",1,new Match(lbport,null,null),new Action("vnf:" + mynat.getId() + ":User:1" )));
		}else{
		}
;

		return true;
	}

}