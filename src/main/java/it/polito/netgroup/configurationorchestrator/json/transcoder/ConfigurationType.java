package it.polito.netgroup.configurationorchestrator.json.transcoder;


public class ConfigurationType
{
	public enum TYPE
	{
		dhcp,
		statico,
		not_defined
	}
	private TYPE type;
	
	
	public ConfigurationType(String type)
	{
		setType(type);
	}
	
	public ConfigurationType()
	{
		type = TYPE.not_defined;
	}
	
	public ConfigurationType(TYPE t)
	{
		type = t;
	}

	public void setType(String typestr)
	{
		if (typestr.equals("static")) type = TYPE.statico;
		else type = TYPE.valueOf(typestr);
	}
	
	public String getType()
	{
		return this.toString();
	}

	@Override
	public String toString()
	{
		if (type.equals(TYPE.statico)) return "static";
		return type.toString();
	}
}
