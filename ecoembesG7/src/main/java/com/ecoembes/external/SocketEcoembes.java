package com.ecoembes.external;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Component
public class SocketEcoembes implements Gateway {

    private static SocketEcoembes instance;
    private Thread hilo;
    private volatile boolean running = true;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Value("${socket.ip}")
    private String serverIP;

    @Value("${socket.port}")
    private int serverPort;

    public SocketEcoembes() {
        instance = this;
    }

    public static SocketEcoembes getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        hilo = new Thread(() -> {
            while (running) {
                try {
                    System.out.println("Conectando a " + serverIP + ":" + serverPort);
                    socket = new Socket(serverIP, serverPort);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());

                    System.out.println("SocketEcoembes conectado exitosamente");

                    while (running && !socket.isClosed()) {
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e){
                  System.out.println("Hilo detenido mientras dormía");
                  cerrarConexion();
                } catch (Exception e) {
                    System.err.println("Error en SocketEcoembes: " + e.getMessage());
                    cerrarConexion();

                    if (running) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        });
        hilo.setDaemon(false);
        hilo.start();
    }

    public synchronized String enviar(String mensaje) {
        try {
            while ((socket == null || socket.isClosed())) {
                Thread.sleep(100);
            }

            System.out.println("Enviando query: " + mensaje);
            out.writeUTF(mensaje);

            String respuesta = in.readUTF();
            System.out.println("Respuesta recibida: " + respuesta);

            return respuesta;

        } catch (Exception e) {
            cerrarConexion();
            throw new RuntimeException("Error al comunicar con ContSocket: " + e.getMessage(), e);
        }
    }

    private void cerrarConexion() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Iniciando shutdown de SocketEcoembes");
        running = false;

        if (hilo != null) {
            hilo.interrupt();
            try {
                hilo.join(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        cerrarConexion();
        System.out.println("SocketEcoembes cerrado");
    }
}
