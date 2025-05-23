/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.entities.Location;
import model.services.LocationService;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class LocationController {
    private final LocationService locationService;

    public LocationController() {
        this.locationService = LocationService.getInstance();
    }

    public Response registerLocation(String airportId, String name, String city, String country, String latitude,String longitude) {
        
        if (airportId == null || name == null || city == null || country == null || 
            latitude == null || longitude == null) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos son requeridos");
        }

        return locationService.registerLocation(
            airportId.trim().toUpperCase(),
            name.trim(),
            city.trim(),
            country.trim(),
            latitude.trim(),
            longitude.trim()
        );
    }

    public Response getAllLocations() {
        try {
            List<Location> locations = locationService.getAllLocationsSorted();
            return Response.success(locations);
        } catch (Exception e) {
            return Response.error(
                ResponseStatus.INTERNAL_ERROR,
                "Error al obtener la lista de aeropuertos: " + e.getMessage()
            );
        }
    }

    public Response getLocation(String airportId) {
        if (airportId == null || airportId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de aeropuerto requerido");
        }

        return locationService.getLocationById(airportId.trim().toUpperCase());
    }
}
