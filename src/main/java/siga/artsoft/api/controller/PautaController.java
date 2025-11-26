package siga.artsoft.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestreService;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteService;
import siga.artsoft.api.pauta.*;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.sessaoturma.SessaoTurmaRepository;

import java.util.List;


@RestController
@RequestMapping("/api/pauta")
public class PautaController {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private DisciplinaSemestreService disciplinaSemestreService;

    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private SessaoTurmaRepository sessaoTurmaRepository;

    @PostMapping
    public ResponseEntity<?> listarNotas(@RequestBody @Valid DadosPesquisaNotas dados){

        var lista= pautaRepository.escolherPautaPorEstudanteAnoLectivoSemestre(dados.idEstudante(),dados.anoLectivo(),dados.semestre()).stream().map(DadosListagemNotas::new);

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/historico")
    public ResponseEntity<?> listarHistoricoAcademico(@RequestBody @Valid DadosPesquisaHistoricoAcademico dados){
        var lista =pautaRepository.escolherPautaPorEstudante(dados.idEstudante()).stream().map(DadosListagemNotas::new);
        return ResponseEntity.ok(lista);
    }
/*
    @PostMapping("/notas-disciplina")
    public ResponseEntity<?> listarNotasDisciplinas(@RequestBody PautaRequestDTO requestDTO) {
        // Busca os objetos de estudante e disciplina pelos IDs fornecidos no DTO
        Estudante estudante = estudanteService.findById(requestDTO.idEstudante())
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado"));
        DisciplinaSemestre disciplina = disciplinaSemestreService.findById(requestDTO.idDisciplina())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        SessaoTurma turma =sessaoTurmaRepository.getReferenceById(requestDTO.idTurma());
        var lista = pautaService.listarNotasByDisciplina(estudante, disciplina, requestDTO.anoLectivo(), requestDTO.semestre(),turma).stream().map(DadosListagemNotas::new);

        // Chama o serviço passando os parâmetros corretos
        return ResponseEntity.ok(lista);
    }

 */

    @PostMapping("/notas-disciplina")
    public ResponseEntity<?> listarNotasDisciplinas(@RequestBody PautaRequestDTO requestDTO) {
        try {
            Estudante estudante = estudanteService.findById(requestDTO.idEstudante())
                    .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado"));
            DisciplinaSemestre disciplina = disciplinaSemestreService.findById(requestDTO.idDisciplina())
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

            var lista = pautaService.listarNotasByDisciplina(estudante, disciplina, requestDTO.anoLectivo(), requestDTO.semestre())
                    .stream()
                    .map(DadosListagemNotas::new);
            return ResponseEntity.ok(lista);

        } catch (PautaService.BloqueioNotasException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno no servidor ao processar a solicitação.");
        }
    }

    @PostMapping("/disciplinas-recorrencia")
    public ResponseEntity<?> listarDisciplinasRecorrencia(@RequestBody PautaRequestDTO requestDTO) {
        Estudante estudante = estudanteService.findById(requestDTO.idEstudante())
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado"));

        var lista = pautaService.listarDisciplinasReprovadas(estudante,  requestDTO.anoLectivo(), requestDTO.semestre()).stream().map(DadosListagemNotas::new);

        return ResponseEntity.ok(lista);
    }

}
