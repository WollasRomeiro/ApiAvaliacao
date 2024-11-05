package service;

import Model.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ifood.avaliacoes.kafka.KafkaAvaliacaoProducer;  // Importando a classe KafkaAvaliacaoProducer
import repository.AvaliacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private KafkaAvaliacaoProducer kafkaAvaliacaoProducer;  // Injeção de dependência do KafkaAvaliacaoProducer

    // Salvar uma nova avaliação
    public Avaliacao salvarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5");
        }

        // Salva a avaliação no banco de dados
        Avaliacao novaAvaliacao = avaliacaoRepository.save(avaliacao);

        // Envia a avaliação para o Kafka
        kafkaAvaliacaoProducer.enviarAvaliacaoParaKafka(novaAvaliacao); // Envia para o Kafka

        return novaAvaliacao;
    }

    // Buscar avaliações por restaurante
    public List<Avaliacao> buscarAvaliacoesPorRestaurante(Long restauranteId) {
        return avaliacaoRepository.findByRestauranteId(restauranteId);
    }

    // Calcular a média das avaliações de um restaurante
    public double calcularMediaPorRestaurante(Long restauranteId) {
        List<Avaliacao> avaliacoes = buscarAvaliacoesPorRestaurante(restauranteId);
        if (avaliacoes.isEmpty()) {
            return 0.0;  // Caso não haja avaliações, retornar 0
        }
        double somaNotas = 0.0;
        for (Avaliacao avaliacao : avaliacoes) {
            somaNotas += avaliacao.getNota();
        }
        return Math.round((somaNotas / avaliacoes.size()) * 10.0) / 10.0;  // Média com uma casa decimal
    }

    // Buscar avaliação por ID
    public Optional<Avaliacao> buscarAvaliacaoPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    // Buscar avaliações por nota
    public List<Avaliacao> buscarAvaliacoesPorNota(int nota) {
        return avaliacaoRepository.findByNota(nota);
    }

    // Buscar avaliações por palavra-chave no comentário
    public List<Avaliacao> buscarAvaliacoesPorComentario(String palavraChave) {
        return avaliacaoRepository.findByComentarioContaining(palavraChave);
    }

    // Deletar avaliação por ID
    public void deletarAvaliacao(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}




