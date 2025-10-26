package siga.artsoft.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.curso.CursoService;
import siga.artsoft.api.semestreano.SemestreAno;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.sessaoturma.SessaoTurmaDTO;
import siga.artsoft.api.sessaoturma.SessaoTurmaRequestDTO;
import siga.artsoft.api.sessaoturma.SessaoTurmaService;
import siga.artsoft.api.turno.Turno;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessoes-turma")
public class SessaoTurmaController {

    @Autowired
    private SessaoTurmaService sessaoTurmaService;

    @PostMapping("/buscar")
    public ResponseEntity<List<SessaoTurmaDTO>> buscarSessoesTurma(
            @Valid @RequestBody SessaoTurmaRequestDTO requestDTO) {
        try {
            // Criar instâncias das entidades com base no DTO
            Curso curso = new Curso();
            curso.setId(requestDTO.getCursoId());
            Curriculo curriculo = new Curriculo();
            curriculo.setId(requestDTO.getCurriculoId());
            AnoCurso anoCurso = new AnoCurso();
            anoCurso.setId(requestDTO.getAnoCursoId());
            SemestreAno semestreAno = new SemestreAno();
            semestreAno.setId(requestDTO.getSemestreId());
            Turno turno = new Turno();
            turno.setId(requestDTO.getTurnoId());

            // Buscar sessões de turma
            List<SessaoTurma> sessoes = sessaoTurmaService.buscarSessoes(
                    curso, curriculo, anoCurso, semestreAno, turno, requestDTO.getAnoLectivo());

            // Converter para DTO
            List<SessaoTurmaDTO> sessoesDTO = sessoes.stream()
                    .map(s -> new SessaoTurmaDTO(s.getId(), s.getTurma().getNome(),s.getCurriculo().getId(),s.getTurma().getSemestreCurso()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(sessoesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/atrasada")
    public ResponseEntity<List<SessaoTurmaDTO>> buscarSessoesTurmaAtrasada(
            @Valid @RequestBody SessaoTurmaRequestDTO requestDTO) {
        try {
            // Criar instâncias das entidades com base no DTO
            Curso curso = new Curso();
            curso.setId(requestDTO.getCursoId());
            Curriculo curriculo = new Curriculo();
            curriculo.setId(requestDTO.getCurriculoId());
            AnoCurso anoCurso = new AnoCurso();
            anoCurso.setId(requestDTO.getAnoCursoId());
            SemestreAno semestreAno = new SemestreAno();
            semestreAno.setId(requestDTO.getSemestreId());


            // Buscar sessões de turma
            List<SessaoTurma> sessoes = sessaoTurmaService.buscarSessoesAtrasadas(
                    curso, curriculo, anoCurso, semestreAno, requestDTO.getAnoLectivo());

            // Converter para DTO
            List<SessaoTurmaDTO> sessoesDTO = sessoes.stream()
                    .map(s -> new SessaoTurmaDTO(s.getId(), s.getTurma().getNome(),s.getCurriculo().getId(),s.getTurma().getSemestreCurso()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(sessoesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
