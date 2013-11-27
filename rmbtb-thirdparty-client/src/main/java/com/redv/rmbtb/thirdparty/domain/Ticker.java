package com.redv.rmbtb.thirdparty.domain;

import java.math.BigDecimal;

public class Ticker extends AbstractObject {

	private static final long serialVersionUID = 2013112601L;

	private BigDecimal high;

	private BigDecimal low;

	private BigDecimal buy;

	private BigDecimal sell;

	private BigDecimal last;

	private BigDecimal vol;

	/**
	 * @return the high
	 */
	public BigDecimal getHigh() {
		return high;
	}

	/**
	 * @return the low
	 */
	public BigDecimal getLow() {
		return low;
	}

	/**
	 * @return the buy
	 */
	public BigDecimal getBuy() {
		return buy;
	}

	/**
	 * @return the sell
	 */
	public BigDecimal getSell() {
		return sell;
	}

	/**
	 * @return the last
	 */
	public BigDecimal getLast() {
		return last;
	}

	/**
	 * @return the vol
	 */
	public BigDecimal getVol() {
		return vol;
	}

}
