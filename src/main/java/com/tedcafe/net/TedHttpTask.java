package com.tedcafe.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by TedHan on 2017-09-19.
 */

public class TedHttpTask extends AsyncTask<TedHttpSession, String, TedHttpSession> {

    final static String LogTag = "TedHttp";

    @Override
    protected TedHttpSession doInBackground(TedHttpSession... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        TedHttpSession sess = params[0];
        TedHttpRequest req = sess.request();
        TedHttpResponse res = sess.response();


	    if ( !TedHttp.isNetworkAvailable() ) {
		    res.errorCode = TedHttpResponse.ErrorCode.NetworkError;
		    return sess;
	    }

        try {
            if (req == null || req.urlString == null || req.method == null) {
                Log.e(TedHttpTask.LogTag, "BAD HttpRequest");
                throw new Exception();
            }

            String urlStr;
            if (req.isGet()) {
                urlStr = req.urlWithQueryString();
            } else {
                urlStr = req.urlString;
            }

            // -------------------------------------------------------------------------------------
            //
            // -------------------------------------------------------------------------------------
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.v(TedHttpTask.LogTag, req.methodString());
            urlConnection.setRequestMethod(req.methodString());
            urlConnection.setDoInput(true);


            if (req.headers() != null) {
                for (HashMap.Entry<String, String> pair : req.headers().entrySet()) {
                    urlConnection.setRequestProperty(pair.getKey(), pair.getValue());
                }
            }

            urlConnection.setConnectTimeout(sess.config.connectionTimeout);
            urlConnection.setReadTimeout(sess.config.readTimeout);

            if (req.isPost()) {
                if (!req.parameters.isEmpty()) {
                    urlConnection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    byte[] postData = req.postData();
                    wr.write(postData);
                }
            }
            // -------------------------------------------------------------------------------------

            int responseCode = urlConnection.getResponseCode();
            Log.v(TedHttpTask.LogTag, "responseCode = " + responseCode);
            res.responseCode = responseCode;

            String responseString = "";

            InputStream is = urlConnection.getInputStream();


            if (is != null) {
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
                responseString = readStream(is);
            } else {
                InputStream eis = urlConnection.getErrorStream();

                if (eis != null) {
                    String errorMesg = readStream(eis);
                    Log.e(TedHttpTask.LogTag, errorMesg);
                }
            }

            Log.v(TedHttpTask.LogTag, "responseCode: " + responseCode);
            Log.v(TedHttpTask.LogTag, "responseBody: " + responseString);

            res.responseBody = responseString;

        } catch ( SocketException se ) {
            Log.e(TedHttpTask.LogTag, "SocketException");
            se.printStackTrace();
            res.errorCode = TedHttpResponse.ErrorCode.NetworkError;
            res.exception = se;
        } catch (Exception e) {
            Log.e(TedHttpTask.LogTag, "Exception");
            e.printStackTrace();
            res.errorCode = TedHttpResponse.ErrorCode.ResponseError;
            res.exception = e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return sess;
    }


    private String readStream(InputStream in) {

        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response.toString();
    }


    @Override
    protected void onPostExecute(TedHttpSession sess) {
        super.onPostExecute(sess);

        if ( sess.response() != null ) {
            String body = sess.response().responseBody;

            if ( body != null ) {
                Log.v(TedHttpTask.LogTag, "responseBody = " + body);
            }
        }

        sess.invokeCallback();
    }
}