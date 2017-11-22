package it.polito.netgroup.configurationorchestrator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.netgroup.configurationorchestrator.json.transcoder.*;
import it.polito.netgroup.configurationorchestrator.json.transcoder.ConfigurationType.TYPE;
import it.polito.netgroup.nffg.json.IpAddressAndNetmask;
import it.polito.netgroup.nffg.json.VNFExtended;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TranscoderConfiguration extends AbstractConfigurationSDN
{
	private TranscoderModel transcoderModel;

	public TranscoderConfiguration(String _tenant_id, String _nffg_id, VNFExtended vnf)
	{
		this(_tenant_id, _nffg_id, vnf.getId(),vnf.getFunctionalCapability());
	}

	public TranscoderConfiguration(String _tenant_id, String _nffg_id, String _vnf_id , String _fc)
	{
		super(_tenant_id, _nffg_id, _vnf_id,_fc);

		transcoderModel = new TranscoderModel();
		transcoderModel.setConfigTranscoderInterfaces(new ConfigTranscoderInterfaces());
		transcoderModel.setConfigTranscoderTranscoder(new ConfigTranscoderTranscoder());

		IfEntry ifEntryLan = new IfEntry();
		IfEntry ifEntryMan = new IfEntry();

		IpAddressAndNetmask lan_ip = new IpAddressAndNetmask("192.168.10.100","255.255.255.0");
		IPv4Configuration ipv4_lan = new IPv4Configuration();
		ipv4_lan.setAddress(lan_ip.getIp());
		ipv4_lan.setNetmask(lan_ip.getNetmask());
		ipv4_lan.setConfigurationType(new ConfigurationType(TYPE.statico));
		ifEntryLan.setIpv4_configuration(ipv4_lan);
		ifEntryLan.setId("Port:1");
		ifEntryLan.setName("eth0");
		ifEntryLan.setManagement(false);

		/*
		IPv4Configuration ipv4_lanMan = new IPv4Configuration();
		ipv4_lanMan.setConfigurationType(new ConfigurationType(TYPE.dhcp));
		ifEntryMan.setIpv4_configuration(ipv4_lanMan);
		ifEntryMan.setId("management:2");
		ifEntryMan.setName("eth1");
		ifEntryMan.setManagement(true);
		*/

		transcoderModel.getConfigTranscoderInterfaces().setIfEntry(new ArrayList<IfEntry>() );
		transcoderModel.getConfigTranscoderInterfaces().getIfEntry().add(ifEntryLan);
		//transcoderModel.getConfigTranscoderInterfaces().getIfEntry().add(ifEntryMan);

	}

	public Boolean getEnabled()
	{
		return transcoderModel.getConfigTranscoderTranscoder().getEnabled();
	}

	public void setEnabled(Boolean enabled)
	{
		transcoderModel.getConfigTranscoderTranscoder().setEnabled(enabled);
	}

	public void setLanIP(IpAddressAndNetmask lan_ip)
	{
		for (IfEntry ifEntry : transcoderModel.getConfigTranscoderInterfaces().getIfEntry())
		{
			if ( ifEntry.getId().equals("Port:1"))
			{
				IPv4Configuration ipv4_lan = new IPv4Configuration();
				ipv4_lan.setAddress(lan_ip.getIp());
				ipv4_lan.setNetmask(lan_ip.getNetmask());
				ipv4_lan.setConfigurationType(new ConfigurationType(TYPE.statico));
				ifEntry.setIpv4_configuration(ipv4_lan);
			}
		}
	}

	public static TranscoderConfiguration getFromJson(String _tenant_id , String _nffg_id, String _vnf_id , String _fc , String json) throws JsonParseException, JsonMappingException, IOException
	{
		TranscoderConfiguration cfg = new TranscoderConfiguration(_tenant_id, _nffg_id, _vnf_id,_fc);
		ObjectMapper mapper = new ObjectMapper();
		TranscoderModel transcodercfg = mapper.readValue(json, TranscoderModel.class);

		cfg.setConfigurationModel(transcodercfg);
		
		return cfg;
	}

	private void setConfigurationModel(TranscoderModel _modelcfg)
	{
		transcoderModel = _modelcfg;
	}

	public TranscoderModel getConfigurationModel()
	{
		for(IfEntry entry : transcoderModel.getConfigTranscoderInterfaces().getIfEntry())
		{
			if ( entry.getId().equals("management:2"))
			{
				transcoderModel.getConfigTranscoderInterfaces().getIfEntry().remove(entry);
				break;
			}
		}

		return transcoderModel;
	}


	public boolean getStatus() {
		return transcoderModel.getConfigTranscoderTranscoder().getEnabled();
	}


	public ConfigurationSDN copy()  {
		try {
			return TranscoderConfiguration.getFromJson(getTenantId(),getNffgId(),getVnfId(),getFunctionalCapability(),getJson());
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}

	public String getTranscoderTemplate() {
		return transcoderModel.getConfigTranscoderTranscoder().getTemplate();
	}

	public void setTranscoderTemplate(String template) {
		transcoderModel.getConfigTranscoderTranscoder().setTemplate(template);
	}
}
