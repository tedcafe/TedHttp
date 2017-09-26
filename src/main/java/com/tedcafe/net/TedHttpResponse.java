package com.tedcafe.net;

import android.support.v4.media.session.PlaybackStateCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by TedHan on 2017-09-19.
 */

public class TedHttpResponse {

	public enum ErrorCode {
		 Ok
		,NetworkError
		,JsonError
		,ResponseError
	}


	/**
	 * Same as HTTP Status Code
	 *
	 * @see java.net.HttpURLConnection#getResponseCode()
	 */
	public int responseCode;

	public String responseBody;

	/**
	 * While requsting and getting response,
	 *
	 */
	public ErrorCode errorCode = ErrorCode.Ok;
	public String errorMessage;

	public Exception exception;


	/**
	 * Return response body as JSObject. If fails, return null.
	 *
	 * @return JSONObject
	 */
	public JSONObject json() {

		JSONObject json = null;

		if ( this.responseBody != null ) {
			try {
				json = new JSONObject(this.responseBody);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return json;
	}


	/**
	 * Return if there is no problem while querying request and processing response or not.
	 * @return
	 */
	public boolean isOk() {
		return this.errorCode == TedHttpResponse.ErrorCode.Ok && this.responseCode == HttpURLConnection.HTTP_OK;
	}


	/**
	 * Return default error message for current responseCode.
	 * @return
	 */
	public String defaultErrorMessage() {
		String mesg;

		switch (this.errorCode) {
			case Ok:
				mesg = "Ok";
				break;
			case JsonError:
				mesg = "Json Parsing Error";
				break;
			case NetworkError:
				mesg = "Network Error";
				break;
			case ResponseError:
				mesg = "While processing response, some problems occurred";
				break;
			default:
				mesg = "";
		}

		return mesg;
	}
}
