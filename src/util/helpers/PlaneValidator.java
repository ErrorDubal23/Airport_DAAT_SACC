/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.helpers;

import util.constants.ErrorMessages;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PlaneValidator {
    private static final int MODEL_YEAR_DIGITS = 5;

    public static Response validate(
        String planeId,
        String brand,
        String model,
        String maxCapacityStr,
        String airline
    ) {
        // Validación 1: Campos no vacíos
        if (planeId.isEmpty() || brand.isEmpty() || model.isEmpty() || maxCapacityStr.isEmpty() || airline.isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, ErrorMessages.FIELD_REQUIRED);
        }

        // Validación 2: Formato del ID (XXYYYYY)
        if (!planeId.matches("^[A-Z]{2}\\d{5}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de avión inválido: debe ser XXYYYYY (2 letras + 5 dígitos)");
        }

        // Validación 3: Capacidad máxima numérica y positiva
        try {
            int maxCapacity = Integer.parseInt(maxCapacityStr);
            if (maxCapacity <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST, "La capacidad máxima debe ser un número positivo");
            }
        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST, "La capacidad máxima debe ser un número válido");
        }

        return Response.success();
    }
}
