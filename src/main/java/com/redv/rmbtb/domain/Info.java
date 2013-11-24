package com.redv.rmbtb.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.json.simple.JSONObject;

public class Info extends AbstractObject {

	private static final long serialVersionUID = 2013112401L;

	private final Date registered;

	private final String userId;

	private final String depositId;

	private final BigDecimal tradeFeeRate;

	private final BigDecimal apiTradeFeeRate;

	private final ApiAccessLevel apiAccessLevel;

	private final String apiSecretIpLock;

	private final Date apiSecretExpires;

	private final Currency btcWithdrawalLimit;

	private final Currency cnyWithdrawalLimit;

	private final String currBtcDepositAddr;

	private final Wallets wallets;

	public Info(JSONObject jsonObject) {
		registered = parseDate((String) jsonObject.get("registered"));
		userId = (String) jsonObject.get("user_id");
		depositId = (String) jsonObject.get("deposit_id");
		tradeFeeRate = parseRate((String) jsonObject.get("trade_fee"));
		apiTradeFeeRate = parseRate((String) jsonObject.get("api_trade_fee"));
		apiAccessLevel = ApiAccessLevel.toApiAccessLevel((String) jsonObject.get("api_access_level"));
		apiSecretIpLock = (String) jsonObject.get("api_secret_ip_lock");
		apiSecretExpires = parseDate((String) jsonObject.get("api_secret_expires"));
		btcWithdrawalLimit = new Currency((JSONObject) jsonObject.get("btc_withdrawal_limit"));
		cnyWithdrawalLimit = new Currency((JSONObject) jsonObject.get("cny_withdrawal_limit"));
		currBtcDepositAddr = (String) jsonObject.get("curr_btc_deposit_addr");
		wallets = new Wallets((JSONObject) jsonObject.get("wallets"));
	}

	/**
	 * @return the registered
	 */
	public Date getRegistered() {
		return registered;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the depositId
	 */
	public String getDepositId() {
		return depositId;
	}

	/**
	 * @return the tradeFeeRate
	 */
	public BigDecimal getTradeFeeRate() {
		return tradeFeeRate;
	}

	/**
	 * @return the apiTradeFeeRate
	 */
	public BigDecimal getApiTradeFeeRate() {
		return apiTradeFeeRate;
	}

	/**
	 * @return the apiAccessLevel
	 */
	public ApiAccessLevel getApiAccessLevel() {
		return apiAccessLevel;
	}

	/**
	 * @return the apiSecretIpLock
	 */
	public String getApiSecretIpLock() {
		return apiSecretIpLock;
	}

	/**
	 * @return the apiSecretExpires
	 */
	public Date getApiSecretExpires() {
		return apiSecretExpires;
	}

	/**
	 * @return the btcWithdrawalLimit
	 */
	public Object getBtcWithdrawalLimit() {
		return btcWithdrawalLimit;
	}

	/**
	 * @return the cnyWithdrawalLimit
	 */
	public Object getCnyWithdrawalLimit() {
		return cnyWithdrawalLimit;
	}

	/**
	 * @return the currBtcDepositAddr
	 */
	public String getCurrBtcDepositAddr() {
		return currBtcDepositAddr;
	}

	/**
	 * @return the wallets
	 */
	public Wallets getWallets() {
		return wallets;
	}

}
