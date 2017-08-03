package it.polito.netgroup.configurationorchestrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.netgroup.configurationorchestrator.json.nat.ConfigNatInterfaces;
import it.polito.netgroup.configurationorchestrator.json.nat.ConfigNatNat;
import it.polito.netgroup.configurationorchestrator.json.nat.ConfigurationType;
import it.polito.netgroup.configurationorchestrator.json.nat.IPv4Configuration;
import it.polito.netgroup.configurationorchestrator.json.nat.IfEntry;
import it.polito.netgroup.configurationorchestrator.json.nat.NatModel;
import it.polito.netgroup.configurationorchestrator.json.nat.ConfigurationType.TYPE;
import it.polito.netgroup.nffg.json.InterfaceLabel;
import it.polito.netgroup.nffg.json.IpAddressAndNetmask;
import it.polito.netgroup.nffg.json.MacAddress;
import it.polito.netgroup.nffg.json.VNFExtended;

public class NatConfiguration implements ConfigurationSDN
{
	private String tenant_id;
	private String nffg_id;
	private String vnf_id;
	private String fc;
	
	private MacAddress lan_mac;
	private IpAddressAndNetmask lan_ip;
	private NatModel natConfiguration;
	
	public NatConfiguration(String _tenant_id, String _nffg_id, VNFExtended vnf)
	{
		this(_tenant_id, _nffg_id, vnf.getId(),vnf.getFunctionalCapability());
	}

	public NatConfiguration(String _tenant_id, String _nffg_id, String _vnf_id , String _fc)
	{
		tenant_id = _tenant_id;
		nffg_id = _nffg_id;
		vnf_id = _vnf_id;
		fc = _fc;
		
		natConfiguration = new NatModel();
	}

	public void setIP(InterfaceLabel interfaceLabel, IpAddressAndNetmask nat_ip, MacAddress nat_mac) throws InvalidInterfaceLabel
	{
		if ( interfaceLabel.getValue().equals("User") )
		{
			lan_ip = nat_ip;
			lan_mac = nat_mac;
			
			
		}
		else throw new InvalidInterfaceLabel(interfaceLabel.getValue());
	}

	public static NatConfiguration getFromJson(String _tenant_id , String _nffg_id, String _vnf_id , String _fc , String json) throws JsonParseException, JsonMappingException, IOException
	{
		NatConfiguration cfg = new NatConfiguration(_tenant_id, _nffg_id, _vnf_id,_fc);
		ObjectMapper mapper = new ObjectMapper();
		NatModel natcfg = mapper.readValue(json, NatModel.class);
		
		cfg.setConfiguration(natcfg);
		
		return cfg;
	}
	
	private void setConfiguration(NatModel _natcfg)
	{
		natConfiguration = _natcfg;
	}

	@JsonIgnore
	@Override
	public String getId()
	{
		return vnf_id;
	}

	@JsonIgnore
	@Override
	public String getFunctionalCapability()
	{
		return fc;
	}

	@JsonIgnore
	@Override
	public String getVnfId()
	{
		return vnf_id;
	}

	@JsonIgnore
	@Override
	public String getNffgId()
	{
		return nffg_id;
	}

	@JsonIgnore
	@Override
	public String getTenantId()
	{
		return tenant_id;
	}

	@JsonIgnore
	@Override
	public String getJson() throws JsonProcessingException
	{
		//Default configuration
		IPv4Configuration ipv4_wan = new IPv4Configuration();
		ipv4_wan.setConfigurationType(new ConfigurationType(TYPE.dhcp));
		IfEntry ifWAN = new IfEntry();
		ifWAN.setId("WAN:0");
		ifWAN.setName("eth0");
		ifWAN.setIpv4_configuration(ipv4_wan);
		ifWAN.setManagement(false);

		IPv4Configuration ipv4_lan = new IPv4Configuration();
		ipv4_lan.setMac_address(lan_mac.getValue());
		ipv4_lan.setAddress(lan_ip.getIp());
		ipv4_lan.setNetmask(lan_ip.getNetmask());
		ipv4_lan.setConfigurationType(new ConfigurationType(TYPE.statico));
		IfEntry ifLAN = new IfEntry();
		ifLAN.setId("User:1");
		ifLAN.setName("eth1");
		ifLAN.setIpv4_configuration(ipv4_lan);
		ifLAN.setManagement(true);
		
		IPv4Configuration ipv4_man = new IPv4Configuration();
		ipv4_man.setConfigurationType(new ConfigurationType(TYPE.dhcp));
		IfEntry ifMAN = new IfEntry();
		ifMAN.setId("management:2");
		ifMAN.setName("eth2");
		ifMAN.setIpv4_configuration(ipv4_man);
		ifWAN.setManagement(true);

		List<IfEntry> ifEntryList = new ArrayList<>();
		ifEntryList.add(ifWAN);
		ifEntryList.add(ifLAN);
		ifEntryList.add(ifMAN);
		
		ConfigNatInterfaces config_nat = new ConfigNatInterfaces();
		config_nat.setIfEntry(ifEntryList);
		natConfiguration.setConfigNatInterfaces(config_nat);
		ConfigNatNat configNatNat = new ConfigNatNat();
		configNatNat.setPrivateInterface("User:1");
		configNatNat.setPublicInterface("WAN:0");
		natConfiguration.setConfigNatNat(configNatNat);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(natConfiguration);
	}

	public NatModel getConfiguration()
	{
		return natConfiguration;
	}

	@Override
	public void setTenantId(String tenant_id)
	{
		this.tenant_id = tenant_id;
	}

	@Override
	public void setNffgid(String nffg_name)
	{
		this.nffg_id = nffg_name;
		
	}

	@Override
	public void setVnfId(String id)
	{
		this.vnf_id = id;	
	}

}
