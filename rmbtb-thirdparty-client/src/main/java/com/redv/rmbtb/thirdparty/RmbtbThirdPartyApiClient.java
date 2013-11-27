package com.redv.rmbtb.thirdparty;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redv.rmbtb.thirdparty.domain.Depth;
import com.redv.rmbtb.thirdparty.domain.Ticker;
import com.redv.rmbtb.thirdparty.domain.TickerResponse;
import com.redv.rmbtb.thirdparty.domain.Trade;

public class RmbtbThirdPartyApiClient implements AutoCloseable {

	private static final URL BASE_URL = newURL("http://www.rmbtb.com/");

	private static final URL API_BASE_URL = newURL(BASE_URL, "api/thirdparty/");

	private static final URI TICKER_URI = toURI(newURL(API_BASE_URL, "ticker/"));
	private static final URI DEPTH_URI = toURI(newURL(API_BASE_URL, "depth/"));
	private static final URI LASTEST_TRADES_URI = toURI(newURL(API_BASE_URL, "lasttrades/"));
	private static final URI ALL_TRADES_URI = toURI(newURL(API_BASE_URL, "trades/"));

	private static URL newURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static URL newURL(URL context, String spec) {
		try {
			return new URL(context, spec);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static URI toURI(URL url) {
		try {
			return url.toURI();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private final CloseableHttpClient httpClient;

	private final ObjectMapper objectMapper;

	public RmbtbThirdPartyApiClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());

		String userAgent = "Mozilla/4.0 (compatible; RMBTB Java client)";
		httpClientBuilder.setUserAgent(userAgent);

		Collection<Header> defaultHeaders = new ArrayList<>();
		defaultHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		httpClientBuilder.setDefaultHeaders(defaultHeaders);

		httpClient = httpClientBuilder.build();

		objectMapper = new ObjectMapper();
	}

	public Ticker getTicker() throws IOException {
		return get(TICKER_URI, TickerResponse.class).getTicker();
	}

	public Depth getDepth() throws IOException {
		return get(DEPTH_URI, Depth.class);
	}

	/**
	 * Returns 80 trades in descending order, starting with the most recent
	 * transaction.
	 * @return 80 trades in descending order.
	 * @throws IOException indicates IO exception.
	 */
	public List<Trade> getLastestTrades() throws IOException {
		return get(LASTEST_TRADES_URI, new TypeReference<List<Trade>>() {
		});
	}

	/**
	 * @see #getAllTrades(long).
	 */
	public List<Trade> getAllTrades() throws IOException {
		return get(ALL_TRADES_URI, new TypeReference<List<Trade>>() {
		});
	}

	/**
	 * Returns 200 trades in ascending order, starting with the transactions
	 * occurring after the transaction ID the specified in the since parameter.
	 * If you do not provide a transaction ID, the first 80 trades are returned.
	 * Transaction IDs start at 0 and do not match your own transaction IDs.
	 * @param since the transaction ID
	 * @return 200 trades in ascending order.
	 * @throws IOException indicates IO exception.
	 */
	public List<Trade> getAllTrades(long since) throws IOException {
		return get(toURI(newURL(ALL_TRADES_URI.toURL(), "?since=" + since)),
				new TypeReference<List<Trade>>() {
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		httpClient.close();
	}

	private <T> T get(URI uri, final Class<T> valueType) throws IOException {
		return get(uri, new ValueReader<T>() {

			@Override
			public T read(InputStream content) throws IOException {
				return objectMapper.readValue(content, valueType);
			}

		});
	}

	private <T> T get(URI uri, final TypeReference<T> valueTypeRef) throws IOException {
		return get(uri, new ValueReader<T>() {
			@Override
			public T read(InputStream content) throws IOException {
				return objectMapper.readValue(content, valueTypeRef);
			}
		});
	}

	private <T> T get(URI uri, ValueReader<T> valueReader) throws IOException {
		final HttpGet get = new HttpGet(uri);

		try (CloseableHttpResponse response = httpClient.execute(get)) {
			final StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				try (InputStream content = response.getEntity().getContent()) {
					return valueReader.read(content);
				}
			} else {
				throw new IOException(statusLine.getReasonPhrase());
			}
		}
	}

	private interface ValueReader<T> {

		T read(InputStream content) throws IOException;

	}

}
