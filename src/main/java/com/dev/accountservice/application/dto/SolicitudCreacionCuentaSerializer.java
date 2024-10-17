package com.dev.accountservice.application.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class SolicitudCreacionCuentaSerializer implements Serializer<SolicitudCreacionCuenta> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, SolicitudCreacionCuenta data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar SolicitudMovimiento", e);
        }
    }
}
