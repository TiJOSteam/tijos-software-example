
import java.io.IOException;

import tijos.framework.networkcenter.alibaba.AliYunIoT;
import tijos.framework.networkcenter.alibaba.IDataModelEventListener;
import tijos.framework.platform.lpwan.lte.TiLTE;
import tijos.framework.util.json.JSONObject;

/**
 * 阿里云接入例程 
 * 请先在阿里云平台进行产品和设备注册，并修改相应的参数进行测试
 * 
 * @author TiJOS
 *
 */

class AliListener implements IDataModelEventListener {
	@Override
	public void onGenericReplyArrived(String product, String deviceName, long msgId, int code, String message,
			byte[] data) {
		System.out.println("onGenericReplyArrived  " + product + " " + deviceName + " mid " + msgId + " code " + code
				+ " message " + message);
	}

	@Override
	public void onPropertySetArrived(String product, String deviceName, long msgId, String params) {
		System.out.println("onPropertySetArrived ");

		// 控制指令解析
		JSONObject commands = new JSONObject(params);
		// 命令执行

		//

		// 控制指令回复
		JSONObject reply = new JSONObject();

		try {
			AliYunIoT.getInstance().propertySetReply(msgId, 200, reply.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAsyncServiceInvokeArrived(String product, String deviceName, long msgId, String serviceId,
			String params) {
		System.out.println("onAsyncServiceInvokeArrived ");

		// 解析服务参数
		JSONObject parameters = new JSONObject(params);

		// 执行服务

		// 返回结果
		int code = 200; // 200 - 成功
		JSONObject reply = new JSONObject();

		try {
			AliYunIoT.getInstance().asynServiceReply(msgId, serviceId, code, reply.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onSyncServiceInvokeArrived(String product, String deviceName, long msgId, String rrpcId,
			String serviceId, String params) {
		System.out.println("onSyncServiceInvokeArrived ");

		// 解析服务参数
		JSONObject parameters = new JSONObject(params);

		// 执行服务

		// 返回结果
		int code = 200; // 200 - 成功
		JSONObject reply = new JSONObject();

		try {
			AliYunIoT.getInstance().syncServiceReply(msgId, serviceId, rrpcId, code, reply.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onRawDataArrived(String product, String deviceName, byte[] rawData) {
		System.out.println("onRawDataArrived ");

	}

	@Override
	public void onRawServiceInvokeArrived(String product, String deviceName, String rrpcId, byte[] data) {
		System.out.println("onRawServiceInvokeArrived ");

	}

}

public class AliYunSample {
	public static void main(String[] args) throws IOException {

		TiLTE.getInstance().startup(30);

		AliYunIoT aliiot = AliYunIoT.getInstance();

		// 阿里云平台接入域名 mqtt://[ProductKey].iot-as-mqtt.cn-shanghai.aliyuncs.com:443
		String serverUrl = "mqtt://a1QJjmusiPI.iot-as-mqtt.cn-shanghai.aliyuncs.com:443"; /* 阿里云平台上海站点的域名后缀 */

		// 设备密钥 由平台生成
		String ProductKey = "a1QJjmusiPI";
		String DeviceName = "gwdemo1";
		String DeviceSecret = "71fab7d64714233e8757181ab6c9e28b";

		// 启动并连接云平台
		aliiot.start(serverUrl, ProductKey, DeviceName, DeviceSecret, new AliListener());

		// 物模型中的属性上报
		JSONObject properties = new JSONObject();
		properties.put("LightStatus", 0);

		int mid = aliiot.propertyPost(properties.toString());

		System.out.println("mid " + mid);

		// 物模型中的事件上报
		JSONObject errorEvent = new JSONObject();
		errorEvent.put("ErrorCode", 0);

		String serviceId = "Error";
		mid = aliiot.eventPost(serviceId, errorEvent.toString());
		System.out.println("mid " + mid);

	}
}
