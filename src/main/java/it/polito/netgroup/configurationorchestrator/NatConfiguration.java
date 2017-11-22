package it.polito.netgroup.configurationorchestrator;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
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

public class NatConfiguration extends AbstractConfigurationSDN
{
	//private MacAddress lan_mac;
	//private IpAddressAndNetmask lan_ip;

	private NatModel natModel;
	
	public NatConfiguration(String _tenant_id, String _nffg_id, VNFExtended vnf)
	{
		this(_tenant_id, _nffg_id, vnf.getId(),vnf.getFunctionalCapability());
	}

	public NatConfiguration(String _tenant_id, String _nffg_id, String _vnf_id , String _fc)
	{
		super(_tenant_id, _nffg_id, _vnf_id,_fc);

		natModel = new NatModel();


		IpAddressAndNetmask lan_ip = new IpAddressAndNetmask("192.168.10.100", "255.255.255.0");
		MacAddress lan_mac =new MacAddress("02:01:02:03:04:05");

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
		ifMAN.setManagement(true);

		List<IfEntry> ifEntryList = new ArrayList<>();
		ifEntryList.add(ifWAN);
		ifEntryList.add(ifLAN);
		ifEntryList.add(ifMAN);

		ConfigNatInterfaces config_nat = new ConfigNatInterfaces();
		config_nat.setIfEntry(ifEntryList);
		natModel.setConfigNatInterfaces(config_nat);
		ConfigNatNat configNatNat = new ConfigNatNat();
		configNatNat.setPrivateInterface("User:1");
		configNatNat.setPublicInterface("WAN:0");
		natModel.setConfigNatNat(configNatNat);

	}

	public void setIP(InterfaceLabel interfaceLabel, IpAddressAndNetmask nat_ip, MacAddress nat_mac) throws InvalidInterfaceLabel
	{
		if ( interfaceLabel.getValue().equals("User") )
		{
			natModel.setConfigNatInterfaces(new ConfigNatInterfaces());

		}
		else throw new InvalidInterfaceLabel(interfaceLabel.getValue());

		throw new InvalidInterfaceLabel(interfaceLabel.getValue());
	}

	public static NatConfiguration getFromJson(String _tenant_id , String _nffg_id, String _vnf_id , String _fc , String json) throws JsonParseException, JsonMappingException, IOException
	{
		NatConfiguration cfg = new NatConfiguration(_tenant_id, _nffg_id, _vnf_id,_fc);
		ObjectMapper mapper = new ObjectMapper();
		NatModel natcfg = mapper.readValue(json, NatModel.class);

		cfg.setConfigurationModel(natcfg);
		
		return cfg;
	}
	
	private void setConfigurationModel(NatModel _natcfg)
	{
		natModel = _natcfg;
	}

	public NatModel getConfigurationModel()
	{
		return natModel;
	}

	public ConfigurationSDN copy()
	{
		try {
			return TranscoderConfiguration.getFromJson(getTenantId(),getNffgId(),getVnfId(),getFunctionalCapability(),getJson());
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}
}
