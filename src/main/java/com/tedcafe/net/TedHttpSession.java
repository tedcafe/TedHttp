package com.tedcafe.net;


import android.os.AsyncTask;
import java.util.HashMap;
import java.util.Objects;


/**
 * Created by tedcafe on 2017. 9. 20..
 */

public class TedHttpSession {

    /**
     * Complete Callback Interface
     */
    public interface CompleteCallback {
        void onComplete(TedHttpRequest req, TedHttpResponse res);
    }


    private TedHttpRequest req;
    private TedHttpResponse res;
    private CompleteCallback callback;
    private AsyncTask task;
    public TedHttpConfig config;


	public TedHttpSession(TedHttpRequest.Method method, String urlString) {
		this.req = new TedHttpRequest(method, urlString);
		this.res = new TedHttpResponse();
        this.config = new TedHttpConfig();
	}


    public TedHttpRequest request() {
        return this.req;
    }


    public void setRequest(TedHttpRequest req) {
        this.req = req;
    }


    public TedHttpResponse response() {
        return this.res;
    }


    public void setResponse(TedHttpResponse res) {
        this.res = res;
    }


    public TedHttpSession callback(CompleteCallback callback) {
        this.callback = callback;
        return this;
    }


    public void invokeCallback() {
	    this.callback.onComplete(this.request(), this.response());
    }


    /**
     * Start request.
     */
    public void start() {
        this.task = new TedHttpTask().execute(this);
    }


    /**
     * Set request parameter.
     * If request method is POST, this value place in body.
     * If GET, this value will is added in url as querystring.
     *
     * @param key
     * @param value
     * @return
     */
    public TedHttpSession parameter(String key, String value) {
        this.req.parameter(key, value);
        return this;
    }


    public TedHttpSession parameters(HashMap<String, Object> params) {
        this.req.parameters.putAll(params);
        return this;
    }


    public TedHttpSession header(String key, String value) {
        this.req.header(key, value);
        return this;
    }


    public TedHttpSession headers(HashMap<String, String> headers) {
        this.req.headers.putAll(headers);
        return this;
    }


    /**
     * User-defined object.
     *
     * <p>Set some object to access in complete callback with a request object.</p>
     * @param userObject
     * @return
     */
    public TedHttpSession userObject(Object userObject) {
        this.req.userObject(userObject);
        return this;
    }


    /**
     * Connection timeout. (milliseconds)
     *
     * @param connectionTimeout
     * @return
     */
    public TedHttpSession connectionTimeout(int connectionTimeout) {
        this.config.connectionTimeout = connectionTimeout;
        return this;
    }

    /**
     * Read timeout. (milliseconds)
     * @param readTimeout
     * @return
     */
    public TedHttpSession readTimeout(int readTimeout) {
        this.config.readTimeout = readTimeout;
        return this;
    }
}
