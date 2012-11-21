package org.pullrequest.squarelibs.demo.activity;

import javax.inject.Inject;

import org.pullrequest.squarelibs.demo.DaggerModule;

import android.app.Activity;

import com.squareup.otto.Bus;

public abstract class DaggerActivity extends Activity {

	@Inject
	protected Bus bus;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DaggerModule.getObjectGraph().inject(this);
	}
}
