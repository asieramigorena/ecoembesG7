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
                if (running) throw new RuntimeException(ex);
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                shutdown();
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (socket;
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String mensaje = in.readUTF();
            System.out.println("Mensaje recibido: " + mensaje);

            String respuesta = processQuery(mensaje);

            out.writeUTF(respuesta);
            out.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Solución de ChatGpt
    /**
     * Formatos soportados (solo estos 2):
     * - capacidad/yyyy-MM-dd              -> GET implícito
     * - capacidad/yyyy-MM-dd/valor        -> PUT implícito
     */
    private String processQuery(String raw) {
        if (raw == null) return "ERROR;Query vacía";

        String q = raw.trim();
        if (q.isEmpty()) return "ERROR;Query vacía";

        // admitir con o sin "/" inicial
        if (q.startsWith("/")) q = q.substring(1);

        String[] p = q.split("/");
        if (p.length < 2) return "ERROR;Query incompleta. Use capacidad/fecha o capacidad/fecha/valor";

        if (!"capacidad".equalsIgnoreCase(p[0].trim())) {
            return "ERROR;Recurso no encontrado: " + p[0];
        }

        String fechaStr = p[1].trim();
        if (fechaStr.isEmpty()) return "ERROR;Fecha vacía";

        // GET implícito: capacidad/fecha
        if (p.length == 2) {
            return capacidadService.getCapacidadPorFecha(fechaStr);
        }

        // PUT implícito: capacidad/fecha/valor
        if (p.length == 3) {
            String valorStr = p[2].trim();
            if (valorStr.isEmpty()) return "ERROR;Valor vacío";

            try {
                LocalDate fecha = LocalDate.parse(fechaStr);
                double valor = Double.parseDouble(valorStr);

                Capacidad c = capacidadService.findById(fecha)
                        .orElseGet(() -> new Capacidad(fecha, valor));
                c.setCapacidad(valor);

                Capacidad guardada = capacidadService.save(c);
                return guardada.getId() + ";" + guardada.getCapacidad();

            } catch (Exception e) {
                return "ERROR;Formato inválido";
            }
        }

        return "ERROR;Formato inválido. Use capacidad/fecha o capacidad/fecha/valor";
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Cerrando socket servidor...");
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
