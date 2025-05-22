/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.responses;

import util.enums.ResponseStatus;

public class Response {
    private final ResponseStatus status;
    private final String message;
    private final Object data;

    // Constructor principal (privado o protegido para forzar el uso de los métodos estáticos)
    private Response(ResponseStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

   
    public static Response success(Object data) {
        return new Response(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getDefaultMessage(), data);
    }

    public static Response success(String message, Object data) {
        return new Response(ResponseStatus.SUCCESS, message, data);
    }

    public static Response success(ResponseStatus status, String message, Object data) {
        return new Response(status, message, data);
    }

    public static Response success() {
        return new Response(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getDefaultMessage(), null);
    }

    public static Response error(ResponseStatus status, String message) {
        return new Response(status, message, null);
    }

    public static Response error(ResponseStatus status) {
        return new Response(status, status.getDefaultMessage(), null);
    }

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
