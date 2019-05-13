package com.ooladehin.awsiot.registry.models;

public enum EventTypeEnum {

	THING("THING_EVENT"), 
	THING_GROUP("THING_GROUP_EVENT"), 
	THING_TYPE("THING_TYPE_EVENT"),
	THING_TYPE_ASSOCIATION("THING_TYPE_ASSOCIATION_EVENT"),
	THING_GROUP_HIERARCHY("THING_GROUP_HIERARCHY_EVENT"), 
	THING_GROUP_MEMBERSHIP("THING_GROUP_MEMBERSHIP_EVENT"),
	CERTIFICATE("CERTIFICATE_EVENT"),
	UNKNOWN_EVENT("UNKNOWN_REGISTRY_EVENT");
	
	private String enumValue;

	EventTypeEnum(String enumValue) {
		this.enumValue = enumValue;
	}

	public String getEnumValue() {
		return this.enumValue;
	}

	public static EventTypeEnum fromEnumValue(String enumValue) {
		for (EventTypeEnum enumTypeEnum : EventTypeEnum.values()) {
			if (enumTypeEnum.enumValue.equalsIgnoreCase(enumValue)) {
				return enumTypeEnum;
			}
		}
		return UNKNOWN_EVENT;
	}

}
