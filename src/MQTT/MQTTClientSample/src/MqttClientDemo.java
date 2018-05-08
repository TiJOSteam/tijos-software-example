
import tijos.framework.networkcenter.TiDNS;
import tijos.framework.networkcenter.TiWLAN;
import tijos.framework.networkcenter.mqtt.MqttClientListener;

import java.io.IOException;

import tijos.framework.networkcenter.mqtt.MqttClient;
import tijos.framework.networkcenter.mqtt.MqttConnectOptions;
import tijos.framework.networkcenter.mqtt.MqttException;
import tijos.framework.util.Delay;
import tijos.framework.util.logging.Logger;

/**
 * 
 * MQTT Client 例程, 在运行此例程时请确保MQTT Server地址及用户名密码正确
 * 
 * @author TiJOS
 */

/**
 * MQTT 事件监听
 * 
 */
class MqttEventLister implements MqttClientListener {

	@Override
	public void connectComplete(Object userContext, boolean reconnect) {
		Logger.info("MqttEventLister", "connectComplete");

	}

	@Override
	public void connectionLost(Object userContext) {
		Logger.info("MqttEventLister", "connectionLost");

	}

	@Override
	public void onMqttConnectFailure(Object userContext, int cause) {
		Logger.info("MqttEventLister", "onMqttConnectFailure cause = " + cause);

	}

	@Override
	public void onMqttConnectSuccess(Object userContext) {
		Logger.info("MqttEventLister", "onMqttConnectSuccess");

	}

	@Override
	public void messageArrived(Object userContext, String topic, byte[] payload) {
		Logger.info("MqttEventLister", "messageArrived topic = " + topic);

	}

	@Override
	public void publishCompleted(Object userContext, int msgId, String topic, int result) {
		Logger.info("MqttEventLister",
				"publishCompleted topic = " + topic + " result = " + result + "msgid = " + msgId);

	}

	@Override
	public void subscribeCompleted(Object userContext, int msgId, String topic, int result) {
		Logger.info("MqttEventLister",
				"subscribeCompleted topic = " + topic + " result " + result + "msgid = " + msgId);

	}

	@Override
	public void unsubscribeCompleted(Object userContext, int msgId, String topic, int result) {
		Logger.info("MqttEventLister",
				"unsubscribeCompleted topic = " + topic + "result " + result + "msgid = " + msgId);

	}

}

public class MqttClientDemo {

	public static void main(String args[]) {

		// 启动WLAN及DNS
		try {
			TiWLAN.getInstance().startup(10);
			TiDNS.getInstance().startup();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		// MQTT Server 地址,用户名, 密码
		final String broker = "tcp://tijos.mqtt.iot.gz.baidubce.com:1883";
		final String username = "tijos/dev1";
		final String password = "tWnuCZdmdgqn6uT6oaVjE1NwC9atipvOTxBA0Xn2QFQ=";

		// ClientID
		final String clientId = "mqtt_test_java_tijos";

		// MQTT连接设置
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setUserName(username);
		connOpts.setPassword(password);
		// 允许自动重新连接
		connOpts.setAutomaticReconnect(true);

		MqttClient mqttClient = new MqttClient(broker, clientId);

		int qos = 1;

		try {

			mqttClient.SetMqttClientListener(new MqttEventLister());

			// 连接MQTT服务器
			mqttClient.connect(connOpts, mqttClient);

			// 订阅topic
			String topic = "topic2";
			String head = "Message from TiJOS NO. ";

			int msgId = mqttClient.subscribe(topic, qos);
			Logger.info("MQTTClientDemo", "Subscribe to topic: " + topic + " msgid = " + msgId);

			// 发布topic
			int counter = 0;
			while (true) {
				String content = head + counter;
				msgId = mqttClient.publish(topic, content.getBytes(), qos, false);
				Logger.info("MQTTClientDemo", "Topic " + topic + "Publish message: " + content + " msgid = " + msgId);

				counter++;
				Delay.msDelay(1000);
			}

		} catch (MqttException ex) {
			ex.printStackTrace();
		} finally {
			try {
				mqttClient.close();// release resource
			} catch (MqttException ex) {
				/* ignore */}
		}
	}
}
