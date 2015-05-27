package com.httpclientlib.server.connection.util;

import java.util.Iterator;
import java.util.Map;

public class HttpParamsManager {

    public static String getQueryStringForPost(Map<Object, Object> params) {
        if (params == null || params.size() == 0)
            return "";

        String urlParams = null;
        Iterator<Object> iterator = params.keySet().iterator();

        while (iterator.hasNext()) {
            Object key = iterator.next();
            Object objValue = params.get(key);
            String value = objValue.toString();
            urlParams = urlParams == null ? "" : urlParams + "&";
            urlParams += key + "=" + value;
        }

        return urlParams;
    }

}
