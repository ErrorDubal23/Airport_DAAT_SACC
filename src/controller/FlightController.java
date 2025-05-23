/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDateTime;
import java.util.List;
import model.entities.Flight;
import model.services.FlightService;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class FlightController {

    private final FlightService flightService;

    public FlightController() {
        this.flightService = FlightService.getInstance();
    }

    public Response registerFlight(String flightId, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival,int hoursDurationScale, int minutesDurationScale ) {
       
        
        if (flightId == null || planeId == null || departureLocationId == null
                || arrivalLocationId == null || departureDate == null) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos requeridos deben estar completos");
        }

        return flightService.registerFlight(
                flightId.trim().toUpperCase(),
                planeId.trim().toUpperCase(),
                departureLocationId.trim().toUpperCase(),
                arrivalLocationId.trim().toUpperCase(),
                scaleLocationId != null ? scaleLocationId.trim().toUpperCase() : null,
                departureDate,
                hoursDurationArrival,
                minutesDurationArrival,
                hoursDurationScale,
                minutesDurationScale
        );
    }

    public Response delayFlight(String flightId, int hours, int minutes) {
        if (flightId == null || flightId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de vuelo requerido");
        }
        if (hours < 0 || minutes < 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El tiempo de retraso debe ser positivo");
        }

        return flightService.delayFlight(
                flightId.trim().toUpperCase(),
                hours,
                minutes
        );
    }

    public Response getAllFlights() {
        try {
            List<Flight> flights = flightService.getAllFlightsSorted();
            return Response.success(flights);
        } catch (Exception e) {
            return Response.error(
                    ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener la lista de vuelos: " + e.getMessage()
            );
        }
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
        if (passengerId <= 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
        }
        if (flightId == null || flightId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de vuelo requerido");
        }

        return flightService.addPassengerToFlight(
                passengerId,
                flightId.trim().toUpperCase()
        );
    }

    public Response getFlightPassengers(String flightId) {
        if (flightId == null || flightId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de vuelo requerido");
        }

        return flightService.getFlightById(flightId.trim().toUpperCase())
                .map(flight -> Response.success(flight.getPassengers()))
                .orElse(Response.error(ResponseStatus.NOT_FOUND, ErrorMessages.FLIGHT_NOT_FOUND));
    }
}
