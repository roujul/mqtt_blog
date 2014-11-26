package com.code_roux.blog.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessengerPublisher {
	
	public static void main(String[] args) {
		String topic = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a topic (default:" + MqttPoc.TOPIC_BLOG_MQTT + " ): ");
		try {
			topic = br.readLine();
		} catch (IOException e) {
			System.out.println("Unexpected error when reading input");
			e.printStackTrace();
		}
		if("".equals(topic)) {
			topic = MqttPoc.TOPIC_BLOG_MQTT;
		}
		
		try {
			MqttPoc publisher = new MqttPoc(MqttPoc.BROKER_ADDRESS, "publisher");
			publisher.connectToBroker("username", "password");
			
			for (;;) {
				try {
					System.out.print("Enter message: ");
					String message = br.readLine();
					publisher.publish(topic, 0, message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
