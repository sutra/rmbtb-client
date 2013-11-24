package com.redv.rmbtb.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Order extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final long oid;

	private final Date date;

	private final String buying;

	private final String selling;

	private final Type type;

	private final Currency initialSellingAmount;

	private final Currency initialBuyingAmount;

	private final Currency remainingSellingAmount;

	private final Currency remainingBuyingAmount;

	private final BigDecimal price;

	private final Status status;

	private final BigDecimal feeRate;

	private final List<Trade> trades;

	public Order(JSONObject jsonObject) {
		Object oidObject = jsonObject.get("oid");
		if (oidObject instanceof Long) {
			oid = (Long) oidObject;
		} else {
			oid = NumberUtils.toLong((String) oidObject);
		}
		date = parseDate((String) jsonObject.get("date"));
		buying = (String) jsonObject.get("buying");
		selling = (String) jsonObject.get("selling");
		type = Type.toType((String) jsonObject.get("type"));
		initialSellingAmount = new Currency((JSONObject) jsonObject.get("initial_selling_amount"));
		initialBuyingAmount = new Currency((JSONObject) jsonObject.get("initial_buying_amount"));
		remainingSellingAmount = new Currency((JSONObject) jsonObject.get("remaining_selling_amount"));
		remainingBuyingAmount = new Currency((JSONObject) jsonObject.get("remaining_buying_amount"));
		price = new BigDecimal(((Number) jsonObject.get("price")).doubleValue());
		status = Status.toStatus((String) jsonObject.get("status"));
		feeRate = parseRate((String) jsonObject.get("fee"));

		Object tradesObject = jsonObject.get("trades");
		if (tradesObject != null) {
			JSONArray tradesArray = (JSONArray) tradesObject;
			trades = new ArrayList<>(tradesArray.size());
			for (Object tradeObject : tradesArray) {
				Trade trade = new Trade((JSONObject) tradeObject);
				trades.add(trade);
			}
		} else {
			trades = Collections.emptyList();
		}
	}

	/**
	 * @return the oid
	 */
	public long getOid() {
		return oid;
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
	 * @return the selling
	 */
	public String getSelling() {
		return selling;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the initialSellingAmount
	 */
	public Currency getInitialSellingAmount() {
		return initialSellingAmount;
	}

	/**
	 * @return the initialBuyingAmount
	 */
	public Currency getInitialBuyingAmount() {
		return initialBuyingAmount;
	}

	/**
	 * @return the remainingSellingAmount
	 */
	public Currency getRemainingSellingAmount() {
		return remainingSellingAmount;
	}

	/**
	 * @return the remainingBuyingAmount
	 */
	public Currency getRemainingBuyingAmount() {
		return remainingBuyingAmount;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return the feeRate
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}

}
