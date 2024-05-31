package pe.a3ya.mscustomers.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {
    public static <T> String convertirAString(T objetoDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(objetoDto);
        } catch (JsonProcessingException e) {
           return null;
        }
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoClase) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, tipoClase);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}