/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.helpers;

import java.time.LocalDateTime;
import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class FlightValidator {
     public static Response validate(
        String flightId, 
        String planeId, 
        String departureLocationId, 
        String arrivalLocationId,
        String scaleLocationId,
        LocalDateTime departureDate,
        int hoursArrival, int minutesArrival,
        int hoursScale, int minutesScale
    ) {
        // Validar formato de ID de vuelo (XXXYYY)
        if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_ID_FORMAT);
        }

        // Validar formatos de ubicaciones (3 letras mayúsculas)
        if (!departureLocationId.matches("^[A-Z]{3}$") || !arrivalLocationId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.LOCATION_ID_FORMAT);
        }

        if (scaleLocationId != null && !scaleLocationId.matches("^[A-Z]{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.LOCATION_ID_FORMAT);
        }

        // Validar que la ubicación de salida y llegada sean diferentes
        if (departureLocationId.equals(arrivalLocationId)) {
            return Response.error(ResponseStatus.BAD_REQUEST, "El aeropuerto de salida y llegada no pueden ser iguales");
        }

        // Validar fechas (no en pasado)
        if (departureDate.isBefore(LocalDateTime.now())) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_DATE_PAST);
        }

        // Validar tiempo de vuelo total
        if (hoursArrival <= 0 && minutesArrival <= 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_DURATION_INVALID);
        }

        // Validar escala
        if (scaleLocationId != null) {
            if (hoursScale <= 0 && minutesScale <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.SCALE_TIME_INVALID);
            }
        } else {
            if (hoursScale != 0 || minutesScale != 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, 
                    "No se puede tener tiempo de escala sin aeropuerto de escala");
            }
        }

        return Response.success();
    }
}
