package com.redv.rmbtb.secure.domain;

import org.json.simple.JSONObject;

public class Wallet extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Currency cny;

	private final Currency btc;

	public Wallet(Currency cny, Currency btc) {
		this.cny = cny;
		this.btc = btc;
	}

	public Wallet(JSONObject jsonObject) {
		cny = new Currency((JSONObject) jsonObject.get("CNY"));
		btc = new Currency((JSONObject) jsonObject.get("BTC"));
	}

	/**
	 * @return the cny
	 */
	public Currency getCny() {
		return cny;
	}

	/**
	 * @return the btc
	 */
	public Currency getBtc() {
		return btc;
	}

}
