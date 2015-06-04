package com.httpclientlib.server.connection.apache.request;

import com.httpclientlib.server.connection.apache.ApacheHttpClient;
import com.httpclientlib.server.connection.apache.ResponseObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.httpclientlib.server.connection.apache.ApacheHttpClient.TOKEN_HEADER;

@SuppressWarnings("deprecation")
public class PutMultipartRequest {

    @SuppressWarnings({"rawtypes"})
    public static ResponseObject put(String url, Map params, List<byte[]> listImageByteArray,
                                     String accessToken, String entity) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(TOKEN_HEADER, accessToken);
        Iterator iter = params.entrySet().iterator();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
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
