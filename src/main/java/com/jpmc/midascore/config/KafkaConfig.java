//package com.jpmc.midascore.config;
//import com.jpmc.midascore.foundation.Transaction;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
////Tells spring how to  create Kafka consumers
//    @Bean
//    public ConsumerFactory<String,Transaction> transactionConsumerFactory(){
//        Map<String,Object>config  = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG,"midas-core-group");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.jpmc.midascore.foundation");
//        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Transaction.class.getName());
//        return new DefaultKafkaConsumerFactory<>(config);
//
//    }
////  Manages multiple Kafka Listeners efficiently
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String,Transaction> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String,Transaction> factory=new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(transactionConsumerFactory());
//        return factory;
//
//    }
//}
