package com.ifood.avaliacoes.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import Model.Avaliacao;

@Component
public class KafkaAvaliacaoProducer {

    private final KafkaTemplate<String, Avaliacao> kafkaTemplate;
    private static final String TOPICO_AVALIACOES = "avaliacoes";  // Nome do tópico Kafka

    public KafkaAvaliacaoProducer(KafkaTemplate<String, Avaliacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarAvaliacaoParaKafka(Avaliacao avaliacao) {
        kafkaTemplate.send(TOPICO_AVALIACOES, avaliacao);
    }
}

