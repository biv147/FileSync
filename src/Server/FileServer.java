package Server;

import Data.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	int portNum;
	private ServerSocket server;
	int clientNo;
	
	public FileServer(int pn) {
		portNum = pn;
	}
	
	public void run(int clientNum) {
		clientNum = clientNo;
		try {
			server = new ServerSocket(portNum);
			while(true){
				System.out.println("FileServer is starting");
                Socket client = server.accept(); //accept the incoming client
                clientNo++;
                System.out.println("Accepted!");
                Connection connects = new Connection(client); //create connection object and passes client socket
				connects.start(); //calls run() function in Connection class
			}
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println("FileServer run method error: " + e.getMessage());
		}
	}
	
	class Connection extends Thread {
		Socket cs;
		String closer;
		
		public Connection(Socket cc) {
			cs = cc;
		}
		
		public void run(){
			try {
				//find the file we are going to send
	            File file = new File("C:\\Users\\izien\\Documents\\Distributed Systems\\File-Synchronizer\\src\\Server\\sample.txt");
	            //create the stream to read the file
	            FileInputStream in = new FileInputStream(file);
	            byte b[] = new byte[in.available()];
	            in.read(b);
	            
	            //for getting client message
	            BufferedReader is = new BufferedReader(new InputStreamReader(cs.getInputStream()));
	            
	          //put the file in the data object
                Data data = new Data();
                data.setName("sample.txt");
                data.setFile(b);

                //create the output stream to send the file
                ObjectOutputStream out = new ObjectOutputStream(cs.getOutputStream());
                out.writeObject(data);
                out.flush();
                System.out.println("Sending file....");
                while(closer == null) {
                	closer = is.readLine();
                }
                cs.close();
                clientNo--;
                System.out.println("FileServer closed");
//                System.out.println(clientNo);
                if(clientNo == 0) {
                	server.close();
                }
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Connection run method error: " + e.getMessage());
			}
			
		}
	}
}
