import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SyncClient {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket client = context.socket(SocketType.SUB);
		client.connect("tcp://localhost:5555");
		System.out.println("Client Running...");
		Scanner input = new Scanner(System.in);

        System.out.print("Enter your group number: ");
        int groupNum = input.nextInt();
        String subscription = String.format("%d", groupNum);
        
        System.out.println("Subscribing to: " + subscription);
        client.subscribe(subscription.getBytes(ZMQ.CHARSET));
        System.out.println("SUB SUCCESS");
        
        String topic = client.recvStr();
        if (topic != null) {
        	String data = client.recvStr();
            assert (topic.equals(subscription));
            System.out.println(data);
        }
		
		client.close();
		context.term();
	}
}
