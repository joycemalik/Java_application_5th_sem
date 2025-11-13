// com/rental/server/ClientHandler.java
package com.rental.server;

import com.rental.dao.UserDAO;
import com.rental.dao.UserDAOImpl;
import com.rental.dao.VehicleDAO;
import com.rental.dao.VehicleDAOImpl;
import com.rental.model.User;
import com.rental.model.Vehicle;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Handles communication with a single client
 * Runs in a separate thread for each connected client
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private UserDAO userDAO;
    private VehicleDAO vehicleDAO;
    private User currentUser;
    private String clientAddress;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.userDAO = new UserDAOImpl();
        this.vehicleDAO = new VehicleDAOImpl();
        this.currentUser = null;
        this.clientAddress = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        System.out.println("[" + clientAddress + "] Client handler started");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send welcome message
            out.println("WELCOME");

            String line;
            // Process commands from client
            while ((line = in.readLine()) != null) {
                System.out.println("[" + clientAddress + "] Received: " + line);
                String response = handleCommand(line);
                out.println(response);
                System.out.println("[" + clientAddress + "] Sent: " + response);
            }

        } catch (IOException e) {
            System.err.println("[" + clientAddress + "] Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("[" + clientAddress + "] Connection closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parse and handle client commands
     * Protocol: COMMAND|param1|param2|...
     */
    private String handleCommand(String line) {
        String[] parts = line.split("\\|");
        
        if (parts.length == 0) {
            return "ERROR|Empty command";
        }

        String cmd = parts[0].toUpperCase();

        switch (cmd) {
            case "LOGIN":
                if (parts.length < 3) return "ERROR|Invalid LOGIN format. Use: LOGIN|email|password";
                return handleLogin(parts[1], parts[2]);
                
            case "REGISTER":
                if (parts.length < 4) return "ERROR|Invalid REGISTER format. Use: REGISTER|name|email|password";
                return handleRegister(parts[1], parts[2], parts[3]);
                
            case "LIST_VEHICLES":
                if (currentUser == null) return "ERROR|Not logged in";
                if (parts.length < 2) return "ERROR|Type required. Use: LIST_VEHICLES|CAR or LIST_VEHICLES|BIKE";
                return handleListVehicles(parts[1]);
                
            case "LOGOUT":
                return handleLogout();
                
            default:
                return "ERROR|Unknown command: " + cmd;
        }
    }

    /**
     * Handle LOGIN command
     */
    private String handleLogin(String email, String password) {
        User user = userDAO.login(email, password);
        
        if (user == null) {
            return "ERROR|Login failed. Invalid email or password.";
        } else {
            this.currentUser = user;
            System.out.println("[" + clientAddress + "] User logged in: " + user.getName() + " (" + user.getRole() + ")");
            return "OK|LOGIN|" + user.getName() + "|" + user.getRole();
        }
    }

    /**
     * Handle REGISTER command
     */
    private String handleRegister(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CUSTOMER"); // Default role for new users
        
        User createdUser = userDAO.register(user);
        
        if (createdUser == null) {
            return "ERROR|Registration failed. Email may already be in use.";
        }
        
        System.out.println("[" + clientAddress + "] New user registered: " + name + " (ID: " + createdUser.getId() + ")");
        return "OK|REGISTER|" + createdUser.getId();
    }

    /**
     * Handle LIST_VEHICLES command
     * Returns available vehicles of specified type (CAR or BIKE)
     */
    private String handleListVehicles(String type) {
        List<Vehicle> vehicles = vehicleDAO.getAvailableVehiclesByType(type);
        
        if (vehicles.isEmpty()) {
            return "OK|LIST_VEHICLES|0";
        }

        // Format: OK|LIST_VEHICLES|count|id,brand,model,regNum,price|id,brand,model,regNum,price|...
        StringBuilder response = new StringBuilder();
        response.append("OK|LIST_VEHICLES|").append(vehicles.size());
        
        for (Vehicle v : vehicles) {
            response.append("|")
                    .append(v.getId()).append(",")
                    .append(v.getBrand()).append(",")
                    .append(v.getModel()).append(",")
                    .append(v.getRegNumber()).append(",")
                    .append(v.getPricePerDay());
        }
        
        return response.toString();
    }

    /**
     * Handle LOGOUT command
     */
    private String handleLogout() {
        if (currentUser != null) {
            System.out.println("[" + clientAddress + "] User logged out: " + currentUser.getName());
            currentUser = null;
        }
        return "OK|LOGOUT";
    }
}
