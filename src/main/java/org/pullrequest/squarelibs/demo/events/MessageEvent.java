package org.pullrequest.squarelibs.demo.events;


public class MessageEvent {

	private String message;
	
	public MessageEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
