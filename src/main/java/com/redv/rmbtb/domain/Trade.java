package com.redv.rmbtb.domain;

import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONObject;

public class Trade extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Currency received;

	private final String selling;

	private final Currency sold;

	private final long tid;

	private final long oid;

	private final Currency fee;

	private final Date date;

	private final String buying;

	private final Currency bought;

	public Trade(JSONObject jsonObject) {
		JSONObject receivedJsonObject = (JSONObject) jsonObject.get("received");
		if (receivedJsonObject != null) {
			received = new Currency(receivedJsonObject);
		} else {
			received = null;
		}
		selling = (String) jsonObject.get("selling");
		sold = new Currency(jsonObject);
		tid = NumberUtils.toLong((String) jsonObject.get("tid"));
		oid = NumberUtils.toLong((String) jsonObject.get("oid"));
		JSONObject feeJsonObject = (JSONObject) jsonObject.get("fee");
		if (feeJsonObject != null) {
			fee = new Currency(feeJsonObject);
		} else {
			fee = null;
		}
		date = parseDate((String) jsonObject.get("date"));
		buying = (String) jsonObject.get("buying");
		bought = new Currency((JSONObject) jsonObject.get("bought"));
	}

	/**
	 * @return the received
	 */
	public Currency getReceived() {
		return received;
	}

	/**
	 * @return the selling
	 */
	public String getSelling() {
		return selling;
	}

	/**
	 * @return the sold
	 */
	public Currency getSold() {
		return sold;
	}

	/**
	 * @return the tid
	 */
	public long getTid() {
		return tid;
	}

	/**
	 * @return the oid
	 */
	public long getOid() {
		return oid;
	}

	/**
	 * @return the fee
	 */
	public Currency getFee() {
		return fee;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the buying
	 */
	public String getBuying() {
		return buying;
	}

	/**
	 * @return the bought
	 */
	public Currency getBought() {
		return bought;
	}

}
