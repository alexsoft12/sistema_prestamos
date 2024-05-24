package pe.a3ya.mscustomers.infraestructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    public static <T> String convertirAString(T objetoDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objetoDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoClase) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, tipoClase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}