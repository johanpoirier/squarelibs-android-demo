package org.pullrequest.squarelibs.demo.activity.adapter;

import org.pullrequest.squarelibs.demo.R;
import org.pullrequest.squarelibs.demo.R.id;
import org.pullrequest.squarelibs.demo.R.layout;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class NetworkListAdapter extends BaseAdapter {

	private ScanResult[] mData;
	private LayoutInflater inflater;

	public NetworkListAdapter(ScanResult[] data, LayoutInflater inflater) {
		mData = data;
		this.inflater = inflater;
	}

	public void changeData(ScanResult[] data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData.length;
	}

	@Override
	public ScanResult getItem(int position) {
		return mData[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public ScanResult[] getData() {
		return mData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.network_item, parent, false);
		}

		((TextView) convertView.findViewById(R.id.ssid)).setText(getItem(position).SSID);
		((TextView) convertView.findViewById(R.id.bssid)).setText(getItem(position).BSSID);

		return convertView;
	}
}
