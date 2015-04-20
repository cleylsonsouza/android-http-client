package com.httpclientlib.server.connection;

import java.io.IOException;
import java.io.InputStream;

public interface Connectable {

	public InputStream doPost(byte[] encodingBytes) throws IOException;

	public InputStream doGet() throws IOException;
	
	public int getContentLength();

	public void disconnect();

}
