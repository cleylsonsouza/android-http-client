package com.httpclientlib.server.connection.impl;

import com.httpclientlib.server.connection.Connectable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenHttpConnectionImpl implements Connectable {

    private URL serverURL;
    private HttpURLConnection connection;

    public OpenHttpConnectionImpl(URL serverURL) {
        this.serverURL = serverURL;
    }

    @Override
    public InputStream doPost(byte[] encodingBytes) throws IOException {

        OutputStream outputStream;
        InputStream inputStream;

        connection = (HttpURLConnection) serverURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        outputStream = connection.getOutputStream();
        outputStream.write(encodingBytes);
        outputStream.flush();
        outputStream.close();

        inputStream = connection.getInputStream();

        return inputStream;
    }

    @Override
    public InputStream doGet() throws IOException {
        InputStream inputStream;

        connection = (HttpURLConnection) serverURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.setDoInput(true);
        connection.connect();

        inputStream = connection.getInputStream();

        return inputStream;
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
