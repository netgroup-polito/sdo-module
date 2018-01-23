package it.polito.netgroup.configurationorchestrator;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StartedVNF
{

	@JsonProperty("graph_id")
	private String nffg_id;
	@JsonProperty("address")
	private String address;
	@JsonProperty("tenant_id")
	private String tenant_id;
	@JsonProperty("vnf_id")
	private String vnf_id;

	public String getTenantId()
	{
		return tenant_id;
	}

	public String getNffgId()
	{
		return nffg_id;
	}

	public String getId()
	{
		return vnf_id;
	}

	public static List<StartedVNF> getFromJson(String json) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(List.class, StartedVNF.class));
		
	}

}
