package siga.artsoft.api.pauta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import siga.artsoft.api.contacorrente.ContaCorrenteService;
import siga.artsoft.api.contacorrente.DadosDividaEstudanteDTO;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.sessaoturma.SessaoTurma;

import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

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
        DadosDividaEstudanteDTO statusDivida = contaCorrenteService.verificarDividaTotalEstudante(estudante.getId());

        if (statusDivida.isEstudanteEstaEmDivida()) {
            String mensagem = String.format(
                    "Acesso bloqueado. O estudante possui dívidas pendentes no valor total de %.2f Meticais. Por favor, regularize a situação financeira.",
                    statusDivida.getTotalDivida()
            );
            throw new BloqueioNotasException(mensagem);
        }
        return pautaRepository.findByEstudanteAndDisciplinaAndAnoLectivoAndSemestre(
                estudante, disciplina, anoLectivo, semestre);
    }

    public Optional<Pauta> findById(long l) {
        return pautaRepository.findById(l);
    }

    public class BloqueioNotasException extends RuntimeException {
        public BloqueioNotasException(String message) {
            super(message);
        }
    }
}
