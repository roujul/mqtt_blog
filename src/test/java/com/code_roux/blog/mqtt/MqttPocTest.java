package com.code_roux.blog.mqtt;

import org.junit.Test;


public class MqttPocTest {
	
	private static final String TOPIC = "topicTest";
	private static final String BROKER_ADDRESS = "tcp://iot.eclipse.org:1883";

	@Test
	public void testPublishing() throws Exception {
		MqttPoc subscriber = new MqttPoc(BROKER_ADDRESS, "subscriber");
		subscriber.connectToBroker("username", "password");
		subscriber.subscribe(TOPIC);

		MqttPoc publisher = new MqttPoc(BROKER_ADDRESS, "publisher");
		publisher.connectToBroker("username", "password");
		
		for (int i = 0; i < 10; i++) {
			publisher.publish(TOPIC, 2, "message " + i);
		}
	}
}
