package utils;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.exception.VisitorManagementException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonUtils {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new VisitorManagementException(VisitorConstants.JSON_PROCESSING_ERROR,e);
        }
    }
    public Map<String,String> toMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new VisitorManagementException(VisitorConstants.JSON_PROCESSING_ERROR,e);
        }
    }

    public <T> T jsonToObject(String json,Class<T> aClass) {
        try {
            return objectMapper.readValue(json, aClass);
        } catch (JsonProcessingException e) {
            throw new VisitorManagementException(VisitorConstants.JSON_PROCESSING_ERROR,e);
        }
    }

    public <T, E> T convertObject(E e, Class<T> aClass) {
        return objectMapper.convertValue(e,aClass);
    }

}
