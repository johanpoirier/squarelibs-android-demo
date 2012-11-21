package org.pullrequest.squarelibs.demo.receiver;

import javax.inject.Inject;

import org.pullrequest.squarelibs.demo.DaggerModule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.squareup.otto.Bus;

public abstract class DaggerBroadcastReceiver extends BroadcastReceiver {

	@Inject
	protected Bus bus;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		DaggerModule.getObjectGraph().inject(this);
	}
}
