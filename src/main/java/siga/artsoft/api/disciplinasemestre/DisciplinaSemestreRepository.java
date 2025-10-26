package siga.artsoft.api.disciplinasemestre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import siga.artsoft.api.curriculo.Curriculo;

import java.util.List;

public interface DisciplinaSemestreRepository extends JpaRepository<DisciplinaSemestre,Long> {

    @Query("SELECT d FROM DisciplinaSemestre d " +
            "JOIN FETCH d.disciplina " +
            "WHERE d.curriculo = :curriculo AND d.semestreCurso = :semestreCurso")
    List<DisciplinaSemestre> findDisciplinaSemestreByCurriculoSemestreCurso(
            @Param("curriculo") Curriculo curriculo,
            @Param("semestreCurso") int semestreCurso);

    @Query("""
    SELECT d FROM DisciplinaSemestre d 
    JOIN FETCH d.disciplina 
    LEFT JOIN Precedencia p ON p.disciplinaSucessede = d.disciplina 
    WHERE d.curriculo = :curriculo 
    AND d.semestreCurso = :semestreCurso
    AND (p IS NULL OR p.disciplinaPrecede.id IN (
        SELECT h.disciplina.disciplina.id 
        FROM Pauta h 
        WHERE h.estudante.id = :estudanteId AND h.notaFinal >= 10
    ))
    AND d.id NOT IN (
        SELECT h.disciplina.id
        FROM Pauta h
        WHERE h.estudante.id = :estudanteId AND h.notaFinal >= 10
    )
""")
    List<DisciplinaSemestre> findDisciplinasDisponiveisPorCurriculoSemestreCurso(
            @Param("curriculo") Curriculo curriculo,
            @Param("semestreCurso") int semestreCurso,
            @Param("estudanteId") Long estudanteId
    );
}
