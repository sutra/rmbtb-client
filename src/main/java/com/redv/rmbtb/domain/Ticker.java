package com.redv.rmbtb.domain;

import org.json.simple.JSONObject;

public class Ticker extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Currency high;

	private final Currency low;

	private final Currency avg;

	private final Currency vwap;

	private final Currency vol;

	private final Currency last;

	private final Currency buy;

	private final Currency sell;

	public Ticker(JSONObject jsonObject) {
		high = new Currency((JSONObject) jsonObject.get("high"));
		low = new Currency((JSONObject) jsonObject.get("low"));
		avg = new Currency((JSONObject) jsonObject.get("avg"));
		vwap = new Currency((JSONObject) jsonObject.get("vwap"));
		vol = new Currency((JSONObject) jsonObject.get("vol"));
		last = new Currency((JSONObject) jsonObject.get("last"));
		buy = new Currency((JSONObject) jsonObject.get("buy"));
		sell = new Currency((JSONObject) jsonObject.get("sell"));
	}

	/**
	 * @return the high
	 */
	public Currency getHigh() {
		return high;
	}

	/**
	 * @return the low
	 */
	public Currency getLow() {
		return low;
	}

	/**
	 * @return the avg
	 */
	public Currency getAvg() {
		return avg;
	}

	/**
	 * @return the vwap
	 */
	public Currency getVwap() {
		return vwap;
	}

	/**
	 * @return the vol
	 */
	public Currency getVol() {
		return vol;
	}

	/**
	 * @return the last
	 */
	public Currency getLast() {
		return last;
	}

	/**
	 * @return the buy
	 */
	public Currency getBuy() {
		return buy;
	}

	/**
	 * @return the sell
	 */
	public Currency getSell() {
		return sell;
	}

}
