package com.redv.rmbtb.secure.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Depth extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Logger log = LoggerFactory.getLogger(Depth.class);

	private final List<Data> asks;

	private final List<Data> bids;

	public Depth(JSONObject jsonObject) {
		log.debug("Depth: {}", jsonObject);

		JSONArray asksJsonArray = (JSONArray) jsonObject.get("asks");
		JSONArray bidsJsonArray = (JSONArray) jsonObject.get("bids");

		asks = new ArrayList<>(asksJsonArray.size());
		bids = new ArrayList<>(bidsJsonArray.size());

		for (Object object : asksJsonArray) {
			asks.add(new Data((JSONArray) object));
		}

		for (Object object : bidsJsonArray) {
			bids.add(new Data((JSONArray) object));
		}
	}

	/**
	 * @return the asks
	 */
	public List<Data> getAsks() {
		return asks;
	}

	/**
	 * @return the bids
	 */
	public List<Data> getBids() {
		return bids;
	}

	public class Data extends AbstractObject {

		private static final long serialVersionUID = 2013112401L;

		private final BigDecimal rate;

		private final BigDecimal amount;

		public Data(JSONArray jsonArray) {
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

}
