package it.polito.netgroup.selforchestratingservices.compiler.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IfCondition
{
	public enum COMPARE_MODE {
		equal_to,
	}
	
	@JsonProperty("operand1")
	EvaluableStatement operand1;
	@JsonProperty("operand2")
	EvaluableStatement operand2;
	
	@JsonProperty("compare_mode")
	COMPARE_MODE compare_mode;
}
