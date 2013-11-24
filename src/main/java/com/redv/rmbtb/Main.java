package com.redv.rmbtb;

import java.io.File;
import java.util.List;

import com.redv.rmbtb.domain.Depth;
import com.redv.rmbtb.domain.Info;
import com.redv.rmbtb.domain.Order;
import com.redv.rmbtb.domain.Ticker;
import com.redv.rmbtb.domain.Trade;
import com.redv.rmbtb.domain.Wallets;

public class Main {

	public static void main(String[] args) throws Exception {
		final String pubkey = args[0];
		final String passphrase = args[1];
		final File rmbtbSecretData = args.length >= 3 ? new File(args[2]) : new File("rmbtb-secret-data.dat");

		RmbtbSecureApiClient client = new RmbtbSecureApiClient(pubkey, passphrase, rmbtbSecretData);

		Info info = client.getInfo();
		System.out.println(info);

		Wallets wallets = client.getFunds();
		System.out.println(wallets);

		Ticker ticker = client.ticker();
		System.out.println(ticker);

		// Buy 1 BTC for 0.01CNY/BTC
		// long bidOrderId = client.addOrder(Type.BID, new BigDecimal("1"), new BigDecimal("0.01"));
		// System.out.println("Placed buy order: " + bidOrderId);

		// Sell 0.001 BTC for 10000CNY/BTC
		// long askOrderId = client.addOrder(Type.ASK, new BigDecimal("0.0001"), new BigDecimal("10000"));
		// System.out.println("Placed sell order: " + askOrderId);

		// Cancel order
		// long cancelledOrderId = client.cancelOrder(182514);
		// System.out.println("Cancelled order: " + cancelledOrderId);

		List<Order> orders = client.getOrders();
		System.out.println(orders);

		if (orders.size() > 0) {
			long oid = orders.get(0).getOid();
			Order order = client.fetchOrder(oid);
			System.out.println(order);
		}

		List<Trade> mine = client.getTrades();
		System.out.println(mine);

		List<Trade> all = client.lastTrades();
		System.out.println(all);

		Depth depth = client.getDepth();
		System.out.println(depth);
	}

}
