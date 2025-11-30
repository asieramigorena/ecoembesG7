package com.ecoembes.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketEcoembes {
    private static String serverIP = "127.0.0.1";
    private static int serverPort = 9500;
    private static SocketEcoembes socketEcoembes;

    private SocketEcoembes(String serverIP, int serverPort) {
        SocketEcoembes.serverIP = serverIP;
        SocketEcoembes.serverPort = serverPort;
    }
    
    public static synchronized SocketEcoembes getInstance(){
        if (socketEcoembes == null) {
            socketEcoembes = new SocketEcoembes(serverIP, serverPort);
        }
        return socketEcoembes;
    }

    public void enviar(String mensaje) {
        try {
            Socket socket = new Socket(serverIP, serverPort);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println(mensaje);
            out.writeUTF(mensaje);
            mensaje = in.readUTF();
            System.out.println(mensaje);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
