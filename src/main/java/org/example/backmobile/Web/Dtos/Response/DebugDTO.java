package org.example.backmobile.Web.Dtos.Response;

import java.util.HashMap;
import java.util.Map;

public class DebugDTO {
    private Map<String, Object> fields;

    public DebugDTO() {
        this.fields = new HashMap<>();
    }

    // Add a field to the DTO
    public DebugDTO addField(String key, Object value) {
        fields.put(key, value);
        return this;  // Allow chaining
    }

    // Get a specific field by key
    public Object getField(String key) {
        return fields.get(key);
    }

    // Get all fields
    public Map<String, Object> getFields() {
        return fields;
    }

    // Override toString to provide a debugging output
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DebugDTO { \n");
        fields.forEach((key, value) -> {
            sb.append("  ").append(key).append(": ").append(value).append("\n");
        });
        sb.append("}");
        return sb.toString();
    }
}
