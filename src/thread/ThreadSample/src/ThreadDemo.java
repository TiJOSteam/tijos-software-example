import java.util.LinkedList;

/**
 * 多线程例程： 生产者-消费者
 * Producer-Consumer in Java.
 *
 * @author TiJOS
 */

class ProducerThread extends Thread {
    LinkedList<Object> objList;

    boolean stop = false;

    public ProducerThread(LinkedList<Object> list) {
        objList = list;
    }

    public void stopRequest() {
        stop = true;
    }

    public void run() {
        int len = 0;
        stop = false;
        while (!stop) {
            synchronized (objList) {
                Object justProduced = new Object();
                objList.addFirst(justProduced);
                len = objList.size();

                System.out.println("Produce a new object " + justProduced);
                System.out.println("List size now " + len);

                objList.notifyAll();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class ConsumerThread extends Thread {
    LinkedList<Object> objList;
    boolean stop = false;

    public ConsumerThread(LinkedList<Object> list) {
        objList = list;
    }

    public void stopRequest() {
        stop = true;
    }

    public void run() {
        Object obj = null;
        int len = 0;
        while (!stop) {
            synchronized (objList) {
                try {
                    objList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (objList.size() > 0) {
                    obj = objList.removeLast();
                    len = objList.size();
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

public class ThreadDemo {

    protected static LinkedList<Object> list = new LinkedList<Object>();

    public static void main(String[] args) {
        try {
            ProducerThread producer = new ProducerThread(list);
            ConsumerThread consumer = new ConsumerThread(list);

            producer.start();
            consumer.start();

            Thread.sleep(10000);

            producer.stopRequest();
            consumer.stopRequest();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
