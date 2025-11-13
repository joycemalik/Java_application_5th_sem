// com/rental/dao/VehicleDAO.java
package com.rental.dao;

import com.rental.model.Vehicle;
import java.util.List;

/**
 * Data Access Object interface for Vehicle operations
 */
public interface VehicleDAO {
    /**
     * Get all available vehicles of a specific type
     * @param type "CAR" or "BIKE"
     * @return List of available vehicles
     */
    List<Vehicle> getAvailableVehiclesByType(String type);
    
    /**
     * Get vehicle by ID
     * @param id vehicle ID
     * @return Vehicle object if found, null otherwise
     */
    Vehicle getVehicleById(int id);
    
    /**
     * Update vehicle availability status
     * @param vehicleId vehicle ID
     * @param available new availability status
     * @return true if update successful, false otherwise
     */
    boolean updateVehicleAvailability(int vehicleId, boolean available);
}
