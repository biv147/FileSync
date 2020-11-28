package com.example.client;

import com.example.data.Data;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {


    public static void main(String args[]) {
        try {
            //create a client socket connection
            Socket client = new Socket("localhost", 2500);
            System.out.println("Connected to server");
            //create the input stream to recieve the data
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            //create the data object and store the file into that object
            Data data = (Data) in.readObject();
            //get the name of the file
            String name = data.getName();
            //create a file output stream to create a file on computer
            FileOutputStream out = new FileOutputStream(name);
            //get the data out from the 'data' object and write to computer
            out.write(data.getFile());
            System.out.println("recieved a file");
            //close the connection
            out.close();

        }catch (Exception e) {
            System.out.println("Did not connect");
        }
    }
}
