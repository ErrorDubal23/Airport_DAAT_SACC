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

    private static FlightService instance;

    private FlightService(Repository<Flight, String> flightRepository, 
                        Repository<Plane, String> planeRepository,
                        Repository<Location, String> locationRepository) {
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.locationRepository = locationRepository;
    }

    public static FlightService getInstance() {
        if (instance == null) {
            instance = new FlightService(
                FlightRepository.getInstance(),
                PlaneRepository.getInstance(),
                LocationRepository.getInstance()
            );
        }
        return instance;
    }

    public Response registerFlight(
        String flightId,
        String planeId,
        String departureLocationId,
        String arrivalLocationId,
        String scaleLocationId,
        LocalDateTime departureDate,
        int hoursArrival,
        int minutesArrival,
        int hoursScale,
        int minutesScale
    ) {
        try {
            // Validar con FlightValidator
            Response validation = FlightValidator.validateFlight(
                flightId, planeId, departureLocationId, arrivalLocationId, scaleLocationId,
                departureDate, hoursArrival, minutesArrival, hoursScale, minutesScale,
                flightRepository, planeRepository, locationRepository
            );
            
            if (!validation.isSuccess()) {
                return validation;
            }

            // Obtener entidades relacionadas
            Plane plane = planeRepository.findById(planeId).get();
            Location departure = locationRepository.findById(departureLocationId).get();
            Location arrival = locationRepository.findById(arrivalLocationId).get();
            Location scale = scaleLocationId != null ? locationRepository.findById(scaleLocationId).get() : null;

            // Crear el vuelo
            Flight flight;
            if (scale == null) {
                flight = new Flight(
                    flightId, plane, departure, arrival,
                    departureDate, hoursArrival, minutesArrival
                );
            } else {
                flight = new Flight(
                    flightId, plane, departure, scale, arrival,
                    departureDate, hoursArrival, minutesArrival,
                    hoursScale, minutesScale
                );
            }

            // Guardar
            flightRepository.add(flight);
            return Response.success(ResponseStatus.CREATED, "Vuelo registrado exitosamente", flight);

        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR, "Error al registrar vuelo: " + e.getMessage());
        }
    }


    public Response delayFlight(String flightId, int hours, int minutes) {
    try {
        // Buscar el vuelo
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        
        if (flightOpt.isEmpty()) {
            return Response.error(ResponseStatus.NOT_FOUND, 
                "Vuelo no encontrado");
        }

        Flight flight = flightOpt.get();

        // Validar que el vuelo no haya despegado aún
        if (flight.getDepartureDate().isBefore(LocalDateTime.now())) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "No se puede retrasar un vuelo que ya ha despegado");
        }

        // Aplicar el retraso
        flight.delay(hours, minutes);
        
        return Response.success("Vuelo retrasado exitosamente", flight);
        
    } catch (Exception e) {
        return Response.error(ResponseStatus.INTERNAL_ERROR, 
            "Error al retrasar vuelo: " + e.getMessage());
    }
}

    public List<Flight> getAllFlightsSorted() {
        List<Flight> flights = flightRepository.findAll();
        flights.sort(Comparator.comparing(Flight::getDepartureDate));
        return flights;
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
    try {
        // 1. Validar existencia del pasajero
        Optional<Passenger> passengerOpt = PassengerRepository.getInstance().findById(passengerId);
        if (passengerOpt.isEmpty()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Pasajero no encontrado");
        }

        // 2. Validar existencia del vuelo
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        if (flightOpt.isEmpty()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Vuelo no encontrado");
        }

        Passenger passenger = passengerOpt.get();
        Flight flight = flightOpt.get();

        // 3. Validar capacidad del vuelo
        if (flight.getNumPassengers() >= flight.getPlane().getMaxCapacity()) {
            return Response.error(ResponseStatus.CONFLICT, "El vuelo ha alcanzado su capacidad máxima");
        }

        // 4. Validar que el pasajero no esté ya en el vuelo
        if (flight.getPassengers().stream().anyMatch(p -> p.getId() == passengerId)) {
            return Response.error(ResponseStatus.CONFLICT, "El pasajero ya está registrado en este vuelo");
        }

        // 5. Validar que el pasajero no tenga vuelos superpuestos
        LocalDateTime departure = flight.getDepartureDate();
        LocalDateTime arrival = flight.calculateArrivalDate();
        
        boolean hasOverlap = passenger.getFlights().stream()
            .anyMatch(f -> {
                LocalDateTime fDeparture = f.getDepartureDate();
                LocalDateTime fArrival = f.calculateArrivalDate();
                return departure.isBefore(fArrival) && arrival.isAfter(fDeparture);
            });
        
        if (hasOverlap) {
            return Response.error(ResponseStatus.CONFLICT, 
                "El pasajero tiene otro vuelo en ese horario");
        }

        // Añadir relación bidireccional
        passenger.addFlight(flight);
        flight.addPassenger(passenger);

        return Response.success("Pasajero añadido al vuelo exitosamente", flight);

    } catch (Exception e) {
        return Response.error(ResponseStatus.INTERNAL_ERROR, 
            "Error al añadir pasajero: " + e.getMessage());
    }
}
}
