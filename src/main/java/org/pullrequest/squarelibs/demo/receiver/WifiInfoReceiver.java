package org.pullrequest.squarelibs.demo.receiver;

import java.util.List;

import org.pullrequest.squarelibs.demo.events.NetworksAvailableEvent;
import org.pullrequest.squarelibs.demo.events.WifiStateChangeEvent;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WifiInfoReceiver extends DaggerBroadcastReceiver {

	private List<ScanResult> scanResults;
	private WifiManager wifiManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		
		// scan results available : post event to the bus to display on the main activity
		if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			scanResults = wifiManager.getScanResults();
			bus.post(new NetworksAvailableEvent(scanResults));
		}
		// the wifi state changed : display it on the main activity
		else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			bus.post(new WifiStateChangeEvent(wifiManager.getWifiState()));
		}
	}
}
