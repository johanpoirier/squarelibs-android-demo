package org.pullrequest.squarelibs.demo;

import javax.inject.Singleton;

import org.apache.http.impl.client.DefaultHttpClient;
import org.pullrequest.squarelibs.demo.activity.Main;
import org.pullrequest.squarelibs.demo.receiver.WifiInfoReceiver;
import org.pullrequest.squarelibs.demo.service.MyService;

import retrofit.http.GsonConverter;
import retrofit.http.RestAdapter;
import retrofit.http.Server;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

@Module(entryPoints = { Main.class, WifiInfoReceiver.class})
public class DaggerModule {

	private static ObjectGraph graph;
	private static RestAdapter restAdapter;
	
	@Provides
	@Singleton
	public MyService provideMyService() {
		return new MyService();
	}

	@Provides
	@Singleton
	public Bus provideBus() {
		// our event bus running on any thread
		return new Bus(ThreadEnforcer.ANY);
	}
	
	// give access to the graph in the entire app
	public static ObjectGraph getObjectGraph() {
		if(graph == null) {
			graph = ObjectGraph.create(new DaggerModule());
		}
		return graph;
	}
	

	// give access to the rest api to the entire app
	public static RestAdapter getRestAdapter() {
		if(restAdapter == null) {
			// rest api
		    restAdapter = new RestAdapter.Builder()
		    .setServer(new Server("https://api.github.com"))
		    .setClient(new DefaultHttpClient())
		    .setConverter(new GsonConverter(new Gson()))
		    .build();
		}
		return restAdapter;
	}
}
