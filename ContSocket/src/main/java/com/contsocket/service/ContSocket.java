package com.contsocket.service;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ContSocket implements CommandLineRunner {

    @Value("${contsocket.socket.port}")
    private int serverPort;

    @Autowired
    private CapacidadService capacidadService;

    private ServerSocket serverSocket;
    private volatile boolean running = true;

    @Override
    public void run(String... args) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(serverPort);
                System.out.println("Socket servidor iniciado en puerto " + serverPort);

                while (running) {
                    Socket socket = serverSocket.accept();
                    handleClient(socket);
                }
            } catch (IOException ex) {
                if (running) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String mensaje = in.readUTF();
            System.out.println("Mensaje recibido: " + mensaje);
            String respuesta = processGetQuery(mensaje);
            out.writeUTF(respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processGetQuery(String query) {
        if (!query.toUpperCase().startsWith("GET")) {
            return "ERROR;Formato inválido";
        }

        String[] parts = query.split(" ");
        if (parts.length < 2) {
            return "ERROR;Query incompleta";
        }

        String path = parts[1];

        if (path.startsWith("/capacidades/") || path.startsWith("capacidades/")) {
            String fecha = path.substring(path.lastIndexOf("/") + 1);
            return capacidadService.getCapacidadPorFecha(fecha);
        }

        return "ERROR;Recurso no encontrado";
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Cerrando socket servidor...");
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
