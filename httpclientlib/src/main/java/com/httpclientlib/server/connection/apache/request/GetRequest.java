package com.httpclientlib.server.connection.apache.request;

import com.httpclientlib.server.connection.apache.ApacheHttpClient;
import com.httpclientlib.server.connection.apache.ResponseObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.InputStream;

import static com.httpclientlib.server.connection.apache.ApacheHttpClient.TOKEN_HEADER;

@SuppressWarnings("deprecation")
public class GetRequest {

    public static ResponseObject get(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            HttpResponse response = httpclient.execute(httpget);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    ApacheHttpClient.responseHandler.handleResponse(response),
                    ApacheHttpClient.responseHandler.getReasonPhrase(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseObject get(String url, String accessToken) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader(TOKEN_HEADER, accessToken);
        httpget.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            HttpResponse response = httpclient.execute(httpget);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    ApacheHttpClient.responseHandler.handleResponse(response),
                    ApacheHttpClient.responseHandler.getReasonPhrase(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getStream(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            HttpResponse response = httpclient.execute(httpget);
            return response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
