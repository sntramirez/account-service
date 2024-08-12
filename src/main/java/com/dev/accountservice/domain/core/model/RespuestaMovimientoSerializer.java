package com.dev.accountservice.domain.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class RespuestaMovimientoSerializer implements Serializer<RespuestaMovimiento> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, RespuestaMovimiento data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar RespuestaMovimiento", e);
        }
    }
}
