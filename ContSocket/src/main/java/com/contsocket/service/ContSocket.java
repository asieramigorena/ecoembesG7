package com.contsocket.service;

import com.contsocket.entity.Capacidad;
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
import java.time.LocalDate;

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
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(serverPort);
                System.out.println("Socket servidor iniciado en puerto " + serverPort);

                while (running) {
                    socket = serverSocket.accept();
                    handleClient(socket);
                }
            } catch (IOException ex) {
                if (running) {
                    throw new RuntimeException(ex);
                }
            } finally {
                try {
                    assert socket != null;
                    socket.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                shutdown();
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (socket; DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            try {

                String mensaje = in.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);
                String respuesta = processGetQuery(mensaje);
                out.writeUTF(respuesta);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String processGetQuery(String query) {
        if (!query.toUpperCase().startsWith("GET") || !query.toUpperCase().contains("PUT")) {
            return "ERROR;Formato inválido";
        }

        String[] parts = query.split(" ");
        if (parts.length < 2) {
            return "ERROR;Query incompleta";
        }

        String path = parts[1];

        if (path.startsWith("/capacidad/") || path.startsWith("capacidad/")) {
            if (parts[0].equalsIgnoreCase("PUT")) {
                String[] datos = path.split("/");
                Capacidad c = new Capacidad(LocalDate.parse(datos[1]), Double.parseDouble(datos[2]));
                return capacidadService.save(c).toString();
            } else if (parts[0].equalsIgnoreCase("GET")){
                String fecha = path.substring(path.lastIndexOf("/") + 1);
                return capacidadService.getCapacidadPorFecha(fecha);
            } else {
                return "ERROR;Método no soportado";
            }
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
            System.err.println(e.getMessage());
        }
    }
}
