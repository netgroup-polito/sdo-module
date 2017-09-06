package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "macro")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NewMacroDescription.class, name = "new"),
    @JsonSubTypes.Type(value = FilterMacroDescription.class, name = "filter"),
    @JsonSubTypes.Type(value = MethodMacroDescription.class, name = "method"),
    @JsonSubTypes.Type(value = IfMacroDescription.class, name = "if"),

})
public abstract class MacroDescription
{
	@JsonProperty("macro")
	String macro;	
	@JsonProperty("assign_to")
	String assign_to;
	
	
	public abstract String getJavaCode();
}
