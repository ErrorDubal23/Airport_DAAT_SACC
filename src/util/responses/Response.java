/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.responses;

import util.enums.ResponseStatus;

/**
 *
 * @author dubalaguilar
 */
public class Response {
    private final ResponseStatus status;
    private final String message;
    private final Object data;
    
    public Response(ResponseStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    // Para éxito con datos
    public static Response success(Object data) {
        return new Response(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getDefaultMessage(), data);
    }
    
    // Para éxito con mensaje personalizado
    public static Response success(String message, Object data) {
        return new Response(ResponseStatus.SUCCESS, message, data);
    }
    
    // Para errores con estado y mensaje personalizado
    public static Response error(ResponseStatus status, String message) {
        return new Response(status, message, null);
    }
    
    // Para errores con estado (usa mensaje por defecto del enum)
    public static Response error(ResponseStatus status) {
        return new Response(status, status.getDefaultMessage(), null);
    }
    
    //Getters 
    public boolean isSuccess() {
        return status.getCode() >= 200 && status.getCode() < 300;
    }

    public int getStatusCode() {
        return status.getCode();
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
