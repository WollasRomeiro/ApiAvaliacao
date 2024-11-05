package repository;

import Model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // Método para buscar avaliações pelo ID do restaurante
    List<Avaliacao> findByRestauranteId(Long restauranteId);

    // Outros métodos, se necessário
    List<Avaliacao> findByNota(int nota);
    List<Avaliacao> findByComentarioContaining(String palavraChave);
}



