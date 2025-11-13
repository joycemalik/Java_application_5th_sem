// com/rental/server/RentalServer.java
package com.rental.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Multi-threaded TCP server for Car and Bike Rental System
 * Supports deployment on Railway by reading PORT from environment variable
 * For local development, defaults to port 5000
 */
public class RentalServer {
    private static final int DEFAULT_PORT = 5000;

    public static void main(String[] args) {
        // Railway provides PORT via environment variable
        // For local development, fall back to DEFAULT_PORT
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isEmpty()) 
                   ? Integer.parseInt(portEnv) 
                   : DEFAULT_PORT;

        System.out.println("===========================================");
        System.out.println("  Car & Bike Rental Management System");
        System.out.println("  Server starting on port " + port);
        System.out.println("  Environment: " + (portEnv != null ? "Production (Railway)" : "Local Development"));
        System.out.println("===========================================");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[SERVER] Successfully bound to port " + port);
            System.out.println("[SERVER] Waiting for client connections...\n");

            // Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[SERVER] New client connected: " + 
                                   clientSocket.getInetAddress().getHostAddress() + 
                                   ":" + clientSocket.getPort());

                // Create a new thread to handle this client
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(handler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
