package com.redv.rmbtb.thirdparty;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		try (RmbtbThirdPartyApiClient client = new RmbtbThirdPartyApiClient()) {
			System.out.println(client.getTicker());
			System.out.println(client.getDepth());
			System.out.println(client.getLastestTrades());
			System.out.println(client.getAllTrades());
			System.out.println(client.getAllTrades(133));
		}
	}

}
