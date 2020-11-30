package Server;

import Data.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    public static void main(String args[]) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //create the server socket at port 2500
                    ServerSocket server = new ServerSocket(3500);
                	System.out.println("Server is starting");
                    Socket client = server.accept(); //accept the incoming client

                    //find the file we are going to send
                    File file = new File("C:\\Users\\izien\\Documents\\Distributed Systems\\File-Synchronizer\\src\\Server\\sample.txt");
                    //create the stream to read the file
                    FileInputStream in = new FileInputStream(file);
                    byte b[] = new byte[in.available()];
                    in.read(b);

                    //put the file in the data object
                    Data data = new Data();
                    data.setName("sample.txt");
                    data.setFile(b);

                    //create the output stream to send the file
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    out.writeObject(data);
                    out.flush();
                    System.out.println("Sending file....");
                    while(!(client.isClosed())) {
                    	
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
