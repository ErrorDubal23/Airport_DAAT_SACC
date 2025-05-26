/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.entities.Plane;
import controller.services.PlaneService;
import util.enums.ResponseStatus;
import util.responses.Response;

/**
 *
 * @author dubalaguilar
 */
public class PlaneController {

    private final PlaneService planeService;

    public PlaneController() {
        this.planeService = PlaneService.getInstance();
    }

    public Response registerPlane(
            String planeId,
            String brand,
            String model,
            String maxCapacityStr,
            String airline
    ) {
        // Validaciones  de campos vacíos
        if (planeId.isEmpty()) {
            return Response.fieldRequired("ID del avión");
        }
        if (brand.isEmpty()) {
            return Response.fieldRequired("marca");
        }
        if (model.isEmpty()) {
            return Response.fieldRequired("modelo");
        }
        if (maxCapacityStr.isEmpty()) {
            return Response.fieldRequired("capacidad máxima");
        }
        if (airline.isEmpty()) {
            return Response.fieldRequired("aerolínea");
        }

        // Formatear datos
        planeId = planeId.trim().toUpperCase();
        brand = brand.trim();
        model = model.trim();
        airline = airline.trim();

        // Validar formato ID (XXYYYYY)
        if (!planeId.matches("^[A-Z]{2}\\d{5}$")) {
            return Response.error(ResponseStatus.BAD_REQUEST,
                    "El ID del avión debe tener formato XXYYYYY (2 letras + 5 dígitos)");
        }

        try {
            // Convertir capacidad
            int maxCapacity = Integer.parseInt(maxCapacityStr.trim());

            // Validar número positivo
            if (maxCapacity <= 0) {
                return Response.error(ResponseStatus.BAD_REQUEST,
                        "La capacidad máxima debe ser un número positivo");
            }

            // Pasar al servicio para validaciones adicionales
            return planeService.registerPlane(planeId, brand, model, maxCapacity, airline);

        } catch (NumberFormatException e) {
            return Response.error(ResponseStatus.BAD_REQUEST,
                    "La capacidad máxima debe ser un número válido");
        }
    }

    public Response getAllPlanes() {
        try {
            return Response.success(planeService.getAllPlanesSorted());
        } catch (Exception e) {
            return Response.error(ResponseStatus.INTERNAL_ERROR,
                    "Error al obtener aviones: " + e.getMessage());
        }
    }

    public Response getPlaneById(String planeId) {
        if (planeId == null || planeId.isEmpty()) {
            return Response.fieldRequired("ID del avión");
        }
        return planeService.getPlaneById(planeId.trim().toUpperCase());
    }
}
