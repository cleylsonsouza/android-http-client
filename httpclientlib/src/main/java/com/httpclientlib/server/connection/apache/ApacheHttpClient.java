package com.httpclientlib.server.connection.apache;

import com.httpclientlib.server.connection.ResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApacheHttpClient {

    public static final String TOKEN_HEADER = "auth_token";
    public static final String EMAIL_HEADER = "X-Auth-Email";

    static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        public String handleResponse(final HttpResponse response)
                throws IOException {
            HttpEntity entity = response.getEntity();
            return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
        }

        public String getReasonPhrase(final HttpResponse response) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                return statusLine.getReasonPhrase();
            }
            return "";
        }
    };

    public static InputStream requestGetForInputStream(String url) {
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

    public static ResponseObject requestGetForRESTContent(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            HttpResponse response = httpclient.execute(httpget);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseObject requestGetForRESTContent(String url,
                                                          String accessToken) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader(TOKEN_HEADER, accessToken);
        httpget.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            HttpResponse response = httpclient.execute(httpget);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject requestPOST(String url, Map params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = pairs.getValue().toString();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPost.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            response = httpclient.execute(httpPost);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject requestPOST(String url, Map params,
                                             String accessToken) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        httpPost.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = pairs.getValue().toString();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPost.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            response = httpclient.execute(httpPost);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject requestPUT(String url, Map params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        HttpResponse response = null;
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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
            response = httpclient.execute(httpPut);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject requestPUT(String url, Map params,
                                            String accessToken) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        HttpResponse response = null;
        httpPut.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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
            response = httpclient.execute(httpPut);
            return new ResponseObject(response.getStatusLine().getStatusCode(),
                    responseHandler.handleResponse(response),
                    responseHandler.getReasonPhrase(response));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static String requestPOSTForImagesAttributes(String url, Map params,
                                                        List<byte[]> listImageByteArray, String entity) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = (String) pairs.getValue();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpost.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
                HTTP.UTF_8);

        for (int index = 0; index < nameValuePairs.size(); index++) {
            builder.addPart(nameValuePairs.get(index).getName(),
                    new StringBody(nameValuePairs.get(index).getValue(),
                            contentType));
        }
        for (int i = 0; i < listImageByteArray.size(); i++) {
            InputStreamBody inputStreamBody = new InputStreamBody(
                    new ByteArrayInputStream(listImageByteArray.get(i)),
                    "uploadImage" + i + ".jpg");
            builder.addPart(entity + "[images_attributes][" + i + "][name]",
                    inputStreamBody);
        }

        final HttpEntity httpEntity = builder.build();
        httpost.setEntity(httpEntity);

        return httpclient.execute(httpost, responseHandler);
    }

    @SuppressWarnings({"rawtypes"})
    public static String requestPOSTForImagesAttributes(String url, Map params,
                                                        List<byte[]> listImageByteArray, String accessToken, String entity)
            throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = (String) pairs.getValue();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPost.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
                HTTP.UTF_8);

        for (int index = 0; index < nameValuePairs.size(); index++) {
            builder.addPart(nameValuePairs.get(index).getName(),
                    new StringBody(nameValuePairs.get(index).getValue(),
                            contentType));
        }
        for (int i = 0; i < listImageByteArray.size(); i++) {
            InputStreamBody inputStreamBody = new InputStreamBody(
                    new ByteArrayInputStream(listImageByteArray.get(i)),
                    "uploadImage" + i + ".jpg");
            builder.addPart(entity + "[images_attributes][" + i + "][name]",
                    inputStreamBody);
        }

        final HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);

        return httpclient.execute(httpPost, responseHandler);
    }

    @SuppressWarnings({"rawtypes"})
    public static String requestPUTForImagesAttributes(String url, Map params,
                                                       List<byte[]> listImageByteArray, String accessToken, String entity)
            throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = (String) pairs.getValue();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPut.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
                HTTP.UTF_8);

        for (int index = 0; index < nameValuePairs.size(); index++) {
            builder.addPart(nameValuePairs.get(index).getName(),
                    new StringBody(nameValuePairs.get(index).getValue(),
                            contentType));
        }
        for (int i = 0; i < listImageByteArray.size(); i++) {
            InputStreamBody inputStreamBody = new InputStreamBody(
                    new ByteArrayInputStream(listImageByteArray.get(i)),
                    "uploadImage" + i + ".jpg");
            builder.addPart(entity + "[images_attributes][" + i + "][name]",
                    inputStreamBody);
        }

        final HttpEntity httpEntity = builder.build();
        httpPut.setEntity(httpEntity);

        return httpclient.execute(httpPut, responseHandler);
    }

    @SuppressWarnings({"rawtypes"})
    public static String requestPUTForAprovedImagesAttributes(String url,
                                                              Map params, List<byte[]> listImageByteArray, String accessToken,
                                                              String entity) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();

            String key = (String) pairs.getKey();
            String value = (String) pairs.getValue();

            nameValuePairs.add(new BasicNameValuePair(key, value));
        }

        httpPut.getParams().setParameter(
                CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
                HTTP.UTF_8);

        for (int index = 0; index < nameValuePairs.size(); index++) {
            builder.addPart(nameValuePairs.get(index).getName(),
                    new StringBody(nameValuePairs.get(index).getValue(),
                            contentType));
        }
        for (int i = 0; i < listImageByteArray.size(); i++) {
            InputStreamBody inputStreamBody = new InputStreamBody(
                    new ByteArrayInputStream(listImageByteArray.get(i)),
                    "uploadImage" + i + ".jpg");
            builder.addPart(entity + "[aproved_images_attributes][" + i
                    + "][name]", inputStreamBody);
        }

        final HttpEntity httpEntity = builder.build();
        httpPut.setEntity(httpEntity);

        return httpclient.execute(httpPut, responseHandler);
    }
}
