package com.tedcafe.net;

/**
 * Created by tedcafe on 2017. 9. 25..
 */

public class TedHttpConfig {
	public int connectionTimeout = 5000;
	public int readTimeout = 5000;

	public void loadFromConfig(TedHttpConfig config) {
		this.connectionTimeout = config.connectionTimeout;
		this.readTimeout = config.readTimeout;
	}
}
