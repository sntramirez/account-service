package com.devsu.accountservice.application.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class SolicitudMovimientoSerializer implements Serializer<SolicitudMovimiento> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, SolicitudMovimiento data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar SolicitudMovimiento", e);
        }
    }
}
