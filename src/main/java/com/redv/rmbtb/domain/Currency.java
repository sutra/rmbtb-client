package com.redv.rmbtb.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONObject;

public class Currency extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	public static final BigDecimal BTC_MULTIPLE = BigDecimal.TEN.pow(8);

	public static final BigDecimal CNY_MULTIPLE = BigDecimal.TEN.pow(5);

	private static Long parseValueInt(Object valueIntObject) {
		final Long valueIntLong;
		if (valueIntObject == null) {
			valueIntLong = 0L;
		} else if (valueIntObject instanceof Long) {
			valueIntLong = (Long) valueIntObject;
		} else if (valueIntObject instanceof String) {
			valueIntLong = Long.parseLong((String) valueIntObject);
		} else {
			throw new IllegalArgumentException("Unexpected type of value_int: "
					+ valueIntObject.getClass() + ".");
		}
		return valueIntLong;
	}

	private final String currency;

	private final String display;

	private final String displayShort;

	private final double value;

	private final Long valueInt;

	private final BigDecimal valueAsDecimal;

	private Currency(String currency, String display, String displayShort,
			double value, Long valueInt) {
		this.currency = currency;
		this.display = display;
		this.displayShort = displayShort;
		this.value = value;
		this.valueInt = valueInt;

		if (this.currency == null) {
			valueAsDecimal = BigDecimal.ZERO;
		} else if (this.currency.equals("CNY")) {
			valueAsDecimal = new BigDecimal(valueInt).divide(CNY_MULTIPLE);
		} else if (this.currency.equals("BTC")) {
			valueAsDecimal = new BigDecimal(valueInt).divide(BTC_MULTIPLE);
		} else {
			throw new IllegalArgumentException("Currency " + this.currency
					+ " does not support.");
		}
	}

	public Currency(JSONObject jsonObject) {
		this(
				(String) jsonObject.get("currency"),
				(String) jsonObject.get("display"),
				(String) jsonObject.get("displayShort"),
				NumberUtils.toDouble((String) jsonObject.get("value")),
				parseValueInt(jsonObject.get("value_int")));
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @return the displayShort
	 */
	public String getDisplayShort() {
		return displayShort;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return the valueInt
	 */
	public Long getValueInt() {
		return valueInt;
	}

	public BigDecimal getValueAsDecimal() {
		return valueAsDecimal;
	}

}
