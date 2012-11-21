package org.pullrequest.squarelibs.demo.service;


public class MyService {

	public String getName() {
		return "Johan";
	}
	
	public String getInstanceId() {
		return this.toString().substring(this.toString().indexOf("@"));
	}
}
