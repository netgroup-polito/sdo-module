
package it.polito.netgroup.configurationorchestrator.json.nat;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "arp-table",
    "private-interface",
    "public-interface",
    "nat-table"
})
public class ConfigNatNat {

    @JsonProperty("arp-table")
    private ArpTable arpTable;
    @JsonProperty("private-interface")
    private String privateInterface;
    @JsonProperty("public-interface")
    private String publicInterface;
    @JsonProperty("nat-table")
    private NatTable natTable;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("arp-table")
    public ArpTable getArpTable() {
        return arpTable;
    }

    @JsonProperty("arp-table")
    public void setArpTable(ArpTable arpTable) {
        this.arpTable = arpTable;
    }

    @JsonProperty("private-interface")
    public String getPrivateInterface() {
        return privateInterface;
    }

    @JsonProperty("private-interface")
    public void setPrivateInterface(String privateInterface) {
        this.privateInterface = privateInterface;
    }

    @JsonProperty("public-interface")
    public String getPublicInterface() {
        return publicInterface;
    }

    @JsonProperty("public-interface")
    public void setPublicInterface(String publicInterface) {
        this.publicInterface = publicInterface;
    }

    @JsonProperty("nat-table")
    public NatTable getNatTable() {
        return natTable;
    }

    @JsonProperty("nat-table")
    public void setNatTable(NatTable natTable) {
        this.natTable = natTable;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
