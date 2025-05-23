/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.entities.Flight;
import model.entities.Passenger;
import model.services.PassengerService;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController() {
        this.passengerService = PassengerService.getInstance();
    }

    public Response registerPassenger(long id, String firstName, String lastName, String birthDate, int phoneCode, long phoneNumber, String country) {
        // Validación básica de parámetros
        if (firstName == null || lastName == null || birthDate == null || country == null) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos son requeridos");
        }

        return passengerService.registerPassenger(
            id,
            firstName.trim(),
            lastName.trim(),
            birthDate.trim(),
            phoneCode,
            phoneNumber,
            country.trim()
        );
    }

    public Response updatePassenger(
        long id,
        String firstName,
        String lastName,
        String birthDate,
        int phoneCode,
        long phoneNumber,
        String country
    ) {
        // Validación básica de parámetros
        if (firstName == null || lastName == null || birthDate == null || country == null) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos son requeridos");
        }

        return passengerService.updatePassenger(
            id,
            firstName.trim(),
            lastName.trim(),
            birthDate.trim(),
            phoneCode,
            phoneNumber,
            country.trim()
        );
    }

    public Response getAllPassengers() {
        try {
            List<Passenger> passengers = passengerService.getAllPassengersSorted();
            return Response.success(passengers);
        } catch (Exception e) {
            return Response.error(
                ResponseStatus.INTERNAL_ERROR,
                "Error al obtener la lista de pasajeros: " + e.getMessage()
            );
        }
    }


    public Response getPassenger(long id) {
        if (id <= 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
        }
        return passengerService.getPassengerById(id);
    }

    public Response getPassengerFlights(long passengerId) {
        if (passengerId <= 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.PASSENGER_ID_INVALID);
        }

        try {
            List<Flight> flights = passengerService.getPassengerFlightsSorted(passengerId);
            return Response.success(flights);
        } catch (Exception e) {
            return Response.error(
                ResponseStatus.INTERNAL_ERROR,
                "Error al obtener los vuelos del pasajero: " + e.getMessage()
            );
        }
    }
    
    

}
