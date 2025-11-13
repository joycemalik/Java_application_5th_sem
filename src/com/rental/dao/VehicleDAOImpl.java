// com/rental/dao/VehicleDAOImpl.java
package com.rental.dao;

import com.rental.model.Vehicle;
import com.rental.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of VehicleDAO interface
 * Handles all database operations related to vehicles
 */
public class VehicleDAOImpl implements VehicleDAO {
    
    @Override
    public List<Vehicle> getAvailableVehiclesByType(String type) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE type = ? AND available = TRUE";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type.toUpperCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("reg_number"),
                    rs.getDouble("price_per_day"),
                    rs.getBoolean("available")
                );
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return vehicleList;
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("reg_number"),
                    rs.getDouble("price_per_day"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicle by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean updateVehicleAvailability(int vehicleId, boolean available) {
        String sql = "UPDATE vehicles SET available = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, available);
            ps.setInt(2, vehicleId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating vehicle availability: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
