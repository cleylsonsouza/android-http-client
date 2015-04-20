package com.httpclientlib.server.connection.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.StrictHostnameVerifier;

import android.content.Context;
import android.util.Log;

import com.httpclientlib.server.connection.Connectable;
import com.httpclientlib.server.connection.certified.SelfSignedSSL;

public class OpenHttpsConnectionImpl implements Connectable {

	private URL serverURL;
	private HostnameVerifier verifier;
	private SSLContext sslContext;

	private HttpsURLConnection connection;

	public OpenHttpsConnectionImpl(URL serverURL) {
		this.serverURL = serverURL;
		verifier = new StrictHostnameVerifier();

		HttpsURLConnection.setDefaultHostnameVerifier(verifier);
	}

	public OpenHttpsConnectionImpl(URL serverURL, Context context,
			int selfSignedKeyStoreResource, int clientAuthKeyStoreResource) {
		this.serverURL = serverURL;
		verifier = new StrictHostnameVerifier();
		sslContext = SelfSignedSSL.getSSLContextForConnection(context,
				selfSignedKeyStoreResource, clientAuthKeyStoreResource);

		HttpsURLConnection.setDefaultHostnameVerifier(verifier);
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
				.getSocketFactory());
	}

	@Override
	public InputStream doPost(byte[] encodingBytes) throws IOException {

		OutputStream outputStream;
		InputStream inputStream;

		connection = (HttpsURLConnection) serverURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.connect();

		// Informando seguranca do objeto de conexao
		Log.i("CIPHER_SUITE", connection.getCipherSuite());
		for (Certificate b : connection.getServerCertificates()) {
			Log.i("SERVER_CERTIFICATE", b.getPublicKey().getAlgorithm());
		}

		outputStream = connection.getOutputStream();
		outputStream.write(encodingBytes);
		outputStream.flush();
		outputStream.close();

		inputStream = connection.getInputStream();

		return inputStream;
	}

	@Override
	public InputStream doGet() throws IOException {
		return null;
	}

	@Override
	public void disconnect() {
		connection.disconnect();
	}

	@Override
	public int getContentLength() {
		return connection.getContentLength();
	}

}
