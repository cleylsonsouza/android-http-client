package com.httpclientlib.server.connection.certified;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

public class SelfSignedSSL {

	public static SSLContext getSSLContextForConnection(Context context,
			int selfSignedKeyStoreResource, int clientAuthKeyStoreResource) {

		SSLContext selfSignedSslContext = null;

		try {
			// KeyStore p/ identificacao do lado servidor
			KeyStore selfSignedKeyStore = KeyStore.getInstance("BKS");
			selfSignedKeyStore.load(
					context.getResources().openRawResource(
							selfSignedKeyStoreResource),
					"genericPassword".toCharArray());

			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(selfSignedKeyStore);

			// KeyStore p/ identificacao do lado cliente
			KeyStore clientAuthKeyStore = KeyStore.getInstance("BKS");
			clientAuthKeyStore.load(
					context.getResources().openRawResource(
							clientAuthKeyStoreResource),
					"genericPassword".toCharArray());

			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(clientAuthKeyStore,
					"genericPassword".toCharArray());

			// inicializacao do SSLContext com as KeyStores
			selfSignedSslContext = SSLContext.getInstance("TLS");
			selfSignedSslContext.init(keyManagerFactory.getKeyManagers(),
					trustManagerFactory.getTrustManagers(), new SecureRandom());

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}

		return selfSignedSslContext;

	}

}
