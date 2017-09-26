package com.tedcafe.net;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by TedHan on 2017-09-19.
 */

public class TedHttpRequest {

    public enum Method {
         GET
        ,POST
    }

    public Method method;
    public String urlString;
	public HashMap<String, String> headers;
	public HashMap<String, Object> parameters;
	public Object userObject;


    public TedHttpRequest() {
        this(Method.GET, null);
    }


    public TedHttpRequest(Method method, String urlString) {
        this.method = method;
        this.urlString = urlString;
        this.headers = new HashMap<String, String>();
	    this.parameters = new HashMap<String, Object>();
    }


	/**
	 * Returns request method string.
	 * @return
	 */
	public String methodString() {
        return method.toString();
    }


	/**
	 * Set some user object for accessing in complete callback.
	 * @param userObject
	 */
	public void userObject(Object userObject) {
	    this.userObject = userObject;
    }


    public HashMap<String, String> headers() {
        return this.headers;
    }


    public void header(String key, String value) {
        if ( key != null && value != null ) {
            this.headers.put(key, value);
        }
    }

    public void parameter(String key, String value) {
	    if ( key != null && value != null ) {
		    this.parameters.put(key, value);
	    }
    }


    public boolean isGet() {
	    return this.method == Method.GET;
    }


	public boolean isPost() {
		return this.method == Method.POST;
	}


    public String urlWithQueryString() {

	    StringBuilder sb = new StringBuilder(this.urlString);

	    if ( !this.parameters.isEmpty() ) {
		    String queryString = TedHttpRequest.buildQueryString(this.parameters);

		    if ( this.urlString.contains("?") ) {
			    sb.append("&");
		    } else {
			    sb.append("?");
		    }
		    sb.append(queryString);
	    }

	    return sb.toString();
	}


    public byte[] postData() {
	    byte[] data = null;

	    String queryString = TedHttpRequest.buildQueryString(this.parameters);

	    try {
		    data = queryString.getBytes("UTF-8");
	    } catch ( UnsupportedEncodingException e ) {
		    e.printStackTrace();
	    }

	    return data;
    }


	/**
	 * Build query string.
	 * @param postsData
	 * @return String.
	 */
	public static String buildQueryString(Map<String, Object> postsData) {
		StringBuilder postData = new StringBuilder();

		try {
			for (Map.Entry<String, Object> param : postsData.entrySet()) {
				if (postData.length() != 0) postData.append('&');

				Object value = param.getValue();
				String key = param.getKey();

				if (value instanceof Object[] || value instanceof List<?>) {
					int size = value instanceof Object[] ? ((Object[]) value).length : ((List<?>) value).size();
					for (int i = 0; i < size; i++) {
						Object val = value instanceof Object[] ? ((Object[]) value)[i] : ((List<?>) value).get(i);
						if (i > 0) postData.append('&');
						postData.append(URLEncoder.encode(key + "[" + i + "]", "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(val), "UTF-8"));
					}
				} else {
					postData.append(URLEncoder.encode(key, "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}

		return postData.toString();
	}
}
