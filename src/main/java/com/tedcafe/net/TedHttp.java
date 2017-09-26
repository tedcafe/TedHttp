
package com.tedcafe.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;

/**
 * Created by TedHan on 2017-09-19.
 *
 * @Version 0.0.1
 * @Author Ted S. Han (tedcafe@gmail.com)
 */

public class TedHttp {

	private static TedHttp instance = null;
	public static Context context;
	public static TedHttpConfig defaultConfig = initializeConfig();


	public static TedHttp getInstance() {
		if (instance == null) {
			instance = new TedHttp();
		}

		return instance;
	}


	private static TedHttpConfig initializeConfig() {
		TedHttpConfig config = new TedHttpConfig();
		return config;
	}


	protected TedHttp() {

	}


	/**
	 * Post request.
	 * @param urlString
	 * @return TedHttpSession
	 */
	public static TedHttpSession post(String urlString) {
		return TedHttp.post(urlString, null, null);
	}

	/**
	 * Post request.
	 * @param urlString
	 * @param parameters HashMap parameters
	 * @return TedHttpSession
	 */
	public static TedHttpSession post(String urlString, HashMap<String, Object> parameters) {
		return TedHttp.post(urlString, parameters, null);
	}

	/**
	 * Post request.
	 * @param urlString
	 * @param parameters HashMap parameters
	 * @param complete Complete callback.
	 * @return TedHttpSession
	 */
	public static TedHttpSession post( String urlString, HashMap<String, Object> parameters, TedHttpSession.CompleteCallback complete) {
		TedHttpSession sess = new TedHttpSession(TedHttpRequest.Method.POST, urlString);
		sess.config.loadFromConfig(TedHttp.defaultConfig);

		if ( parameters != null )
			sess.parameters(parameters);

		if ( complete != null )
			sess.callback(complete);

		return sess;
	}


	/**
	 * Get request.
	 * @param urlString
	 * @return TedHttpSession
	 */
	public static TedHttpSession get(String urlString) {
		return TedHttp.post(urlString, null, null);
	}

	/**
	 * Get request.
	 * @param urlString
	 * @param parameters
	 * @return TedHttpSession
	 */
	public static TedHttpSession get(String urlString, HashMap<String, Object> parameters) {
		return TedHttp.post(urlString, parameters, null);
	}

	/**
	 * Get request.
	 * @param urlString
	 * @param parameters
	 * @param complete
	 * @return TedHttpSession
	 */
	public static TedHttpSession get(String urlString, HashMap<String, Object> parameters, TedHttpSession.CompleteCallback complete) {
		TedHttpSession sess = new TedHttpSession(TedHttpRequest.Method.GET, urlString);
		sess.config.loadFromConfig(TedHttp.defaultConfig);

		if ( parameters != null )
			sess.parameters(parameters);

		if ( complete != null )
			sess.callback(complete);

		return sess;
	}


	public static boolean isNetworkAvailable() {
		boolean isAvailable = true;

		if (null != context) {
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();

			if (netInfo == null || !netInfo.isConnectedOrConnecting()) {
				isAvailable = false;
			}
		}

		return isAvailable;
	}
}

