package com.httpclientlib.server.connection;


@SuppressWarnings("deprecation")
public interface ResponseHandler<T> extends org.apache.http.client.ResponseHandler<T> {

    T getReasonPhrase(org.apache.http.HttpResponse httpResponse);

}
