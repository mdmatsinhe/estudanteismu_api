package siga.artsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curriculo.CurriculoRepository;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestreDTO;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaSemestreController {

    @Autowired
    private DisciplinaSemestreService disciplinaSemestreService;

    @Autowired
    private CurriculoRepository curriculoRepository;

    @GetMapping("/por-curriculo-e-semestre")
    public ResponseEntity<List<DisciplinaSemestreDTO>> buscarDisciplinasPorCurriculoESemestre(
            @RequestParam Long curriculoId,
            @RequestParam int semestreCurso,
            @RequestParam Long estudanteId) {
        try {
            Curriculo curriculo = new Curriculo();
            curriculo.setId(curriculoId);

            List<DisciplinaSemestre> disciplinas = disciplinaSemestreService
                    .buscarDisciplinasDisponiveis(curriculo, semestreCurso, estudanteId);

            //.buscarDisciplinasPorCurriculoESemestre(curriculo, semestreCurso);

            // Converter para DTO para retornar apenas os dados necessários
            List<DisciplinaSemestreDTO> disciplinasDTO = disciplinas.stream()
                    .map(d -> new DisciplinaSemestreDTO(
                            d.getId(),
                            d.getDisciplina().getNome(),
                            d.getSemestreCurso(),
                            d.getAnoCurso().getId().intValue(),
                            d.getSemestreAno().getId().intValue(),
                            d.getCreditos()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(disciplinasDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<DisciplinaSemestreDTO>> listarDisciplinasAtrasadas(
            @RequestParam Long curriculoId,
            @RequestParam int semestreCurso,
            @RequestParam Long estudanteId) {
        Curriculo curriculo = curriculoRepository.getReferenceById(curriculoId);
                List<DisciplinaSemestreDTO> disciplinas = disciplinaSemestreService.listarDisciplinasAtrasadas(curriculo, semestreCurso,estudanteId);
        return ResponseEntity.ok(disciplinas);
    }
}

