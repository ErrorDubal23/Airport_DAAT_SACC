/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import model.entities.Flight;
import model.entities.Location;
import model.entities.Passenger;
import model.entities.Plane;
import model.repositories.FlightRepository;
import model.repositories.LocationRepository;
import model.repositories.PassengerRepository;
import model.repositories.PlaneRepository;
import model.repositories.impl.Repository;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.helpers.FlightValidator;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class FlightService {

    private final Repository<Flight, String> flightRepository;
    private final Repository<Plane, String> planeRepository;
    private final Repository<Location, String> locationRepository;

    public FlightService(Repository<Flight, String> flightRepository, Repository<Plane, String> planeRepository, Repository<Location, String> locationRepository) {
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.locationRepository = locationRepository;
    }

    private static FlightService instance;

    public static FlightService getInstance() {
        if (instance == null) {
            instance = new FlightService(FlightRepository.getInstance(), PlaneRepository.getInstance(), LocationRepository.getInstance());
        }
        return instance;
    }

    public Response registerFlight(String flightId, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival, int hoursDurationScale, int minutesDurationScale) {
        // Validación inicial
        Response validation = FlightValidator.validate(
                flightId, planeId, departureLocationId, arrivalLocationId, scaleLocationId,
                departureDate, hoursDurationArrival, minutesDurationArrival,
                hoursDurationScale, minutesDurationScale
        );
        if (!validation.isSuccess()) {
            return validation;
        }

        // Obtener entidades relacionadas
        Optional<Plane> plane = planeRepository.findById(planeId);
        Optional<Location> departure = locationRepository.findById(departureLocationId);
        Optional<Location> arrival = locationRepository.findById(arrivalLocationId);
        Optional<Location> scale = scaleLocationId != null
                ? locationRepository.findById(scaleLocationId) : Optional.empty();

        // Verificar existencia
        if (plane.isEmpty() || departure.isEmpty() || arrival.isEmpty()) {
            return Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.RELATED_RESOURCE_NOT_FOUND);
        }

        // Crear el vuelo
        Flight flight;
        if (scale.isEmpty()) {
            flight = new Flight(
                    flightId, plane.get(), departure.get(), arrival.get(),
                    departureDate, hoursDurationArrival, minutesDurationArrival
            );
        } else {
            flight = new Flight(
                    flightId, plane.get(), departure.get(), scale.get(), arrival.get(),
                    departureDate, hoursDurationArrival, minutesDurationArrival,
                    hoursDurationScale, minutesDurationScale
            );
        }

        // Guardar
        flightRepository.add(flight);
        return Response.success(ResponseStatus.CREATED, "Vuelo registrado", flight);

    }

    public Response delayFlight(String flightId, int hours, int minutes) {
        if (hours < 0 || minutes < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.DELAY_TIME_INVALID);
        }

        return flightRepository.findById(flightId)
                .map(flight -> {
                    flight.delay(hours, minutes);
                    return Response.success("Vuelo retrasado", flight);
                })
                .orElse(Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.FLIGHT_NOT_FOUND));
    }

    public List<Flight> getAllFlightsSorted() {
        List<Flight> flights = flightRepository.findAll();
        flights.sort(Comparator.comparing(Flight::getDepartureDate));
        return flights;
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
        try {
            // Validación de IDs
            if (passengerId <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
            }
            if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_ID_FORMAT);
            }

            // Obtener pasajero y vuelo
            Optional<Passenger> passengerOpt = PassengerRepository.getInstance().findById(passengerId);
            Optional<Flight> flightOpt = flightRepository.findById(flightId);

            if (passengerOpt.isEmpty()) {
                return Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.PASSENGER_NOT_FOUND);
            }
            if (flightOpt.isEmpty()) {
                return Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.FLIGHT_NOT_FOUND);
            }

            Passenger passenger = passengerOpt.get();
            Flight flight = flightOpt.get();

            // Verificar capacidad del avión
            if (flight.getNumPassengers() >= flight.getPlane().getMaxCapacity()) {
                return Response.error(ResponseStatus.CONFLICT, ErrorMessages.FLIGHT_FULL_CAPACITY);
            }

            // Verificar si el pasajero ya está en el vuelo (usando el nuevo getter)
            boolean isDuplicate = flight.getPassengers().stream()
                    .anyMatch(p -> p.getId() == passengerId);

            if (isDuplicate) {
                return Response.error(ResponseStatus.CONFLICT, ErrorMessages.PASSENGER_ALREADY_IN_FLIGHT);
            }

            // Añadir relación bidireccional
            passenger.addFlight(flight);
            flight.addPassenger(passenger);

            return Response.success("Pasajero añadido al vuelo exitosamente", flight);

        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR, "Error inesperado: " + e.getMessage());
        }
    }
    
    public Optional<Flight> getFlightById(String flightId) {
        return flightRepository.findById(flightId);
    }

    
}
