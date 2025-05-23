/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.Comparator;
import java.util.List;
import model.entities.Location;
import model.repositories.LocationRepository;
import model.repositories.impl.Repository;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.helpers.LocationValidator;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class LocationService {

    private final Repository<Location, String> locationRepository;

    private static LocationService instance;

    private LocationService(Repository<Location, String> locationRepository) {
        this.locationRepository = locationRepository;
    }

    public static LocationService getInstance() {
        if (instance == null) {
            instance = new LocationService(LocationRepository.getInstance());
        }
        return instance;
    }

    public Response registerLocation(
            String airportId,
            String name,
            String city,
            String country,
            String latitude,
            String longitude
    ) {
        // Validación
        Response validation = LocationValidator.validate(
                airportId, name, city, country, latitude, longitude
        );
        if (!validation.isSuccess()) {
            return validation;
        }

        // Verificar si ya existe
        if (locationRepository.findById(airportId).isPresent()) {
            return Response.error(ResponseStatus.CONFLICT, ErrorMessages.LOCATION_ALREADY_EXISTS);
        }

        // Crear y guardar
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);
            Location location = new Location(airportId, name, city, country, lat, lon);
            locationRepository.add(location);
            return Response.success(ResponseStatus.CREATED, "Aeropuerto registrado exitosamente", location);
        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.COORDINATES_INVALID);
        }
    }
    
    public List<Location> getAllLocationsSorted() {
        List<Location> locations = locationRepository.findAll();
        locations.sort(Comparator.comparing(Location::getAirportId));
        return locations;
    }

    /**
     * Obtiene una ubicación por su ID
     */
    public Response getLocationById(String airportId) {
        return locationRepository.findById(airportId)
            .map(location -> Response.success(location))
            .orElse(Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.LOCATION_NOT_FOUND));
    }
}
