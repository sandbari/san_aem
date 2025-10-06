package com.vcm.core.utils;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

public class XSSUtils {

    public static String encodeQueryParameter(String param) {
        try {
            return URIUtil.encodeQuery(param, "UTF-8");
        } catch (URIException e) {
            return "";
        }
    }

}
