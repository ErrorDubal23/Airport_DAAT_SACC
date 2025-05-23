/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.entities.Plane;
import model.services.PlaneService;
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

    public Response registerPlane(String planeId, String brand, String model, String maxCapacity,String airline ) {

        if (planeId == null || brand == null || model == null || maxCapacity == null || airline == null) {
            return Response.error(ResponseStatus.BAD_REQUEST, "Todos los campos son requeridos");
        }

        return planeService.registerPlane(
            planeId.trim().toUpperCase(),
            brand.trim(),
            model.trim(),
            maxCapacity.trim(),
            airline.trim()
        );
    }

    public Response getAllPlanes() {
        try {
            List<Plane> planes = planeService.getAllPlanesSorted();
            return Response.success(planes);
        } catch (Exception e) {
            return Response.error(
                ResponseStatus.INTERNAL_ERROR,
                "Error al obtener la lista de aviones: " + e.getMessage()
            );
        }
    }

    public Response getPlane(String planeId) {
        if (planeId == null || planeId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de avión requerido");
        }

        return planeService.getPlaneById(planeId.trim().toUpperCase());
    }

    public Response getPlaneFlightCount(String planeId) {
        if (planeId == null || planeId.trim().isEmpty()) {
            return Response.error(ResponseStatus.BAD_REQUEST, "ID de avión requerido");
        }

        try {
            int count = planeService.getNumberOfFlightsForPlane(planeId.trim().toUpperCase());
            return Response.success(count);
        } catch (Exception e) {
            return Response.error(
                ResponseStatus.INTERNAL_ERROR,
                "Error al obtener número de vuelos: " + e.getMessage()
            );
        }
    }
}
