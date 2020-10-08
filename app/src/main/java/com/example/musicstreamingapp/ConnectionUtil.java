package com.example.musicstreamingapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class ConnectionUtil extends AsyncTask<Serializable, Void, Object> {
    @Override
    protected Object doInBackground(Serializable... serializables) {
        String hostName = (String) serializables[0];
        int port = (int) serializables[1];
        Object data = serializables[2];
        System.out.println("Async got " + hostName + " " + port + " " + data);

        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();
        Object response = new Object();
        try {
            socket.connect(socketAddress, 2000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Server wrote output data : " + data);
            out.writeObject(data);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            response = in.readObject();
            System.out.println("Server read input data : " + response);
            out.close();
            in.close();
            socket.close();

        } catch (SocketTimeoutException exception) {
            System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
            exception.printStackTrace();
        } catch (IOException exception) {
            System.out.println("IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static Object sendDataToServer(String hostName, int port, Object data) {

        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();
        Object response = new Object();
        try {
            socket.connect(socketAddress, 2000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Server wrote output data : " + data);
            out.writeObject(data);
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            response = in.readObject();
            System.out.println("Server read input data : " + response);
            out.close();
            in.close();
            socket.close();

        } catch (SocketTimeoutException exception) {
            System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
            exception.printStackTrace();
        } catch (IOException exception) {
            System.out.println("IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;

    }

}
