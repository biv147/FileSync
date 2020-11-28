package com.example.data;

import java.io.Serializable;

public class Data implements Serializable{

    private String name;
    private byte[] file;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFile(byte[] file){
        this.file = file;
    }

    public byte[] getFile(){
        return file;
    }

}
