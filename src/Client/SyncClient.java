package Client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SyncClient {
	
	//for updating server with the new file
	public void fileUpdate(String from, String to) throws IOException {
		Path src = Paths.get(from);
		Path dest = Paths.get(to);
		Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
	}
	
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
        
        while(true) {
        	 String topic = client.recvStr();
             if (topic == null) {
                 break;
             }
             String data = client.recvStr();
             assert (topic.equals(subscription));
             System.out.println("Received for gorup: [" + topic + "] File status: " + data);
        }
		client.close();
		context.term();
	}
}
