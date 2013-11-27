package com.redv.rmbtb.secure.domain;

public enum Status {

	OPEN("open"), CLOSED("closed");

	public static Status toStatus(String statusString) {
		for (Status status : Status.values()) {
			if (status.getStatus().equals(statusString)) {
				return status;
			}
		}

		throw new IllegalArgumentException("Unexpected status: " + statusString);
	}

	private final String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
