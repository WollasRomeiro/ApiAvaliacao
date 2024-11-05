package controller;

import Model.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AvaliacaoService;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    // Endpoint para salvar avaliação
    @PostMapping
    public ResponseEntity<Avaliacao> salvarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao novaAvaliacao = avaliacaoService.salvarAvaliacao(avaliacao);  // Salva e envia para Kafka
        return new ResponseEntity<>(novaAvaliacao, HttpStatus.CREATED);
    }

    // Endpoint para buscar avaliações por nota
    @GetMapping("/nota/{nota}")
    public ResponseEntity<List<Avaliacao>> buscarAvaliacoesPorNota(@PathVariable int nota) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorNota(nota);
        if (avaliacoes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content caso não tenha avaliações
        }
        return ResponseEntity.ok(avaliacoes); // Retorna 200 OK com as avaliações
    }

    // Endpoint para buscar avaliações por comentário
    @GetMapping("/comentario/{palavraChave}")
    public ResponseEntity<List<Avaliacao>> buscarAvaliacoesPorComentario(@PathVariable String palavraChave) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorComentario(palavraChave);
        if (avaliacoes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content caso não tenha avaliações
        }
        return ResponseEntity.ok(avaliacoes); // Retorna 200 OK com as avaliações
    }

    // Endpoint para buscar avaliação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarAvaliacaoPorId(@PathVariable Long id) {
        return avaliacaoService.buscarAvaliacaoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deletar avaliação por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        if (avaliacaoService.buscarAvaliacaoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        avaliacaoService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}




