package Client;

import Data.Data;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class FileClient {

	String ip_address;
	int portNum;
	
	public FileClient(String ipa, int pn) {
			ip_address = ipa;
			portNum = pn;	
	}
	
	public void run() {
		try {
			Socket client = new Socket(ip_address, portNum);
			System.out.println("Connected to FileServer");
			 //create the input stream to receive the data
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            //for sending message to server
            PrintWriter serverOut = new PrintWriter(client.getOutputStream(), true);
            
            //create the data object and store the file into that object
            Data data = (Data) in.readObject();
            //get the name of the file
            String name = data.getName();
            //create a file output stream to create a file on computer
            FileOutputStream out = new FileOutputStream("C:\\Users\\izien\\Documents\\Distributed Systems\\File-Synchronizer\\src\\Client\\"+name);
            //get the data out from the 'data' object and write to computer
            out.write(data.getFile());
            System.out.println("Recieved a file");
            //send message to server
            serverOut.println("true");
            //close the connection
            out.close();
            System.out.println("FileClient closed");            
		} catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Did not connect");
	         System.out.println(e.getMessage());
		}
		
	}
}
