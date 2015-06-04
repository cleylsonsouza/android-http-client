package com.httpclientlib.server.connection.apache;

import com.httpclientlib.server.connection.ResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@SuppressWarnings("deprecation")
public class ApacheHttpClient {

    public static final String TOKEN_HEADER = "auth_token";
    public static final String EMAIL_HEADER = "X-Auth-Email";

    public static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
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

}
