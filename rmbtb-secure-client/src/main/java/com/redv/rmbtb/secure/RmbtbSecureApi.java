/**
 * JAVA RMBTB SECURE API CLASS
 *
 *	 版权和限制
 *	 (c)2013人盟比特币公布以下的代码遵循BSD条款。您可以按您的意愿使用或修改，用于个人或商业用途。
 *
 *	 然而您不可以以 "RMBTB"， "人盟比特币"来标识您的程序,也不可以有意或无意示意您的软件或服务是由人盟比特币提供的。
 *
 *	 你可以使用默认的浮点型或者整数型的.
 *	 请仔细阅读下面的设置和安全建议
 *
 *	 使用示例
 * This class relies on the Apache commons codec and the json-simple modules.
 *
 * RmbtbSecureApi r = new RmbtbSecureApi("pubkey", "passphrase", "BTCCNY");
 * Sytem.out.print(r.addOrder("bid", 1.1, 600.0));
 *
 * Full documentation is at https://www.rmbtb.com/help-secureapi-en/
 *
 */
package com.redv.rmbtb.secure;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RMBTB Secure API class. Set the constants here.
 */
interface RmbtbApi {

	public static final String urlBaseLoc = "https://www.rmbtb.com/api/secure/";

	/**
	 * Gets useful information about your account Authentication required.
	 *
	 * @return JSONObject account details
	 */
	public JSONObject getInfo() throws Exception;

	/**
	 * Gets your wallet balances Authentication required.
	 *
	 * @return JSONObject account balances
	 */
	public JSONObject getFunds() throws Exception;

	/**
	 * Gets your wallet balances Authentication required.
	 *
	 * @return JSONObject account balances
	 */
	public JSONObject ticker() throws Exception;

	/**
	 * Gets useful market information
	 *
	 * @return JSONObject market info
	 */
	public JSONArray getOrders() throws Exception;

	/**
	 * Adds an order -- double params
	 *
	 * @param String
	 *            type bid | ask
	 * @param double amount the amount to buy/sell
	 * @param souble
	 *            price the price to buy/sell at
	 * @return JSONObject containing the Order ID
	 */
	public JSONObject addOrder(String type, double amount, double price)
			throws Exception;

	/**
	 * Adds an order -- long integer params
	 *
	 * @param String
	 *            type bid | ask
	 * @param long amount the amount to buy/sell
	 * @param long price the price to buy/sell at
	 * @return JSONObject containing the Order ID
	 */
	public JSONObject addOrder(String type, long amount, long price)
			throws Exception;

	/**
	 * Cancels an order
	 *
	 * @param long orderId the Order ID to cancel
	 * @return JSONObject containing the Order ID
	 */
	public JSONObject cancelOrder(long orderId) throws Exception;

	/**
	 * fetches info about an order
	 *
	 * @param long orderId the Order ID to cancel
	 * @return JSONObject of info
	 */
	public JSONObject fetchOrder(long orderId) throws Exception;

	/**
	 * Gets your 50 most recent trades
	 *
	 * @return JSONArray of trades
	 */
	public JSONArray getTrades() throws Exception;

	/**
	 * returns the most recent market transactions
	 *
	 * @return JSONArray of trades
	 */
	public JSONArray lastTrades() throws Exception;

	/**
	 * returns the market depth
	 *
	 * @return JSONObject showing bids and asks
	 */
	public JSONObject getDepth() throws Exception;

}

public class RmbtbSecureApi implements RmbtbApi {

	private final Logger log = LoggerFactory.getLogger(RmbtbSecureApi.class);

	private final File rmbtbSecretData;

	private String currPair;
	private String pubkey;
	private String passphrase;
	private String secret;
	private Calendar expires;

	public RmbtbSecureApi(String pubkey, String passphrase, File rmbtbSecretData) {
		this(pubkey, passphrase, rmbtbSecretData, "BTCCNY");
	}

	public RmbtbSecureApi(String pubkey, String passphrase, File rmbtbSecretData, String currPair) {

		log.debug("pubKey: {}, passphrase: {}", pubkey, passphrase);

		this.currPair = currPair;
		this.pubkey = pubkey;
		this.passphrase = passphrase;
		this.rmbtbSecretData = rmbtbSecretData;

		secret = null;
		expires = Calendar.getInstance();

	}

	@Override
	public JSONObject getInfo() throws Exception {
		return (JSONObject) doRequest("getinfo", "GET", true);
	}

	@Override
	public JSONObject getFunds() throws Exception {
		return (JSONObject) doRequest("wallets", "GET", true);
	}

	@Override
	public JSONObject ticker() throws Exception {
		return (JSONObject) doRequest("ticker", "GET", false);
	}

	@Override
	public JSONArray getOrders() throws Exception {
		return (JSONArray) doRequest("orders", "GET", true);
	}

	@Override
	public JSONObject addOrder(String type, double amount, double price)
			throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", type == "ask" ? "ask" : "bid");
		params.put("amount",
				new DecimalFormat("00000000.00000000").format(amount));
		params.put("price",
				new DecimalFormat("00000000.00000000").format(price));

		return (JSONObject) doRequest("order/add", "POST", true, params);
	}

	@Override
	public JSONObject addOrder(String type, long amount, long price)
			throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", type == "ask" ? "ask" : "bid");
		params.put("amount_int", String.valueOf(amount));
		params.put("price_int", String.valueOf(price));

		return (JSONObject) doRequest("order/add", "POST", true, params);
	}

	@Override
	public JSONObject cancelOrder(long orderId) throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("oid", String.valueOf(orderId));

		return (JSONObject) doRequest("order/cancel", "POST", true, params);
	}

	@Override
	public JSONObject fetchOrder(long orderId) throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("oid", String.valueOf(orderId));

		return (JSONObject) doRequest("order/fetch", "GET", true, params);
	}

	@Override
	public JSONArray getTrades() throws Exception {
		return (JSONArray) doRequest("trades/mine", "GET", true);
	}

	@Override
	public JSONArray lastTrades() throws Exception {
		return (JSONArray) doRequest("trades/all", "GET", false);
	}

	@Override
	public JSONObject getDepth() throws Exception {
		return (JSONObject) doRequest("depth", "GET", false);
	}

	private Object doRequest(String api, String httpMethod, boolean auth)
			throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		return doRequest(api, httpMethod, auth, params);
	}

	private Object doRequest(String api, String httpMethod, boolean auth,
			HashMap<String, String> params) throws Exception {

		if (auth) {
			Calendar maxAge = Calendar.getInstance();
			maxAge.add(Calendar.MINUTE, -1);
			if (secret == null || expires.before(maxAge)) {
				loadSecret();
			}
//			params.put("nonce", String.valueOf(System.nanoTime()) + "000");
			params.put("nonce", String.valueOf(System.currentTimeMillis()));
		}

		String paramStr = "";
		int i = 0;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			paramStr += entry.getKey() + "="
					+ URLEncoder.encode(entry.getValue(), "UTF-8");
			i++;
			if (i < params.size()) {
				paramStr += "&";
			}
		}

		HashMap<String, String> headers = new HashMap<String, String>();

		if (auth) {
			headers.put("Rest-Key", pubkey);
			headers.put("Rest-Sign", getRequestSig(paramStr));
		}

		if (log.isDebugEnabled()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				log.debug("header: {} = {}", entry.getKey(), entry.getValue());
			}

			log.debug("paramString: {}", paramStr);
		}

		Object data =  doHttpRequest(api, httpMethod, paramStr, headers);

		return data;
	}

	private void loadSecret() throws UnsupportedEncodingException, IOException,
			ParseException {

		File dat = rmbtbSecretData;

		// Load secret from file
		if (dat.exists()) {

			log.debug("Secret file exists: {}", dat.getPath());

			expires.setTimeInMillis(dat.lastModified());
			expires.add(Calendar.HOUR, 2);

			Calendar maxAge = Calendar.getInstance();
			maxAge.add(Calendar.MINUTE, -1);

			if (expires.after(maxAge)) {
				log.debug("The secret does not expire.");

				StringBuilder datContents = new StringBuilder(
						(int) dat.length());
				try (Scanner scanner = new Scanner(dat)) {
					while (scanner.hasNextLine()) {
						datContents.append(scanner.nextLine());
					}
				}

				secret = datContents.toString();
				expires = Calendar.getInstance();
				expires.add(Calendar.HOUR, 2);

				if (log.isDebugEnabled()) {
					log.debug("Read secret from file, the secret is {}, length is {}.", secret, secret.length());
				}

				return;
			}
		}

		log.debug("Getting secret by API calling...");

		// Need to fetch a new secret
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Rest-Key", pubkey);

		String params = "api_passphrase="
				+ URLEncoder.encode(passphrase, "UTF-8");

		JSONObject data = (JSONObject) doHttpRequest("getsecret", "POST", params, headers);

		secret = (String) data.get("secret");

		if (log.isDebugEnabled()) {
			log.debug("Got secret by API calling, the secrit is {}, length is {}.", secret, secret.length());
		}

		expires = Calendar.getInstance();
		expires.add(Calendar.HOUR, 2);

		try (FileWriter fw = new FileWriter(dat)) {
			fw.write(secret);
		}
	}

	// Perform the request
	private Object doHttpRequest(String api, String httpMethod,
			String params, HashMap<String, String> headers) throws IOException,
			ProtocolException, ParseException {

		api = "GET".equals(httpMethod) ? (api += "?" + params) : api;
		URL uObj = new URL(urlBaseLoc + currPair + "/" + api);

		HttpsURLConnection conn = (HttpsURLConnection) uObj.openConnection();
		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; RMBTB Java client)");

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			conn.setRequestProperty(entry.getKey(), entry.getValue());
		}

		if ("POST".equals(httpMethod)) {
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(params);
			out.flush();
			out.close();

		} else {
			conn.setRequestMethod("GET");
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inLine;
		StringBuffer response = new StringBuffer();

		while ((inLine = in.readLine()) != null) {
			response.append(inLine);
		}
		in.close();

		JSONParser parser = new JSONParser();

		JSONObject respJSON = (JSONObject) parser.parse(response.toString());

		if (respJSON.get("error").getClass().getName() != "java.lang.Boolean") {
			String strErr = (String) respJSON.get("error");
			throw new RuntimeException(strErr);
		}
		Object data = respJSON.get("data");

		return data;

	}

	// Signs using HMAC
	private String getRequestSig(String data) throws NoSuchAlgorithmException,
			InvalidKeyException {

		log.debug("getRequestSig for {} with {}", data, secret);
		Mac mac = Mac.getInstance("HmacSHA512");
		SecretKeySpec secret_spec = new SecretKeySpec(secret.getBytes(),
				"HmacSHA512");
		mac.init(secret_spec);

		return Base64.encodeBase64String(mac.doFinal(data.getBytes()))
				.toString();
	}
}
