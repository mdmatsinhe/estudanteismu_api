package siga.artsoft.api.sessaoturma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.semestreano.SemestreAno;
import siga.artsoft.api.turno.Turno;

import java.util.List;

@Service
public class SessaoTurmaService {

    @Autowired
    private SessaoTurmaRepository sessaoTurmaRepository;

    public List<SessaoTurma> buscarSessoes(Curso curso, Curriculo curriculo,
                                           AnoCurso ano, SemestreAno semestre,
                                           Turno turno, int anoLectivo) {
        return sessaoTurmaRepository.findByCursoCurriculoAnoSemestreTurno(
                curso, curriculo, ano, semestre, turno, anoLectivo);
    }

    public List<SessaoTurma> buscarSessoesAtrasadas(Curso curso, Curriculo curriculo,
                                           AnoCurso ano, SemestreAno semestre, int anoLectivo) {
        return sessaoTurmaRepository.findByCursoCurriculoAnoSemestre(
                curso, curriculo, ano, semestre, anoLectivo);
    }

}
