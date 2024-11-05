package com.ifood.avaliacoes.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import Model.Avaliacao;

@Component
public class KafkaAvaliacaoListener {

    private static final String TOPICO_AVALIACOES = "avaliacoes";  // Nome do tópico Kafka

    @KafkaListener(topics = TOPICO_AVALIACOES, groupId = "avaliacoes-group")
    public void ouvirAvaliacao(Avaliacao avaliacao) {
        System.out.println("Recebendo avaliação: " + avaliacao);
    }
}

