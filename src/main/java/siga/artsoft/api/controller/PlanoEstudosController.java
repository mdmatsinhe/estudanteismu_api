package siga.artsoft.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.planoestudos.DisciplinaPlanoDTO;
import siga.artsoft.api.planoestudos.PlanoEstudosService;

import java.util.List;

@RestController
@RequestMapping("/api/plano-curricular")
public class PlanoEstudosController {

    private static final Logger logger = LoggerFactory.getLogger(PlanoEstudosController.class); // Cria o Logger

    @Autowired
    private PlanoEstudosService planoEstudosService;

    @GetMapping("/estudante/{estudanteId}")
    public ResponseEntity<List<DisciplinaPlanoDTO>> getPlanoCurricular(
            @PathVariable Long estudanteId) {

        try {
            List<DisciplinaPlanoDTO> plano =
                    planoEstudosService.buscarPlanoCurricularPorEstudante(estudanteId);

            if (plano.isEmpty()) {
                // Retorna 404 se não houver dados
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(plano);

        } catch (Exception e) {
            // 🔥 LOG CRUCIAL: Imprime a stack trace completa no console
            logger.error("ERRO FATAL ao carregar plano curricular para estudante ID: {}", estudanteId, e);

            // Retorna um erro 500 para o frontend, evitando o 403/redirecionamento
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}