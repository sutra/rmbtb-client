package com.redv.rmbtb.thirdparty.domain;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Depth extends AbstractObject {

	private static final long serialVersionUID = 2013112501L;

	private List<Data> asks;

	private List<Data> bids;

	public List<Data> getAsks() {
		return asks;
	}

	public List<Data> getBids() {
		return bids;
	}

	public void setAsks(final BigDecimal[][] asks) {
		this.asks = new ArrayList<>(asks.length);
		for (final BigDecimal[] ask : asks) {
			this.asks.add(new Data(ask[0], ask[1]));
		}
	}

	public void setBids(final BigDecimal[][] bids) {
		this.bids = new ArrayList<>(bids.length);
		for (final BigDecimal[] bid : bids) {
			this.bids.add(new Data(bid[0], bid[1]));
		}
	}

	public class Data extends AbstractObject {

		private static final long serialVersionUID = 2013112501L;

		private final BigDecimal rate;

		private final BigDecimal amount;

		public Data(BigDecimal rate, BigDecimal amount) {
			this.rate = rate;
			this.amount = amount;
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