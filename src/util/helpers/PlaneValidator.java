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
    public static Response validateDomainRules(
        String planeId,
        String brand,
        String model,
        int maxCapacity,
        String airline
    ) {
        // Validación de longitud mínima para marca/modelo/aerolínea
        if (brand.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "La marca debe tener al menos 2 caracteres");
        }

        if (model.length() < 1) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El modelo no puede estar vacío");
        }

        if (airline.length() < 2) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El nombre de la aerolínea debe tener al menos 2 caracteres");
        }

        // Validación de caracteres permitidos
        if (!brand.matches("^[a-zA-Z0-9\\s-]+$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "La marca contiene caracteres no permitidos");
        }

        if (!model.matches("^[a-zA-Z0-9\\s-]+$")) {
            return Response.error(ResponseStatus.BAD_REQUEST, 
                "El modelo contiene caracteres no permitidos");
        }

        return Response.success();
    }
}
