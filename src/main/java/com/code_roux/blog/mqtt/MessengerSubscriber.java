package com.code_roux.blog.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessengerSubscriber {

	public static void main(String[] args) {
		String clientId = "";
		String topic = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (; "".equals(clientId);) {
			System.out
					.print("Enter a unique identifier (for example, your name): ");
			try {
				clientId = br.readLine();
			} catch (IOException e) {
				System.out.println("Unexpected error when reading input");
				e.printStackTrace();
			}
		}
		System.out.print("Enter a topic (default:" + MqttPoc.TOPIC_BLOG_MQTT + " ): ");
		try {
			topic = br.readLine();
		} catch (IOException e) {
			System.out.println("Unexpected error when reading input");
			e.printStackTrace();
		}
		if ("".equals(topic)) {
			topic = MqttPoc.TOPIC_BLOG_MQTT;
		}

		MqttPoc subscriber = new MqttPoc(MqttPoc.BROKER_ADDRESS, "subscriber");
		try {
			subscriber.connectToBroker("username", "password");
			subscriber.subscribe(topic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
