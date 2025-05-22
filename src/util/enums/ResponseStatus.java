/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package util.enums;

/**
 *
 * @author dubalaguilar
 */

  //En esta clase se definiran los posibles estados de repuesta, siguiendo como guia el siguiente link:
public enum ResponseStatus {
    //Para el exito
    SUCCESS(200, "Operacion exitosa"),
    CREATED(201, "Producto creado correctamente"),
    
    //Errores producidos por parte del usuario
    
    BAD_REQUEST(400, "Solicitud invalida"),
    UNAUTHORIZED(401, "No autorizado"),
    NOT_FOUND(404, "Recurso no encontrado"),
    CONFLICT(409, "Conflicto con el estado actual"),
    
    //Errores Internos 
    INTERNAL_ERROR(500, "Error interno del servidor");
    
    
    //Variables que tendran la ResponseStatus 
    private final int code;
    private final String defaultMessage;

    private ResponseStatus(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
    
}
