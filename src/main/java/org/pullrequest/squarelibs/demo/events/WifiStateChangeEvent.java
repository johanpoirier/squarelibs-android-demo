package org.pullrequest.squarelibs.demo.events;

import android.net.wifi.WifiManager;


public class WifiStateChangeEvent {

	private int state;
	
	public WifiStateChangeEvent(int state) {
		this.state = state;
	}

	public String getState() {
		String wifiState = "?";
		switch(state) {
		case WifiManager.WIFI_STATE_DISABLED:
			wifiState = "disabled";
			break;
		case WifiManager.WIFI_STATE_DISABLING:
			wifiState = "disabling";
			break;
		case WifiManager.WIFI_STATE_ENABLED:
			wifiState = "enabled";
			break;
		case WifiManager.WIFI_STATE_ENABLING:
			wifiState = "enabling";
			break;
		case WifiManager.WIFI_STATE_UNKNOWN:
			wifiState = "unknown";
			break;
		}
		return wifiState;
	}
}
