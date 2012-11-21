package org.pullrequest.squarelibs.demo;

import android.app.Application;

public class DaggerApplication extends Application {
		
	public void onCreate() {
		// initial request to build the graph and store it in DaggerModule
		DaggerModule.getObjectGraph();
	}
}
