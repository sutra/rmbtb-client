package com.redv.rmbtb.domain;

public enum ApiAccessLevel {

	FULL("full");

	public static ApiAccessLevel toApiAccessLevel(String levelString) {
		for (ApiAccessLevel level : ApiAccessLevel.values()) {
			if (level.getApiAccessLevel().equals(levelString)) {
				return level;
			}
		}

		throw new IllegalArgumentException("Unexpected API Access Level: " + levelString);
	}

	private final String apiAccessLevel;

	private ApiAccessLevel(String apiAccessLevel) {
		this.apiAccessLevel = apiAccessLevel;
	}

	public String getApiAccessLevel() {
		return apiAccessLevel;
	}

}
