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
            double latitude,
            double longitude
    ) {
        try {
            // Validar coordenadas (4 decimales máximo)
            if (!isValidCoordinate(latitude, -90, 90) || !isValidCoordinate(longitude, -180, 180)) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Coordenadas fuera de rango válido");
            }

            // Validar decimales en coordenadas
            if (hasMoreThanFourDecimals(latitude) || hasMoreThanFourDecimals(longitude)) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Las coordenadas no pueden tener más de 4 decimales");
            }

            // Validaciones específicas con LocationValidator
            Response validation = LocationValidator.validateDomainRules(
                    airportId, name, city, country, latitude, longitude
            );
            if (!validation.isSuccess()) {
                return validation;
            }

            // Verificar duplicados
            if (locationRepository.findById(airportId).isPresent()) {
                return Response.error(ResponseStatus.CONFLICT, ErrorMessages.LOCATION_ALREADY_EXISTS);
            }

            // Crear y guardar ubicación
            Location location = new Location(airportId, name, city, country, latitude, longitude);
            locationRepository.add(location);

            return Response.success(ResponseStatus.CREATED, "Aeropuerto registrado exitosamente", location);

        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al registrar ubicación: " + e.getMessage());
        }
    }

    private boolean isValidCoordinate(double value, double min, double max) {
        return value >= min && value <= max;
    }

    private boolean hasMoreThanFourDecimals(double value) {
        String str = String.valueOf(value);
        int decimalIndex = str.indexOf('.');
        return decimalIndex != -1 && str.substring(decimalIndex + 1).length() > 4;
    }

    public List<Location> getAllLocationsSorted() {
        List<Location> locations = locationRepository.findAll();
        locations.sort(Comparator.comparing(Location::getAirportId));
        return locations;
    }

    public Response getLocationById(String airportId) {
        return locationRepository.findById(airportId)
                .map(location -> Response.success(location))
                .orElse(Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.LOCATION_NOT_FOUND));
    }
}
