
package it.polito.netgroup.nffg_template.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "name", "functional-capability", "expandable", "vnf-type", "uri-image-type", "uri-image", "uri-yang", "memory-size",
		"root-file-system-size", "ephemeral-file-system-size", "swap-disk-size", "CPUrequirements", "ports" })
public class NF_FGTemplate
{

	@JsonProperty("name")
	private String name;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("functional-capability")
	private String functionalCapability;
	@JsonProperty("expandable")
	private Boolean expandable;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-type")
	private NF_FGTemplate.VnfType vnfType;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("uri-image-type")
	private NF_FGTemplate.UriImageType uriImageType;
	@JsonProperty("uri-image")
	private String uriImage;
	@JsonProperty("uri-yang")
	private String uriYang;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("memory-size")
	private Double memorySize;
	@JsonProperty("root-file-system-size")
	private Double rootFileSystemSize;
	@JsonProperty("ephemeral-file-system-size")
	private Double ephemeralFileSystemSize;
	@JsonProperty("swap-disk-size")
	private Double swapDiskSize;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("CPUrequirements")
	private CPUrequirements cPUrequirements;
	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	private List<Port> ports = null;

	@JsonProperty("name")
	public String getName()
	{
		return name;
	}

	@JsonProperty("name")
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("functional-capability")
	public String getFunctionalCapability()
	{
		return functionalCapability;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("functional-capability")
	public void setFunctionalCapability(String functionalCapability)
	{
		this.functionalCapability = functionalCapability;
	}

	@JsonProperty("expandable")
	public Boolean getExpandable()
	{
		return expandable;
	}

	@JsonProperty("expandable")
	public void setExpandable(Boolean expandable)
	{
		this.expandable = expandable;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-type")
	public NF_FGTemplate.VnfType getVnfType()
	{
		return vnfType;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("vnf-type")
	public void setVnfType(NF_FGTemplate.VnfType vnfType)
	{
		this.vnfType = vnfType;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("uri-image-type")
	public NF_FGTemplate.UriImageType getUriImageType()
	{
		return uriImageType;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("uri-image-type")
	public void setUriImageType(NF_FGTemplate.UriImageType uriImageType)
	{
		this.uriImageType = uriImageType;
	}

	@JsonProperty("uri-image")
	public String getUriImage()
	{
		return uriImage;
	}

	@JsonProperty("uri-image")
	public void setUriImage(String uriImage)
	{
		this.uriImage = uriImage;
	}

	@JsonProperty("uri-yang")
	public String getUriYang()
	{
		return uriYang;
	}

	@JsonProperty("uri-yang")
	public void setUriYang(String uriYang)
	{
		this.uriYang = uriYang;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("memory-size")
	public Double getMemorySize()
	{
		return memorySize;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("memory-size")
	public void setMemorySize(Double memorySize)
	{
		this.memorySize = memorySize;
	}

	@JsonProperty("root-file-system-size")
	public Double getRootFileSystemSize()
	{
		return rootFileSystemSize;
	}

	@JsonProperty("root-file-system-size")
	public void setRootFileSystemSize(Double rootFileSystemSize)
	{
		this.rootFileSystemSize = rootFileSystemSize;
	}

	@JsonProperty("ephemeral-file-system-size")
	public Double getEphemeralFileSystemSize()
	{
		return ephemeralFileSystemSize;
	}

	@JsonProperty("ephemeral-file-system-size")
	public void setEphemeralFileSystemSize(Double ephemeralFileSystemSize)
	{
		this.ephemeralFileSystemSize = ephemeralFileSystemSize;
	}

	@JsonProperty("swap-disk-size")
	public Double getSwapDiskSize()
	{
		return swapDiskSize;
	}

	@JsonProperty("swap-disk-size")
	public void setSwapDiskSize(Double swapDiskSize)
	{
		this.swapDiskSize = swapDiskSize;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("CPUrequirements")
	public CPUrequirements getCPUrequirements()
	{
		return cPUrequirements;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("CPUrequirements")
	public void setCPUrequirements(CPUrequirements cPUrequirements)
	{
		this.cPUrequirements = cPUrequirements;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	public List<Port> getPorts()
	{
		return ports;
	}

	/**
	 * 
	 * (Required)
	 * 
	 */
	@JsonProperty("ports")
	public void setPorts(List<Port> ports)
	{
		this.ports = ports;
	}

	public enum UriImageType
	{

		LOCAL_FILE("local-file"), REMOTE_FILE("remote-file"), DOCKER_REGISTRY("docker-registry");
		private final String value;
		private final static Map<String, NF_FGTemplate.UriImageType> CONSTANTS = new HashMap<String, NF_FGTemplate.UriImageType>();

		static
		{
			for (NF_FGTemplate.UriImageType c : values())
			{
				CONSTANTS.put(c.value, c);
			}
		}

		private UriImageType(String value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return this.value;
		}

		@JsonValue
		public String value()
		{
			return this.value;
		}

		@JsonCreator
		public static NF_FGTemplate.UriImageType fromValue(String value)
		{
			NF_FGTemplate.UriImageType constant = CONSTANTS.get(value);
			if (constant == null)
			{
				throw new IllegalArgumentException(value);
			} else
			{
				return constant;
			}
		}

	}

	public enum VnfType
	{

		VIRTUAL_MACHINE_KVM("virtual-machine-kvm"), DOCKER("docker"), NATIVE("native"), DPDK("dpdk"), ONOS_APPLICATION(
				"onos-application");
		private final String value;
		private final static Map<String, NF_FGTemplate.VnfType> CONSTANTS = new HashMap<String, NF_FGTemplate.VnfType>();

		static
		{
			for (NF_FGTemplate.VnfType c : values())
			{
				CONSTANTS.put(c.value, c);
			}
		}

		private VnfType(String value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return this.value;
		}

		@JsonValue
		public String value()
		{
			return this.value;
		}

		@JsonCreator
		public static NF_FGTemplate.VnfType fromValue(String value)
		{
			NF_FGTemplate.VnfType constant = CONSTANTS.get(value);
			if (constant == null)
			{
				throw new IllegalArgumentException(value);
			} else
			{
				return constant;
			}
		}

	}

}
