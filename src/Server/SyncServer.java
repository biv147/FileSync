package Server;


import java.io.File;
import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SyncServer {
	long timeStamp;
	File file;
	
	public SyncServer(File file)  {
		this.file = file;
		this.timeStamp = file.lastModified();
	}
	
	//function for sending from server to client
	
	public static void main(String[] args) throws InterruptedException {
		
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket server = context.socket(SocketType.PUB);
		server.bind("tcp://*:5555");
		Thread.sleep(1000);
		System.out.println("Server Running...");
		int groupNum = 1;
		
		server.send(String.format("%d", groupNum), ZMQ.SNDMORE);
		
		//setting the first timestamp
//		System.out.print("Enter your file's address: ");
//        Scanner file_input = new Scanner(System.in);
        SyncServer newServer = new SyncServer(new File("C:\\Users\\izien\\Documents\\Distributed Systems\\File-Synchronizer\\src\\Server\\sample.txt"));
        //!Thread.currentThread().isInterrupted()
		 while (true) {
			 	Thread.sleep(500);
			 	long timeStamp = newServer.file.lastModified();
	        	if(newServer.timeStamp != timeStamp) {
	        		newServer.timeStamp = timeStamp;
	        		String data = "new";
	                String dest = String.format("%d", groupNum);
	                server.send(dest, ZMQ.SNDMORE);
	                server.send(data);		
	        		System.out.println("File's changed!");
	        	}
//	            String dest = String.format("%d", groupNum);
//	            server.send(dest, ZMQ.SNDMORE);
//	            server.send("Welcome to group "+ groupNum);
	        }
//		server.close();
//		context.term();
	}
}
