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
        // Validar formato 
        if (!flightId.matches("^[A-Z]{3}\\d{3}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_ID_FORMAT);
        }

        // Validar fechas
        if (departureDate.isBefore(LocalDateTime.now())) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_DATE_PAST);
        }

        // Validar tiempos
        if (hoursArrival <= 0 && minutesArrival <= 0) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FLIGHT_DURATION_INVALID);
        }

        // Validar escala
        if (scaleLocationId != null && (hoursScale <= 0 || minutesScale <= 0)) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.SCALE_TIME_INVALID);
        }

        return Response.success();
    }
}
