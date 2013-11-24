package com.redv.rmbtb.domain;

public enum Type {

	BID("bid"), ASK("ask"), LIMIT("limit");

	public static Type toType(String typeString) {
		for (Type type : Type.values()) {
			if (type.getType().equals(typeString)) {
				return type;
			}
		}

		throw new IllegalArgumentException("Unexpected type: " + typeString);
	}

	private final String type;

	Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}


}