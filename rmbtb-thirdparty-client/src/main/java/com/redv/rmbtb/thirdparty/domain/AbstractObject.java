package com.redv.rmbtb.thirdparty.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AbstractObject implements Serializable {

	private static final long serialVersionUID = 2013112601L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
