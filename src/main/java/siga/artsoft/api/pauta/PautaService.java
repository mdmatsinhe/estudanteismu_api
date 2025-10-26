package siga.artsoft.api.pauta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.sessaoturma.SessaoTurma;

import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public List<Pauta> listarDisciplinasReprovadas(Estudante estudante,  int anoLectivo, int semestre) {
        return pautaRepository.findByEstudanteAndDisciplinaAndAnoLectivoAndSemestreAndNotaExameNormalLessThanAndGradedExameNormalTrueAndPublicadoExameNormalTrue(
                estudante, anoLectivo, semestre, 10.0);
    }
/*
    public List<Pauta> listarNotasByDisciplina(Estudante estudante, DisciplinaSemestre disciplina, int anoLectivo, int semestre, SessaoTurma turma) {
        return pautaRepository.findByEstudanteAndDisciplinaAndAnoLectivoAndSemestreAndTurma(
                estudante, disciplina, anoLectivo, semestre,turma);
    }
*/

    public List<Pauta> listarNotasByDisciplina(Estudante estudante, DisciplinaSemestre disciplina, int anoLectivo, int semestre) {
        return pautaRepository.findByEstudanteAndDisciplinaAndAnoLectivoAndSemestre(
                estudante, disciplina, anoLectivo, semestre);
    }

    public Optional<Pauta> findById(long l) {
        return pautaRepository.findById(l);
    }
}
