package com.httpclientlib.server.connection.apache.request;

import com.httpclientlib.server.connection.apache.ApacheHttpClient;
import com.httpclientlib.server.connection.apache.ResponseObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static com.httpclientlib.server.connection.apache.ApacheHttpClient.TOKEN_HEADER;

@SuppressWarnings("deprecation")
public class PutRequest {

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject put(String url, Map params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = pairs.getValue().toString();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPut.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httpPut);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    ApacheHttpClient.responseHandler.handleResponse(response),
                    ApacheHttpClient.responseHandler.getReasonPhrase(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject put(String url, Map params, String accessToken) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = pairs.getValue().toString();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPut.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httpPut);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    ApacheHttpClient.responseHandler.handleResponse(response),
                    ApacheHttpClient.responseHandler.getReasonPhrase(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
