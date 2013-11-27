package com.redv.rmbtb.secure;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.redv.rmbtb.secure.domain.Currency;
import com.redv.rmbtb.secure.domain.Depth;
import com.redv.rmbtb.secure.domain.Info;
import com.redv.rmbtb.secure.domain.Order;
import com.redv.rmbtb.secure.domain.Ticker;
import com.redv.rmbtb.secure.domain.Trade;
import com.redv.rmbtb.secure.domain.Type;
import com.redv.rmbtb.secure.domain.Wallets;

public class RmbtbSecureApiClient {

	private RmbtbSecureApi api;

	public RmbtbSecureApiClient(String pubkey, String passphrase, File rmbtbSecretData) {
		this(pubkey, passphrase, rmbtbSecretData, "BTCCNY");
	}

	public RmbtbSecureApiClient(String pubkey, String passphrase,
			File rmbtbSecretData, String currPair) {
		api = new RmbtbSecureApi(pubkey, passphrase, rmbtbSecretData, currPair);
	}

	public Info getInfo() throws RmbtbSecureApiException {
		try {
			return new Info(api.getInfo());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}

	}

	public Wallets getFunds() throws RmbtbSecureApiException {
		try {
			return new Wallets(api.getFunds());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public Ticker ticker() throws RmbtbSecureApiException {
		try {
			return new Ticker(api.ticker());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public List<Order> getOrders() throws RmbtbSecureApiException {
		JSONArray jsonArray;
		try {
			jsonArray = api.getOrders();
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
		ArrayList<Order> orders = new ArrayList<>(jsonArray.size());
		for (Object object : jsonArray) {
			orders.add(new Order((JSONObject) object));
		}
		return orders;
	}

	public long addOrder(Type type, BigDecimal amount, BigDecimal price)
			throws RmbtbSecureApiException {
		BigDecimal actualAmount = amount.multiply(Currency.BTC_MULTIPLE);
		BigDecimal actualPrice = price.multiply(Currency.CNY_MULTIPLE);
		JSONObject jsonObject;
		try {
			jsonObject = api.addOrder(type.getType(),
					actualAmount.longValue(), actualPrice.longValue());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
		return (Long) jsonObject.get("oid");
	}

	public long cancelOrder(long orderId) throws RmbtbSecureApiException {
		try {
			return (Long) api.cancelOrder(orderId).get("oid");
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public Order fetchOrder(long orderId) throws RmbtbSecureApiException {
		try {
			return new Order(api.fetchOrder(orderId));
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public List<Trade> getTrades() throws RmbtbSecureApiException {
		try {
			return parseTrades(api.getTrades());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public List<Trade> lastTrades() throws RmbtbSecureApiException {
		try {
			return parseTrades(api.lastTrades());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	public Depth getDepth() throws RmbtbSecureApiException {
		try {
			return new Depth(api.getDepth());
		} catch (Exception e) {
			throw new RmbtbSecureApiException(e);
		}
	}

	private List<Trade> parseTrades(JSONArray jsonArray) {
		ArrayList<Trade> trades = new ArrayList<>(jsonArray.size());
		for (Object object : jsonArray) {
			trades.add(new Trade((JSONObject) object));
		}
		return trades;
	}

}
