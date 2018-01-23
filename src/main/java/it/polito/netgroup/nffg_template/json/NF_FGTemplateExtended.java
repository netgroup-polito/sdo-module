package it.polito.netgroup.nffg_template.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.netgroup.nffg.json.VNFExtended;

public class NF_FGTemplateExtended extends NF_FGTemplate
{

	public VNFExtended buildVNF(String vnf_id, String vnf_name) throws NotImplementedYetException
	{
		List<it.polito.netgroup.nffg.json.Port> vnf_ports = new ArrayList<>();

		for (Port template_port : getPorts())
		{
			String[] port_range = template_port.getPosition().split("-");

			if (port_range[1].equals("N"))
			{
				throw new NotImplementedYetException("Not implemented yet.");
			}

			Integer i_from = new Integer(port_range[0]);
			Integer i_to = new Integer(port_range[1]);

			for (Integer portId = i_from; portId <= i_to; portId++)
			{
				it.polito.netgroup.nffg.json.Port p = new it.polito.netgroup.nffg.json.Port();
				p.setId(template_port.getLabel() + ':' + (portId - 1));

				vnf_ports.add(p);
			}
		}

		VNFExtended vnf = new VNFExtended();
		vnf.setId(vnf_id);
		vnf.setName(vnf_name);
		vnf.setVnfTemplate(getName());
		vnf.setFunctionalCapability(getFunctionalCapability());
		vnf.setPorts(vnf_ports);
		return vnf;
	}

	public static NF_FGTemplateExtended getFromJson(String json) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, NF_FGTemplateExtended.class);
	}

}
