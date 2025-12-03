package com.ecoembes.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Component
public class SocketEcoembes implements CommandLineRunner {

    @Value("${socket.ip}")
    private String serverIP;

    @Value("${socket.port}")
    private int serverPort;

    @Override
    public void run(String... args) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);

                System.out.println("SocketEcoembes iniciado");
                System.out.println("Conectando a " + serverIP + ":" + serverPort);

            } catch (Exception e) {
                System.err.println("Error en SocketEcoembes: " + e.getMessage());
            }
        }).start();
    }

    public String enviarGet(String recurso, String queryParams) {
        String query = "GET " + recurso;
        if (queryParams != null && !queryParams.isEmpty()) {
            query += "?" + queryParams;
        }
        return enviar(query);
    }

    public String enviarGet(String recurso) {
        return enviarGet(recurso, null);
    }

    public void enviarPut(String recurso, String body) {
        enviar("PUT " + recurso + body);
    }

    private String enviar(String mensaje) {
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
}
