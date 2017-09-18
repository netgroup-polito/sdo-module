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

public class MyInfrastructureEventHandler extends AbstractInfrastructureEventHandler {

	public MyInfrastructureEventHandler(Variables var)
	{
		super(var);
	};

	@Override
	public void on_resource_added(Resource resource) throws Exception {
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

		if ( resource instanceof NatInfrastructureResource) {
			NatInfrastructureResource nat = (NatInfrastructureResource) resource;
			Infrastructure infrastructure = nat.getInfrastructure();
			infrastructure.removeFlowRuleStartingWith("LB_DEFAULT_");
			infrastructure.addFlowRule(new FlowRule("LB_DEFAULT_" + nat.getId() ,"",1,new Match(lbport,null,null),new Action("vnf:" + nat.getId() + ":User:1" )));
			infrastructure.addFlowRule(new FlowRule("LB_REPLY_" + nat.getId() ,"",1,new Match("vnf:" + nat.getId() + ":User:1" ,null,null),new Action(lbport)));
		}
	}

	@Override
	public void on_resource_removing(Resource resource) throws Exception {
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

		if ( resource instanceof NatInfrastructureResource) {
			NatInfrastructureResource nat = (NatInfrastructureResource) resource;
		}
	}

	@Override
	public void on_resource_removed(Resource resource) throws Exception {
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

	}
}