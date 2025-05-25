/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.entities.Location;
import model.services.LocationService;
import util.constants.ErrorMessages;
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

    public Response registerLocation(
            String airportId,
            String name,
            String city,
            String country,
            String latitudeStr,
            String longitudeStr
    ) {
        // Validaciones básicas de campos vacíos
        if (airportId.isEmpty()) {
            return Response.fieldRequired("ID del aeropuerto");
        }
        if (name.isEmpty()) {
            return Response.fieldRequired("nombre");
        }
        if (city.isEmpty()) {
            return Response.fieldRequired("ciudad");
        }
        if (country.isEmpty()) {
            return Response.fieldRequired("país");
        }
        if (latitudeStr.isEmpty()) {
            return Response.fieldRequired("latitud");
        }
        if (longitudeStr.isEmpty()) {
            return Response.fieldRequired("longitud");
        }

        // Formatear datos
        airportId = airportId.trim().toUpperCase();
        name = name.trim();
        city = city.trim();
        country = country.trim();

        // Validar formato de ID (3 letras mayúsculas)
        if (!airportId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El ID del aeropuerto debe ser 3 letras mayúsculas");
        }

        try {
            // Convertir coordenadas
            double latitude = Double.parseDouble(latitudeStr.trim());
            double longitude = Double.parseDouble(longitudeStr.trim());

            // Pasar al servicio para validaciones específicas
            return locationService.registerLocation(
                    airportId, name, city, country, latitude, longitude
            );

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Las coordenadas deben ser valores numéricos");
        }
    }

    public Response getAllLocations() {
        try {
            return Response.success(locationService.getAllLocationsSorted());
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener ubicaciones: " + e.getMessage());
        }
    }

    public Response getLocationById(String airportId) {
        if (airportId == null || airportId.isEmpty()) {
            return Response.fieldRequired("ID del aeropuerto");
        }
        return locationService.getLocationById(airportId.trim().toUpperCase());
    }
}
