package com.redv.rmbtb.domain;

import org.json.simple.JSONObject;

public class Wallets extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Wallet total;

	private final Wallet locked;

	private final Wallet available;

	public Wallets(Wallet total, Wallet locked, Wallet available) {
		this.total = total;
		this.locked = locked;
		this.available = available;
	}

	public Wallets(JSONObject jsonObject) {
		this(
				new Wallet((JSONObject) jsonObject.get("total")),
				new Wallet((JSONObject) jsonObject.get("locked")),
				new Wallet((JSONObject) jsonObject.get("available")));
	}

	/**
	 * @return the total
	 */
	public Wallet getTotal() {
		return total;
	}

	/**
	 * @return the locked
	 */
	public Wallet getLocked() {
		return locked;
	}

	/**
	 * @return the available
	 */
	public Wallet getAvailable() {
		return available;
	}

}
