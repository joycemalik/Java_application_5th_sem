// com/rental/client/RentalClient.java
package com.rental.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Console-based client for Car and Bike Rental System
 * Connects to the server and provides a menu-driven interface
 * 
 * Configuration:
 * - For LOCAL testing: use "localhost" and port 5000
 * - For RAILWAY deployment: change SERVER_HOST to your Railway URL
 *   Example: "your-service-name.up.railway.app"
 */
public class RentalClient {
    // ============================================================
    // CONFIGURATION - Change these for Railway deployment
    // ============================================================
    
    // For LOCAL development:
    // private static final String SERVER_HOST = "localhost";
    // private static final int SERVER_PORT = 5000;
    
    // For RAILWAY deployment:
    private static final String SERVER_HOST = "java-group1-asgn.up.railway.app";
    private static final int SERVER_PORT = 443;
    
    // ============================================================

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  Car & Bike Rental Management System");
        System.out.println("  Client Application");
        System.out.println("===========================================\n");

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);
            
            // Read welcome message from server
            String welcome = in.readLine();
            System.out.println("Server: " + welcome + "\n");

            boolean loggedIn = false;
            boolean running = true;

            while (running) {
                displayMenu(loggedIn);
                System.out.print("Enter your choice: ");
                
                String choiceStr = scanner.nextLine().trim();
                int choice;
                
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    System.out.println("\n[ERROR] Please enter a valid number.\n");
                    continue;
                }

                switch (choice) {
                    case 1: // Register
                        loggedIn = handleRegister(scanner, out, in);
                        break;
                        
                    case 2: // Login
                        loggedIn = handleLogin(scanner, out, in);
                        break;
                        
                    case 3: // List Cars
                        if (!loggedIn) {
                            System.out.println("\n[ERROR] Please login first.\n");
                            break;
                        }
                        handleListVehicles(out, in, "CAR");
                        break;
                        
                    case 4: // List Bikes
                        if (!loggedIn) {
                            System.out.println("\n[ERROR] Please login first.\n");
                            break;
                        }
                        handleListVehicles(out, in, "BIKE");
                        break;
                        
                    case 5: // Logout
                        if (!loggedIn) {
                            System.out.println("\n[ERROR] You are not logged in.\n");
                            break;
                        }
                        loggedIn = handleLogout(out, in);
                        break;
                        
                    case 0: // Exit
                        System.out.println("\nThank you for using Car & Bike Rental System!");
                        System.out.println("Goodbye!\n");
                        running = false;
                        break;
                        
                    default:
                        System.out.println("\n[ERROR] Invalid choice. Please try again.\n");
                }
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Could not connect to server: " + e.getMessage());
            System.err.println("Please make sure the server is running on " + SERVER_HOST + ":" + SERVER_PORT);
        }
    }

    /**
     * Display the main menu
     */
    private static void displayMenu(boolean loggedIn) {
        System.out.println("───────────────────────────────────────────");
        System.out.println("            MAIN MENU");
        System.out.println("───────────────────────────────────────────");
        
        if (!loggedIn) {
            System.out.println("  1. Register");
            System.out.println("  2. Login");
        } else {
            System.out.println("  3. List Available Cars");
            System.out.println("  4. List Available Bikes");
            System.out.println("  5. Logout");
        }
        
        System.out.println("  0. Exit");
        System.out.println("───────────────────────────────────────────");
    }

    /**
     * Handle user registration
     */
    private static boolean handleRegister(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("\n──── REGISTER ────");
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("\n[ERROR] All fields are required.\n");
            return false;
        }

        // Send REGISTER command to server
        out.println("REGISTER|" + name + "|" + email + "|" + password);
        
        // Read server response
        String response = in.readLine();
        System.out.println("\nServer response: " + response);

        if (response.startsWith("OK|REGISTER")) {
            String[] parts = response.split("\\|");
            System.out.println("\n[SUCCESS] Registration successful! Your User ID is: " + parts[2]);
            System.out.println("You can now login with your credentials.\n");
            return false;
        } else {
            String errorMsg = response.substring(response.indexOf("|") + 1);
            System.out.println("\n[ERROR] " + errorMsg + "\n");
            return false;
        }
    }

    /**
     * Handle user login
     */
    private static boolean handleLogin(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("\n──── LOGIN ────");
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("\n[ERROR] Email and password are required.\n");
            return false;
        }

        // Send LOGIN command to server
        out.println("LOGIN|" + email + "|" + password);
        
        // Read server response
        String response = in.readLine();

        if (response.startsWith("OK|LOGIN")) {
            String[] parts = response.split("\\|");
            String userName = parts[2];
            String userRole = parts[3];
            
            System.out.println("\n[SUCCESS] Login successful!");
            System.out.println("Welcome, " + userName + " (" + userRole + ")!\n");
            return true;
        } else {
            String errorMsg = response.substring(response.indexOf("|") + 1);
            System.out.println("\n[ERROR] " + errorMsg + "\n");
            return false;
        }
    }

    /**
     * Handle listing vehicles
     */
    private static void handleListVehicles(PrintWriter out, BufferedReader in, String type) throws IOException {
        System.out.println("\n──── AVAILABLE " + type + "S ────");
        
        // Send LIST_VEHICLES command to server
        out.println("LIST_VEHICLES|" + type);
        
        // Read server response
        String response = in.readLine();

        if (!response.startsWith("OK|LIST_VEHICLES")) {
            String errorMsg = response.substring(response.indexOf("|") + 1);
            System.out.println("[ERROR] " + errorMsg + "\n");
            return;
        }

        String[] parts = response.split("\\|");
        int count = Integer.parseInt(parts[2]);

        if (count == 0) {
            System.out.println("No " + type.toLowerCase() + "s available at the moment.\n");
            return;
        }

        System.out.println("Found " + count + " available " + type.toLowerCase() + "(s):\n");
        System.out.println("┌──────┬─────────────────────────┬─────────────┬──────────────┐");
        System.out.println("│  ID  │      Brand & Model      │  Reg Number │ Price/Day(₹) │");
        System.out.println("├──────┼─────────────────────────┼─────────────┼──────────────┤");

        for (int i = 0; i < count; i++) {
            String[] vehicleData = parts[3 + i].split(",");
            String id = vehicleData[0];
            String brand = vehicleData[1];
            String model = vehicleData[2];
            String regNumber = vehicleData[3];
            String price = vehicleData[4];

            String brandModel = brand + " " + model;
            
            System.out.printf("│ %-4s │ %-23s │ %-11s │ %12s │%n", 
                id, brandModel, regNumber, price);
        }

        System.out.println("└──────┴─────────────────────────┴─────────────┴──────────────┘\n");
    }

    /**
     * Handle user logout
     */
    private static boolean handleLogout(PrintWriter out, BufferedReader in) throws IOException {
        out.println("LOGOUT");
        String response = in.readLine();
        
        if (response.startsWith("OK|LOGOUT")) {
            System.out.println("\n[SUCCESS] Logged out successfully.\n");
            return false;
        }
        
        return true;
    }
}
