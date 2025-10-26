package siga.artsoft.api.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao,Long> {

    @Query(""" 
    select i from Inscricao i
    where
    i.estudante.id=:idEstudante
    and
   i.anoLectivo=:anoLectivo and i.semestre=:semestre
    
""")
    List<Inscricao> escolherInscricaoPorEstudanteAnoLectivoSemestre(long  idEstudante, int anoLectivo, int semestre);

    List<Inscricao> findByEstudanteIdAndAnoLectivoAndSemestre(Long estudanteId, int anoLectivo, int semestre);

    boolean existsByEstudanteAndDisciplinaSemestreAndAnoLectivoAndSemestre(
            Estudante estudante, DisciplinaSemestre disciplinaSemestre, int anoLectivo, int semestre);

    boolean existsByEstudanteAndAnoLectivoAndSemestre(Estudante estudante, int anoLectivo, int semestre);

    boolean existsByEstudanteAndDisciplinaSemestre(Estudante estudante, DisciplinaSemestre disciplinaSemestre);
}
