package com.httpclientlib.server.connection;


public interface ResponseHandler<T> extends org.apache.http.client.ResponseHandler<T> {
    T getReasonPhrase(org.apache.http.HttpResponse httpResponse);
}
