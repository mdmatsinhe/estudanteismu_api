package siga.artsoft.api.planoestudos;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanoEstudosService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Transactional(readOnly = true)
    public List<DisciplinaPlanoDTO> buscarPlanoCurricularPorEstudante(Long estudanteId) {
        Estudante estudante = estudanteRepository.findByIdWithCurriculumAndPlan(estudanteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado: " + estudanteId));

        Curriculo curriculo = estudante.getCurriculo();
        if (curriculo == null) {
            log.warn("Estudante {} sem currículo", estudanteId);
            return Collections.emptyList();
        }

        if (curriculo.getPlanoEstudos() == null || curriculo.getPlanoEstudos().isEmpty()) {
            log.warn("Currículo {} sem plano de estudos", curriculo.getId());
            return Collections.emptyList();
        }

        return curriculo.getPlanoEstudos().stream()
                .map(plano -> new DisciplinaPlanoDTO(
                        plano.getDisciplina().getCodigo(),
                        plano.getDisciplina().getNome(),
                        plano.getAnoCurso().getCodigo(),
                        plano.getSemestreCurso(),
                        plano.getCreditos() != null ? plano.getCreditos().doubleValue() : null,
                        plano.getCargaHoraria(),
                        plano.getSemestre().getId().intValue()
                ))
                .sorted(Comparator.comparing(DisciplinaPlanoDTO::semestre))
                .collect(Collectors.toList());
    }

}