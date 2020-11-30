package Client;

import Data.Data;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class FileClient {


    public static void main(String args[]) {
        try {
            //create a client socket connection
            Socket client = new Socket("localhost", 3500);
            System.out.println("Connected to server");
            //create the input stream to recieve the data
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            //create the data object and store the file into that object
            Data data = (Data) in.readObject();
            //get the name of the file
            String name = data.getName();
            //create a file output stream to create a file on computer
            FileOutputStream out = new FileOutputStream("C:\\Users\\izien\\Documents\\Distributed Systems\\File-Synchronizer\\src\\Client\\"+name);
            //get the data out from the 'data' object and write to computer
            out.write(data.getFile());
            System.out.println("recieved a file");
            //close the connection
            out.close();
            
        }catch (Exception e) {
            System.out.println("Did not connect");
            System.out.println(e.getMessage());
        }
    }
}
