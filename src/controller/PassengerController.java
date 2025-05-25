/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
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

    public Response registerPassenger(
            String idStr,
            String firstName,
            String lastName,
            String birthYearStr,
            String birthMonthStr,
            String birthDayStr,
            String phoneCodeStr,
            String phoneNumberStr,
            String country
    ) {
        // Validaciones de campos vacíos
        if (idStr.isEmpty()) {
            return Response.fieldRequired("ID");
        }
        if (firstName.isEmpty()) {
            return Response.fieldRequired("nombre");
        }
        if (lastName.isEmpty()) {
            return Response.fieldRequired("apellido");
        }
        if (birthYearStr.isEmpty()) {
            return Response.fieldRequired("año de nacimiento");
        }
        if (birthMonthStr.isEmpty()) {
            return Response.fieldRequired("mes de nacimiento");
        }
        if (birthDayStr.isEmpty()) {
            return Response.fieldRequired("día de nacimiento");
        }
        if (phoneCodeStr.isEmpty()) {
            return Response.fieldRequired("código de teléfono");
        }
        if (phoneNumberStr.isEmpty()) {
            return Response.fieldRequired("número de teléfono");
        }
        if (country.isEmpty()) {
            return Response.fieldRequired("país");
        }

        try {
            // Convertir datos
            long id = Long.parseLong(idStr.trim());
            int year = Integer.parseInt(birthYearStr.trim());
            int month = Integer.parseInt(birthMonthStr.trim());
            int day = Integer.parseInt(birthDayStr.trim());
            int phoneCode = Integer.parseInt(phoneCodeStr.trim());
            long phoneNumber = Long.parseLong(phoneNumberStr.trim());

            // Formatear fecha
            String birthDateStr = String.format("%04d-%02d-%02d", year, month, day);

            // Pasar al servicio para validaciones específicas
            return passengerService.registerPassenger(
                    id, firstName.trim(), lastName.trim(), birthDateStr,
                    phoneCode, phoneNumber, country.trim()
            );

        } catch (NumberFormatException e) {
            return Response.numberRequired("Todos los campos numéricos");
        }
    }

    //Falta pasar los comentarios de las response a los ErrorMessages 
    public Response updatePassenger(
            String idStr,
            String firstName,
            String lastName,
            String birthYearStr,
            String birthMonthStr,
            String birthDayStr,
            String phoneCodeStr,
            String phoneNumberStr,
            String country
    ) {
        // Validaciones  de campos vacíos
        if (idStr.isEmpty()) {
            return Response.fieldRequired("ID");
        }
        if (firstName.isEmpty()) {
            return Response.fieldRequired("nombre");
        }
        if (lastName.isEmpty()) {
            return Response.fieldRequired("apellido");
        }
        if (birthYearStr.isEmpty()) {
            return Response.fieldRequired("año de nacimiento");
        }
        if (birthMonthStr.isEmpty()) {
            return Response.fieldRequired("mes de nacimiento");
        }
        if (birthDayStr.isEmpty()) {
            return Response.fieldRequired("día de nacimiento");
        }
        if (phoneCodeStr.isEmpty()) {
            return Response.fieldRequired("código de teléfono");
        }
        if (phoneNumberStr.isEmpty()) {
            return Response.fieldRequired("número de teléfono");
        }
        if (country.isEmpty()) {
            return Response.fieldRequired("país");
        }

        // Formatear datos
        firstName = firstName.trim();
        lastName = lastName.trim();
        country = country.trim();

        try {
            long id = Long.parseLong(idStr.trim());
            int year = Integer.parseInt(birthYearStr.trim());
            int month = Integer.parseInt(birthMonthStr.trim());
            int day = Integer.parseInt(birthDayStr.trim());
            int phoneCode = Integer.parseInt(phoneCodeStr.trim());
            if (phoneNumberStr.length() > 11) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "El nombre no puede tener más de 11 caracteres");
            }
            long phoneNumber = Long.parseLong(phoneNumberStr.trim());

            // Validar ID 
            if (id <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.ID_NOT_NEGATIVE);
            }
            if (String.valueOf(id).length() > 15) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID no puede tener más de 15 dígitos");
            }

            // Validar código de telefono
            if (phoneCode <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Código de teléfono no puede ser negativo");
            }
            if (String.valueOf(phoneCode).length() > 3) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Código de teléfono no puede tener más de 3 dígitos");
            }

            // Validar numero de telefono
            if (phoneNumber <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Número de teléfono no puede ser negativo");
            }
            if (String.valueOf(phoneNumber).length() > 11) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Número de teléfono no puede tener más de 11 dígitos");
            }

            // Validar rangos de fecha
            LocalDate now = LocalDate.now();
            if (year < 1900 || year > now.getYear()) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Año de nacimiento inválido");
            }
            if (month < 1 || month > 12) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Mes de nacimiento inválido");
            }
            if (day < 1 || day > 31) {
                return Response.error(ResponseStatus.BAD_REQUEST, "Día de nacimiento inválido");
            }

            // Formatear fecha
            String birthDateStr = String.format("%04d-%02d-%02d", year, month, day);

            // Pasar al servicio para validaciones adicionales
            return passengerService.updatePassenger(
                    id, firstName, lastName, birthDateStr, phoneCode, phoneNumber, country
            );

        } catch (NumberFormatException e) {

            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos numéricos deben ser válidos");
        }
    }

    public Response getPassengerById(String idStr) {
        if (idStr == null || idStr.isEmpty()) {
            return Response.fieldRequired("ID del pasajero");
        }

        try {
            long id = Long.parseLong(idStr.trim());
            return passengerService.getPassengerById(id);
        } catch (NumberFormatException e) {
            return Response.numberRequired("ID del pasajero");
        }
    }

    public Response getAllPassengers() {
        try {
            return Response.success(passengerService.getAllPassengersSorted());
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener pasajeros: " + e.getMessage());
        }
    }

    public Response getPassengerFlights(String passengerIdStr) {
        if (passengerIdStr == null || passengerIdStr.isEmpty()) {
            return Response.fieldRequired("ID del pasajero");
        }

        try {
            long passengerId = Long.parseLong(passengerIdStr.trim());
            List<Flight> flights = passengerService.getPassengerFlightsSorted(passengerId);
            return Response.success(flights);
        } catch (NumberFormatException e) {
            return Response.numberRequired("ID del pasajero");
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener vuelos del pasajero: " + e.getMessage());
        }
    }
}
