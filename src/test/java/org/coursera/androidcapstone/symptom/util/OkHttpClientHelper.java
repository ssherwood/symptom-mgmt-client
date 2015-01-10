package org.coursera.androidcapstone.symptom.util;

import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.squareup.okhttp.OkHttpClient;

public class OkHttpClientHelper {

	private static final String KEYSTORE_PASSWORD = "changeit";

	public static OkHttpClient getCustomSslOkHttpClient() throws Exception {
		KeyStore keyStore = readKeyStore();

		SSLContext sslContext = SSLContext.getInstance("SSL");

		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(keyStore);

		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());

		sslContext.init(keyManagerFactory.getKeyManagers(),
				trustManagerFactory.getTrustManagers(), new SecureRandom());

		OkHttpClient client = new OkHttpClient();
		client.setSslSocketFactory(sslContext.getSocketFactory());

		return client;
	}

	private static KeyStore readKeyStore() throws Exception {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		java.io.FileInputStream fis = null;
		try {
			fis = new java.io.FileInputStream(
					"D:/workspaces/coursera/SymptomManagement/src/main/java/keystore.jks");
			ks.load(fis, KEYSTORE_PASSWORD.toCharArray());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return ks;
	}
}
