package org.pullrequest.squarelibs.demo.activity;

import java.util.List;

import javax.inject.Inject;

import org.pullrequest.squarelibs.demo.C;
import org.pullrequest.squarelibs.demo.DaggerModule;
import org.pullrequest.squarelibs.demo.R;
import org.pullrequest.squarelibs.demo.activity.adapter.NetworkListAdapter;
import org.pullrequest.squarelibs.demo.events.MessageEvent;
import org.pullrequest.squarelibs.demo.events.NetworksAvailableEvent;
import org.pullrequest.squarelibs.demo.events.WifiStateChangeEvent;
import org.pullrequest.squarelibs.demo.rest.Github;
import org.pullrequest.squarelibs.demo.rest.Repo;
import org.pullrequest.squarelibs.demo.service.MyService;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class Main extends DaggerActivity {

	@Inject
	private MyService service;

	private NetworkListAdapter networkListAdapter;
	private WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// using my injected service to get title
		setTitle(service.getName());

		// getting wifi manager before enabling the bus
		wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

		// the otto bus will get events from broadcast receivers
		bus.register(this);

		// get scan results from wifi in background
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				getNetworks();
				getSquareRepos();
			}
		};
		new Thread(runnable).start();

		TextView hello = (TextView) findViewById(R.id.hello);
		hello.setText("Wifi networks");

		// display saved instance if screen is rotated
		if ((savedInstanceState != null) && savedInstanceState.containsKey("list")) {
			networkListAdapter = new NetworkListAdapter((ScanResult[]) savedInstanceState.getParcelableArray("list"), getLayoutInflater());
		} else {
			networkListAdapter = new NetworkListAdapter(new ScanResult[0], getLayoutInflater());
		}
		ListView listNetworks = (ListView) findViewById(R.id.listNetworks);
		listNetworks.setAdapter(networkListAdapter);
	}

	@Override
	protected void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArray("list", networkListAdapter.getData());
		super.onSaveInstanceState(outState);
	}

	@Subscribe
	public void displayNetworks(final NetworksAvailableEvent event) {
		// event is coming from the background, requestiing UI thread
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				networkListAdapter.changeData(event.getNetworks());
			}
		});
	}

	@Subscribe
	public void displayMessage(final MessageEvent event) {
		// event is coming from the background, requestiing UI thread
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Main.this, event.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}

	@Subscribe
	public void displayWifiState(final WifiStateChangeEvent event) {
		// event is coming from the background, requestiing UI thread
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setTitle("Wifi state : " + event.getState());
			}
		});
	}

	private void getNetworks() {
		if (wifiManager.isWifiEnabled()) {
			wifiManager.startScan();
		} else {
			bus.post(new MessageEvent("Wifi is not enabled"));
		}
	}

	private void getSquareRepos() {
		try {
			Github githubApi = DaggerModule.getRestAdapter().create(Github.class);
			final List<Repo> repos = githubApi.getSquareRepos();
			//final Org square = githubApi.getSquare();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Main.this, "Square got " + repos.size() + " repositories.", Toast.LENGTH_LONG).show();
					//Toast.makeText(Main.this, "Square : " + square.getType(), Toast.LENGTH_LONG).show();
				}
			});
		} catch (Exception e) {
			Log.e(C.LOG_TAG, "Error during github api call", e);
		}
	}

	@Produce
	public WifiStateChangeEvent produceWifiStateChange() {
		// automatic first reponse to subscribers : give current wifi state
		return new WifiStateChangeEvent(wifiManager.getWifiState());
	}
}
