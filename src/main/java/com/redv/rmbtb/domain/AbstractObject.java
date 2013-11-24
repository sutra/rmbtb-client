package com.redv.rmbtb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;

abstract class AbstractObject implements Serializable {

	private static final long serialVersionUID = 2013112401L;

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Date parseDate(String dateString) {
		try {
			return DateUtils.parseDateStrictly(dateString, DATE_PATTERN);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static BigDecimal parseRate(String percentString) {
		String percent = percentString.substring(0, percentString.length() - 1);
		return new BigDecimal(percent).divide(new BigDecimal(100));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
