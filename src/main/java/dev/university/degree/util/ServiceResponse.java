package dev.university.degree.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ServiceResponse<T> {
    private int code;
    private String message;
    private T value;
    private String[] validateErrors;

    public ServiceResponse(int code, String message, T value){
        this.code = code;
        this.message = message;
        this.value = value;
        validateErrors = null;
    }
}
