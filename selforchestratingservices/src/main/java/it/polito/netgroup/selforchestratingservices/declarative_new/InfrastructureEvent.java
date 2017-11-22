package it.polito.netgroup.selforchestratingservices.declarative_new;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfrastructureEvent {
	public enum EventType
	{
		UPDATE_RESOURCES,
		NONE
	}

	@JsonProperty("eventType")
	EventType eventType;

	@JsonProperty("eventId")
	String eventId;
	@JsonProperty("serviceId")
	String serviceId;
	@JsonProperty("updateNum")
	String updateNum;
}
