package com.redv.rmbtb.domain;

import java.math.BigDecimal;

import org.json.simple.JSONArray;

public class DepthData extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final BigDecimal rate;

	private final BigDecimal amount;

	public DepthData(JSONArray jsonArray) {
		rate = new BigDecimal((String) jsonArray.get(0));
		amount = new BigDecimal((String) jsonArray.get(1));
	}

	/**
	 * @return the rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

}
