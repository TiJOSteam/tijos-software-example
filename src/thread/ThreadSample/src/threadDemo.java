import java.util.LinkedList;

/** 
 * 多线程例程： 生产者-消费者
 * Producer-Consumer in Java. 
 */

class producerThread extends Thread
{
	LinkedList<Object> _list;
	
	boolean _stop = false;

	public producerThread(LinkedList<Object> list)
	{
		_list = list;
	}
	
	public void stopRequest()
	{
		_stop = true;
	}

	public void run() {
		int len = 0;
		_stop = false;
		while(!_stop)
		{
			synchronized(_list) {
				Object justProduced = new Object();
				_list.addFirst(justProduced);
				len = _list.size();

				System.out.println("Produce a new object " + justProduced);
				System.out.println("List size now " + len);

				_list.notifyAll();
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}

class consumerThread extends Thread
{
	LinkedList<Object> _list;
	boolean _stop = false;
	
	public consumerThread(LinkedList<Object> list)
	{
		_list = list;
	}
	
	public void stopRequest()
	{
		_stop = true;
	}
	public void run()
	{
		Object obj = null;
		int len = 0;
		while (!_stop) 
		{
			synchronized(_list) {
				try {
					_list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				while(_list.size() > 0)
				{
					obj = _list.removeLast();
					len = _list.size();
					System.out.println("Consuming object " + obj);
					System.out.println("List size now " + len);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class threadDemo {

	protected static LinkedList<Object> list = new LinkedList<Object>();

	public static void main(String[] args)
	{
		try
		{
			producerThread  producer = new producerThread(list);
			consumerThread  consumer = new consumerThread(list);

			producer.start();
			consumer.start();

			Thread.sleep(10000);
			
			producer.stopRequest();
			consumer.stopRequest();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
