 
import java.io.IOException;


import tijos.runtime.net.mqtt.IMqttClientListener;
import tijos.runtime.net.mqtt.MqttClient;
import tijos.runtime.net.mqtt.MqttConnectOptions;
import tijos.runtime.networkcenter.TiNetworkCenter;
import tijos.util.logging.Logger;

/**
 * 
 * MQTT Client ����, �����д�����ʱ��ȷ��MQTT Server��ַ���û���������ȷ
 *
 */


//MQTT �¼�����
class MqttEventLister implements IMqttClientListener
{
	
	@Override
	public void onConnected(Object userContext) {
		Logger.info("MqttEventLister","onConnected");
	}

	@Override
	public void onDisconnected(Object userContext) {
		Logger.info("MqttEventLister","onDisconnected");
		
	}

	@Override
	public void messageArrived(Object userContext, String topic, byte[] payload) throws IOException {
		Logger.info("MqttEventLister","messageArrived topic = " + topic);
		
	}

	@Override
	public void publishCompleted(Object userContext, int msgId, String topic, int result) throws IOException {
		Logger.info("MqttEventLister","publishCompleted topic = " + topic + " result = " + result + "msgid = " + msgId);
	
	}

	@Override
	public void subscribeCompleted(Object userContext, int msgId,String topic, int result) throws IOException {
		Logger.info("MqttEventLister","subscribeCompleted topic = " + topic + " result " + result + "msgid = " + msgId);
	
	}

	@Override
	public void unsubscribeCompleted(Object userContext, int msgId, String topic, int result) throws IOException {
		Logger.info("MqttEventLister","unsubscribeCompleted topic = " + topic + "result " + result + "msgid = " + msgId);

	}
	
}

public class MQTTClientDemo {

	public static void main(String args[]) {
		
		//����WLAN��DNS
		TiNetworkCenter.getNetworkCenter().getWLAN().startup(10000);
		TiNetworkCenter.getNetworkCenter().getDNS().startup();
	
		//MQTT Server ��ַ,�û���, ���� 
		final String broker       = "tcp://tijos.mqtt.iot.gz.baidubce.com:1883";
        final String username     = "tijos/dev1";
        final String password     = "tWnuCZdmdgqn6uT6oaVjE1NwC9atipvOTxBA0Xn2QFQ=";

        //ClientID
        final String clientId     = "mqtt_test_java_tijos";
	        
        	//MQTT��������
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password);
            //�����Զ���������
            connOpts.setAutomaticReconnect(true);

            MqttClient mqttClient = new MqttClient(broker, clientId);
       
            int qos = 1;

	        try {
	           
	        	mqttClient.SetMqttClientListener(new MqttEventLister());
	        	
	        	//����MQTT������
	            mqttClient.connect(connOpts, mqttClient);
	            
	            //����topic
	            String topic        = "topic2";
	            String head         = "Message from TiJOS NO. ";
	            
	            int msgId = mqttClient.subscribe(topic, qos);
	            Logger.info("MQTTClientDemo", "Subscribe to topic: " + topic + " msgid = " + msgId);
	            
	            //����topic
	            int counter = 0;
	            while(true)
	            {
	            	String content = head + counter;
	            	msgId = mqttClient.publish(topic, content.getBytes(), qos, false);
	            	Logger.info("MQTTClientDemo", "Topic " + topic + "Publish message: " + content  + " msgid = " + msgId);
	            	
	            	counter ++;
	            	Thread.sleep(1000);
	            }

	            
	        } catch(Exception ex) {
	        	
	            ex.printStackTrace();
	        }
	        finally
	        {	
	        	try {
					mqttClient.close();//release resource 
				} catch (IOException e) {
					 
					e.printStackTrace();
				} 
	        }
	    }
}
