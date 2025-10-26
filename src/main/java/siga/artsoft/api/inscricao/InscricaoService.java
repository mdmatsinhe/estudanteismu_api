package siga.artsoft.api.inscricao;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.disciplina.Disciplina;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestreRepository;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteRepository;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.sessaoturma.SessaoTurmaRepository;
import siga.artsoft.api.user.User;
import siga.artsoft.api.user.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private DisciplinaSemestreRepository disciplinaSemestreRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private SessaoTurmaRepository sessaoTurmaRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void registrarInscricao(List<InscricaoRequest> inscricoes) {

        if (inscricoes.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma inscrição fornecida!");
        }

        // Busca o estudante uma vez, assumindo que todas as requisições são para o mesmo estudante
        Long estudanteId = inscricoes.get(0).getEstudanteId();
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));



        for (InscricaoRequest request : inscricoes) {
            try {
                SessaoTurma turma = sessaoTurmaRepository.getReferenceById(request.getTurmaId());
                DisciplinaSemestre disciplina = disciplinaSemestreRepository.getReferenceById(request.getDisciplinaId());
                Inscricao inscricao = new Inscricao();

                inscricao.setEstudante(estudante);
                inscricao.setDisciplinaSemestre(disciplina);
                inscricao.setTurma(turma);
                inscricao.setAnoLectivo(request.getAnoLectivo());
                inscricao.setSemestreCurso(request.getSemestreCurso());
                inscricao.setSemestre(request.getSemestre());
                inscricao.setNotaFinal(0.0);
                inscricao.setNotaFrequencia(0.0);
                inscricao.setStatus(request.isBolseiro() && "Completa".equalsIgnoreCase(request.getTipoBolsa()) ? "Confirmada" : "Pendente");
                inscricao.setDia(new Date());
                inscricao.setCreatedBy(estudante.getUserLogin());
                inscricao.setUpdatedBy(estudante.getUserLogin());

                if(!inscricaoRepository.existsByEstudanteAndDisciplinaSemestreAndAnoLectivoAndSemestre(estudante,disciplina,request.getAnoLectivo(), request.getSemestre())) {

                    turma.addEstudantes(estudante);
                    sessaoTurmaRepository.save(turma);
                    inscricaoRepository.save(inscricao);
                }
            }catch (Exception e) {
                // Lança a exceção para forçar o rollback
                throw new RuntimeException("Erro ao registrar inscrição: " + request.getDisciplinaId(), e);
            }
        }
    }

    public boolean isEstudanteInscrito(Long estudanteId, int anoLectivo, int semestre) {
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));
        return inscricaoRepository.existsByEstudanteAndAnoLectivoAndSemestre(estudante, anoLectivo, semestre);
    }

}
