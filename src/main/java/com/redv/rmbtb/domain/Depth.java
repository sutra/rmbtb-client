package com.redv.rmbtb.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Depth extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Logger log = LoggerFactory.getLogger(Depth.class);

	private final List<DepthData> asks;

	private final List<DepthData> bids;

	public Depth(JSONObject jsonObject) {
		log.debug("Depth: {}", jsonObject);

		JSONArray asksJsonArray = (JSONArray) jsonObject.get("asks");
		JSONArray bidsJsonArray = (JSONArray) jsonObject.get("bids");

		asks = new ArrayList<>(asksJsonArray.size());
		bids = new ArrayList<>(bidsJsonArray.size());

		for (Object object : asksJsonArray) {
			asks.add(new DepthData((JSONArray) object));
		}

		for (Object object : bidsJsonArray) {
			bids.add(new DepthData((JSONArray) object));
		}
	}

	/**
	 * @return the asks
	 */
	public List<DepthData> getAsks() {
		return asks;
	}

	/**
	 * @return the bids
	 */
	public List<DepthData> getBids() {
		return bids;
	}

}
