package com.code_roux.blog.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPoc implements MqttCallback {

	private static final String BROKER_ADDRESS = "tcp://iot.eclipse.org:1883";

	private static final String TOPIC_BLOG_MQTT = "topic/blog/mqtt";

	private static final Logger LOGGER = Logger.getLogger(MqttPoc.class);
	
	private String clientId;
	private String brokerUrl;
	private MqttClient mqttClient;

	public MqttPoc(String brokerUrl, String clientId) {
		this.brokerUrl = brokerUrl;
		this.clientId = clientId;
	}
	
	public void connectToBroker(String username, String password) throws Exception {
		final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setCleanSession(true);
		mqttConnectOptions.setKeepAliveInterval(30);
		mqttConnectOptions.setUserName(username);
		mqttConnectOptions.setPassword(password.toCharArray());
		mqttClient = new MqttClient(brokerUrl, clientId);
		mqttClient.setCallback(this);
		mqttClient.connect(mqttConnectOptions);
	}
	
	public void subscribe(String topic) throws Exception {
		mqttClient.subscribe(topic, 0);
		LOGGER.info("Subscribed to messages from " + topic);
	}
	
	public void publish(String topic, int qos, String message) throws Exception {
		final MqttMessage mqttMessage = new MqttMessage(message.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(false);
		MqttDeliveryToken mqttDeliveryToken = mqttClient.getTopic(topic).publish(mqttMessage);
		mqttDeliveryToken.waitForCompletion();
		Thread.sleep(100);
	}
	
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String arg0, MqttMessage message) throws Exception {
		LOGGER.info(new String(message.getPayload()));
	}

	public static void main(String[] args) {
		MqttPoc mqttPoc = new MqttPoc(BROKER_ADDRESS, "pubAndSub");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			mqttPoc.connectToBroker("username", "password");
			mqttPoc.subscribe(TOPIC_BLOG_MQTT);

			for (;;) {
				try {
					System.out.print("Enter message: ");
					String message = br.readLine();
					mqttPoc.publish(TOPIC_BLOG_MQTT, 0, message);
					Thread.sleep(500);
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
