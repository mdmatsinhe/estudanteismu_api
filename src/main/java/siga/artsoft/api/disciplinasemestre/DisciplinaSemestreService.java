package siga.artsoft.api.disciplinasemestre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.emolumento.Emolumento;
import siga.artsoft.api.emolumento.EmolumentoRepository;
import siga.artsoft.api.pauta.PautaRepository;
import siga.artsoft.api.precedencia.Precedencia;
import siga.artsoft.api.precedencia.PrecedenciaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaSemestreService {

    @Autowired
    private DisciplinaSemestreRepository disciplinaSemestreRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PrecedenciaRepository precedenciaRepository;

    public Optional<DisciplinaSemestre> findById(long l) {
        return disciplinaSemestreRepository.findById(l);
    }

    public List<DisciplinaSemestre> buscarDisciplinasPorCurriculoESemestre(Curriculo curriculo, int semestreCurso){
        return disciplinaSemestreRepository.findDisciplinaSemestreByCurriculoSemestreCurso(curriculo, semestreCurso);
    }

    public List<DisciplinaSemestreDTO> listarDisciplinasAtrasadas(Curriculo curriculo, int semestreAtual, Long estudanteId) {
        List<DisciplinaSemestreDTO> disciplinasAtrasadas = new ArrayList<>();

        if (semestreAtual >= 2) {
            for (int i = semestreAtual - 2; i >= 0; i -= 2) {
                List<DisciplinaSemestre> disciplinas = disciplinaSemestreRepository
                        .findDisciplinaSemestreByCurriculoSemestreCurso(curriculo, i);

                for (DisciplinaSemestre disciplina : disciplinas) {

                    // Verifica se o estudante já está aprovado na disciplina
                    boolean aprovado = pautaRepository.existsByEstudanteIdAndDisciplinaIdAndNotaFinalGreaterThanEqual(
                            estudanteId,
                            disciplina.getDisciplina().getId(),
                            10
                    );

                    // Verifica se o estudante já se inscreveu na disciplina
                    //  boolean inscrito = pautaRepository.existsByEstudanteIdAndDisciplinaId(estudanteId, disciplina.getDisciplina().getId());

                    // Adicionar disciplina apenas se não estiver aprovada e não já inscrita
                    if (!aprovado) {

                        // Verifica se a disciplina possui precedências
                        List<Precedencia> precedencias = precedenciaRepository.findByCurriculoAndDisciplinaSucessede(curriculo, disciplina.getDisciplina());

                        boolean todasPrecedenciasCumpridas = true;

                        // Verifica se todas as precedências foram cumpridas
                        for (Precedencia precedencia : precedencias) {
                            // Verifica se o estudante tem aprovação na disciplina que precede
                            boolean precedenciaCumprida = pautaRepository.existsByEstudanteIdAndDisciplinaIdAndNotaFinalGreaterThanEqual(
                                    estudanteId,
                                    precedencia.getDisciplinaPrecede().getId(),
                                    10
                            );

                            if (!precedenciaCumprida) {
                                todasPrecedenciasCumpridas = false;
                                break;
                            }
                        }

                        // Adiciona a disciplina atrasada apenas se ela não tiver precedências
                        // ou se todas as precedências forem cumpridas
                        if (todasPrecedenciasCumpridas) {
                            disciplinasAtrasadas.add(new DisciplinaSemestreDTO(
                                    disciplina.getId(),
                                    disciplina.getDisciplina().getNome(),
                                    disciplina.getSemestreCurso(),
                                    disciplina.getAnoCurso().getCodigo(),
                                    disciplina.getSemestreAno().getCodigo(),
                                    disciplina.getCreditos()
                            ));
                        }
                    }
                }
            }
        }
        return disciplinasAtrasadas;
    }

    public List<DisciplinaSemestre> buscarDisciplinasDisponiveis(
            Curriculo curriculo, int semestreCurso, Long estudanteId) {

        return disciplinaSemestreRepository.findDisciplinasDisponiveisPorCurriculoSemestreCurso(
                curriculo, semestreCurso, estudanteId
        );
    }
}
