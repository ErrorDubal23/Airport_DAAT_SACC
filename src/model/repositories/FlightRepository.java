/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositories;

import java.util.List;
import model.entities.Flight;
import model.repositories.impl.BaseRepositoryImpl;

/**
 *
 * @author dubalaguilar
 */
public class FlightRepository extends BaseRepositoryImpl<Flight, String> {
    private static FlightRepository instance;

    private FlightRepository() {}

    public static FlightRepository getInstance() {
        if (instance == null) {
            instance = new FlightRepository();
        }
        return instance;
    }

    @Override
    protected String getId(Flight flight) {
        return flight.getId();
    }

   
    public List<Flight> findByPlaneId(String planeId) {
        return items.stream()
                .filter(flight -> flight.getPlane().getId().equals(planeId))
                .toList();
    }

    public List<Flight> findByDepartureLocation(String locationId) {
        return items.stream()
                .filter(flight -> flight.getDepartureLocation().getAirportId().equals(locationId))
                .toList();
    }
}