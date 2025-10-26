package siga.artsoft.api.sessaoturma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import siga.artsoft.api.anocurso.AnoCurso;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.semestreano.SemestreAno;
import siga.artsoft.api.turno.Turno;

import java.util.List;

@Repository
public interface SessaoTurmaRepository extends JpaRepository<SessaoTurma,Long> {

    @Query("SELECT s FROM SessaoTurma s " +
            "WHERE s.curso = :curso " +
            "AND s.curriculo = :curriculo " +
            "AND s.turma.ano = :ano " +
            "AND s.turma.semestre = :semestre " +
            "AND s.turma.turno = :turno " +
            "AND s.anoLectivo = :anoLectivo")
    List<SessaoTurma> findByCursoCurriculoAnoSemestreTurno(
            @Param("curso") Curso curso,
            @Param("curriculo") Curriculo curriculo,
            @Param("ano") AnoCurso ano,
            @Param("semestre") SemestreAno semestre,
            @Param("turno") Turno turno,
            @Param("anoLectivo") int anoLectivo);

    @Query("SELECT s FROM SessaoTurma s " +
            "WHERE s.curso = :curso " +
            "AND s.curriculo = :curriculo " +
            "AND s.turma.ano = :ano " +
            "AND s.turma.semestre = :semestre " +
            "AND s.anoLectivo = :anoLectivo")
    List<SessaoTurma> findByCursoCurriculoAnoSemestre(
            @Param("curso") Curso curso,
            @Param("curriculo") Curriculo curriculo,
            @Param("ano") AnoCurso ano,
            @Param("semestre") SemestreAno semestre,
            @Param("anoLectivo") int anoLectivo);
}
