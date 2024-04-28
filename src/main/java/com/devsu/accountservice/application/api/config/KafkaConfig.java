package com.devsu.accountservice.application.api.config;

import com.devsu.accountservice.application.api.model.RespuestaMovimiento;
import com.devsu.accountservice.application.api.model.RespuestaMovimientoSerializer;
import com.devsu.accountservice.application.api.model.SolicitudMovimiento;
import com.devsu.accountservice.application.api.model.SolicitudMovimientoSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, RespuestaMovimiento> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.ACKS_CONFIG, "1");
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        configProps.put(ProducerConfig.RETRIES_CONFIG, 1);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, RespuestaMovimientoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, RespuestaMovimiento> respuestaKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, SolicitudMovimiento> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Configuración para deserializar JSON a SolicitudMovimientoSerializer
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SolicitudMovimientoSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
                new JsonDeserializer<>(SolicitudMovimiento.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SolicitudMovimiento>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SolicitudMovimiento> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
