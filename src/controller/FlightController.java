/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import controller.services.FlightService;
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

    public Response registerFlight(
            String flightId,
            String planeId,
            String departureLocationId,
            String arrivalLocationId,
            String scaleLocationId,
            String yearStr,
            String monthStr,
            String dayStr,
            String hourStr,
            String minuteStr,
            String hoursArrivalStr,
            String minutesArrivalStr,
            String hoursScaleStr,
            String minutesScaleStr
    ) {
        // Validaciones de campos vacíos
        if (flightId.isEmpty()) {
            return Response.fieldRequired("ID del vuelo");
        }
        if (planeId.isEmpty()) {
            return Response.fieldRequired("avión");
        }
        if (departureLocationId.isEmpty()) {
            return Response.fieldRequired("origen");
        }
        if (arrivalLocationId.isEmpty()) {
            return Response.fieldRequired("destino");
        }
        if (yearStr.isEmpty()) {
            return Response.fieldRequired("año");
        }
        if (monthStr.isEmpty()) {
            return Response.fieldRequired("mes");
        }
        if (dayStr.isEmpty()) {
            return Response.fieldRequired("día");
        }
        if (hourStr.isEmpty()) {
            return Response.fieldRequired("hora");
        }
        if (minuteStr.isEmpty()) {
            return Response.fieldRequired("minuto");
        }
        if (hoursArrivalStr.isEmpty()) {
            return Response.fieldRequired("duración (horas)");
        }
        if (minutesArrivalStr.isEmpty()) {
            return Response.fieldRequired("duración (minutos)");
        }

        // Manejo de escala 
        boolean hasScale = !scaleLocationId.isEmpty();
        int hoursScale = 0;
        int minutesScale = 0;

        if (hasScale) {
            if (hoursScaleStr.isEmpty()) {
                return Response.fieldRequired("escala (horas)");
            }
            if (minutesScaleStr.isEmpty()) {
                return Response.fieldRequired("escala (minutos)");
            }

            // Convertir a números
            hoursScale = Integer.parseInt(hoursScaleStr.trim());
            minutesScale = Integer.parseInt(minutesScaleStr.trim());

            // Si el tiempo de escala es 00:00 no hay escala
            if (hoursScale == 0 && minutesScale == 0) {
                hasScale = false;
                scaleLocationId = null;
            }
        }

        // Formatear ID
        flightId = flightId.trim().toUpperCase();
        planeId = planeId.trim().toUpperCase();
        departureLocationId = departureLocationId.trim().toUpperCase();
        arrivalLocationId = arrivalLocationId.trim().toUpperCase();
        scaleLocationId = hasScale ? scaleLocationId.trim().toUpperCase() : null;

        // Validar formato de ID
        if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de vuelo debe ser XXXYYY (3 letras + 3 dígitos)");
        }
        if (!departureLocationId.matches("^[A-Z]{3}$") || !arrivalLocationId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, "IDs de ubicación deben ser 3 letras mayúsculas");
        }
        if (hasScale && !scaleLocationId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de escala debe ser 3 letras mayúsculas");
        }

        // Convertir y validar datos numéricos
        try {
            int year = Integer.parseInt(yearStr.trim());
            int month = Integer.parseInt(monthStr.trim());
            int day = Integer.parseInt(dayStr.trim());
            int hour = Integer.parseInt(hourStr.trim());
            int minute = Integer.parseInt(minuteStr.trim());
            int hoursArrival = Integer.parseInt(hoursArrivalStr.trim());
            int minutesArrival = Integer.parseInt(minutesArrivalStr.trim());

            // Validar tiempos positivos
            if (hoursArrival < 0 || minutesArrival < 0 || (hoursArrival == 0 && minutesArrival == 0)) {
                return Response.error(ResponseStatus.BAD_REQUEST, "La duración del vuelo debe ser mayor a 00:00");
            }
            if (hasScale && (hoursScale < 0 || minutesScale < 0 || (hoursScale == 0 && minutesScale == 0))) {
                return Response.error(ResponseStatus.BAD_REQUEST, "El tiempo de escala debe ser mayor a 00:00");
            }
            if (!hasScale && (hoursScale != 0 || minutesScale != 0)) {
                return Response.error(ResponseStatus.BAD_REQUEST, "No puede haber tiempo de escala sin aeropuerto de escala");
            }

            // Crear fecha de salida
            LocalDateTime departureDate = LocalDateTime.of(year, month, day, hour, minute);

            // Pasar al servicio para validaciones complejas
            return flightService.registerFlight(
                    flightId, planeId, departureLocationId, arrivalLocationId,
                    hasScale ? scaleLocationId : null, // nulosi no hay escala
                    departureDate, hoursArrival, minutesArrival,
                    hoursScale, minutesScale
            );

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos numéricos deben ser válidos");
        } catch (DateTimeException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Fecha u hora inválida");
        }
    }

    public Response getAllFlights() {
        try {
            return Response.success(flightService.getAllFlightsSorted());
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener vuelos: " + e.getMessage());
        }
    }

    public Response delayFlight(String flightId, String hoursStr, String minutesStr) {
        // Validaciones  de campos vacíos
        if (flightId.isEmpty()) {
            return Response.fieldRequired("ID del vuelo");
        }
        if (hoursStr.isEmpty()) {
            return Response.fieldRequired("horas de retraso");
        }
        if (minutesStr.isEmpty()) {
            return Response.fieldRequired("minutos de retraso");
        }

        // Formatear ID del vuelo
        flightId = flightId.trim().toUpperCase();

        try {
            // Convertir y validar tiempos
            int hours = Integer.parseInt(hoursStr.trim());
            int minutes = Integer.parseInt(minutesStr.trim());

            // Validar que el retraso sea mayor que 00:00
            if (hours < 0 || minutes < 0) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "El tiempo de retraso no puede ser negativo");
            }
            if (hours == 0 && minutes == 0) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "El tiempo de retraso debe ser mayor que 00:00");
            }

            // Validar formato del ID del vuelo (XXXYYY)
            if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "ID de vuelo debe tener formato XXXYYY (3 letras + 3 dígitos)");
            }

            // Pasar al servicio para validaciones adicionales
            return flightService.delayFlight(flightId, hours, minutes);

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST,
                    "Horas y minutos deben ser valores numéricos válidos");
        }
    }

    public Response addPassengerToFlight(String passengerIdStr, String flightId) {
        // Validaciones básicas de campos vacíos
        if (passengerIdStr.isEmpty()) {
            return Response.fieldRequired("ID del pasajero");
        }
        if (flightId.isEmpty()) {
            return Response.fieldRequired("ID del vuelo");
        }

        // Formatear ID
        flightId = flightId.trim().toUpperCase();

        try {
            // Convertir y validar ID del pasajero
            long passengerId = Long.parseLong(passengerIdStr.trim());

            // Validar formato ID pasajero
            if (passengerId <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID de pasajero debe ser positivo");
            }
            if (String.valueOf(passengerId).length() > 15) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID de pasajero no puede tener más de 15 dígitos");
            }

            // Validar formato ID vuelo (XXXYYY)
            if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
                return Response.error(ResponseStatus.BAD_REQUEST, "ID de vuelo debe tener formato XXXYYY");
            }

            // Pasar al servicio para validaciones adicionales
            return flightService.addPassengerToFlight(passengerId, flightId);

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de pasajero debe ser numérico");
        }
    }
}
