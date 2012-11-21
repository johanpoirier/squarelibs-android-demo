package org.pullrequest.squarelibs.demo.events;

import java.util.List;

import android.net.wifi.ScanResult;

public class NetworksAvailableEvent {

	private List<ScanResult> networks;
	
	public NetworksAvailableEvent(List<ScanResult> networks) {
		this.networks = networks;
	}

	public ScanResult[] getNetworks() {
		return networks.toArray(new ScanResult[networks.size()]);
	}
}
