/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.helpers;

import java.time.LocalDateTime;
import model.entities.Flight;
import model.entities.Location;
import model.entities.Plane;
import model.repositories.impl.Repository;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class FlightValidator {
    public static Response validateFlight(
        String flightId,
        String planeId,
        String departureLocationId,
        String arrivalLocationId,
        String scaleLocationId,
        LocalDateTime departureDate,
        int hoursArrival,
        int minutesArrival,
        int hoursScale,
        int minutesScale,
        Repository<Flight, String> flightRepository,
        Repository<Plane, String> planeRepository,
        Repository<Location, String> locationRepository
    ) {
        // Validar unicidad del ID
        if (flightRepository.findById(flightId).isPresent()) {
            return Response.error(ResponseStatus.CONFLICT, "Ya existe un vuelo con este ID");
        }

        //  Validar existencia del avión
        if (!planeRepository.findById(planeId).isPresent()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Avión no encontrado");
        }

        //  Validar existencia de ubicaciones
        if (!locationRepository.findById(departureLocationId).isPresent()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Ubicación de salida no encontrada");
        }
        if (!locationRepository.findById(arrivalLocationId).isPresent()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Ubicación de llegada no encontrada");
        }

        // Validar ubicación de escala si existe
        if (scaleLocationId != null && !locationRepository.findById(scaleLocationId).isPresent()) {
            return Response.error(ResponseStatus.NOT_FOUND, "Ubicación de escala no encontrada");
        }

        //  Validar fecha futura
        if (departureDate.isBefore(LocalDateTime.now())) {
            return Response.error(ResponseStatus.BAD_REQUEST, "La fecha de salida debe ser futura");
        }

        //  Validar que origen y destino sean diferentes
        if (departureLocationId.equals(arrivalLocationId)) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Origen y destino no pueden ser iguales");
        }

        // Validar que escala no sea igual a origen/destino
        if (hoursScale > 0 || minutesScale > 0) {
        if (scaleLocationId != null && 
            (scaleLocationId.equals(departureLocationId) || scaleLocationId.equals(arrivalLocationId))) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "La escala no puede ser igual al origen o destino");
        }
    }

    return Response.success();
    }
}
