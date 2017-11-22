
package it.polito.netgroup.configurationorchestrator.json.transcoder;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ConfigTranscoderTranscoder {

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("template")
    private String template;

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("template")
    public String getTemplate() {
        return template;
	}

    @JsonProperty("template")
    public void setTemplate(String template)
    {
        this.template = template;
    }
}
