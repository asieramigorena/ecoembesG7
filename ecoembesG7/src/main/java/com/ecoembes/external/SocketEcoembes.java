package com.ecoembes.external;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Component
public class SocketEcoembes implements Gateway{

    private static SocketEcoembes instance;
    private Thread hilo;

    @Value("${socket.ip}")
    private String serverIP;

    @Value("${socket.port}")
    private int serverPort;

    private SocketEcoembes(){
        init();
    }

    public static SocketEcoembes getInstance(){
        if (instance == null){
            instance = new SocketEcoembes();
        }
        return instance;
    }

    private void init(){
        hilo = new Thread(() -> {
            try {
                Thread.sleep(3000);

                System.out.println("SocketEcoembes iniciado");
                System.out.println("Conectando a " + serverIP + ":" + serverPort);

            } catch (Exception e) {
                System.err.println("Error en SocketEcoembes: " + e.getMessage());
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }

    public String enviar(String mensaje) {
        try (Socket socket = new Socket(serverIP, serverPort);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Enviando query: " + mensaje);
            out.writeUTF(mensaje);

            String respuesta = in.readUTF();
            System.out.println("Respuesta recibida: " + respuesta);

            return respuesta;

        } catch (IOException e) {
            throw new RuntimeException("Error al comunicar con ContSocket: " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void shutdown(){
        if (hilo != null){
            hilo.interrupt();
        }
    }
}
