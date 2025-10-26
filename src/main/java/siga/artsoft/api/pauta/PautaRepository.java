package siga.artsoft.api.pauta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestre;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.sessaoturma.SessaoTurma;

import java.util.List;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query(""" 
    select p from Pauta p
    where
    p.estudante.id=:idEstudante
    and
   p.anoLectivo=:anoLectivo and p.semestre=:semestre
    
""")
    List<Pauta> escolherPautaPorEstudanteAnoLectivoSemestre(long  idEstudante, int anoLectivo, int semestre);

    @Query(""" 
    select p from Pauta p
    where
    p.estudante.id=:idEstudante
    order by p.anoLectivo asc
  
""")
    List<Pauta> escolherPautaPorEstudante(long idEstudante);

    @Query("""
            select p from Pauta p
            where p.estudante = ?1 and p.disciplina = ?2 and p.anoLectivo = ?3 and p.semestre = ?4 and p.notaExameNormal < ?5 and p.gradedExameNormal = true and p.publicadoExameNormal = true""")
    List<Pauta> findByEstudanteAndDisciplinaAndAnoLectivoAndSemestreAndNotaExameNormalLessThanAndGradedExameNormalTrueAndPublicadoExameNormalTrue(
            Estudante estudante, DisciplinaSemestre disciplina, int anoLectivo, int semestre, double nota);

    @Query("""
            select p from Pauta p
            where p.estudante = ?1 and p.disciplina = ?2 and p.anoLectivo = ?3 and p.semestre = ?4 and p.turma=?5""")
    List<Pauta> findByEstudanteAndDisciplinaAndAnoLectivoAndSemestreAndTurma(
            Estudante estudante, DisciplinaSemestre disciplina, int anoLectivo, int semestre, SessaoTurma turma);

    @Query("""
            select p from Pauta p
            where p.estudante = ?1 and p.anoLectivo = ?2 and p.semestre = ?3 and (p.notaExameNormal < ?4 and p.gradedExameNormal = true or p.notaFinal<10) and p.publicadoExameNormal = true""")
    List<Pauta> findByEstudanteAndDisciplinaAndAnoLectivoAndSemestreAndNotaExameNormalLessThanAndGradedExameNormalTrueAndPublicadoExameNormalTrue(
            Estudante estudante, int anoLectivo, int semestre, double nota);

    @Query("""
            select p from Pauta p
            where p.estudante = ?1 and p.disciplina = ?2 and p.anoLectivo = ?3 and p.semestre = ?4""")
    List<Pauta> findByEstudanteAndDisciplinaAndAnoLectivoAndSemestre(
            Estudante estudante, DisciplinaSemestre disciplina, int anoLectivo, int semestre);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Pauta p " +
            "WHERE p.estudante.id = :estudanteId " +
            "AND p.disciplina.disciplina.id = :disciplinaId " +
            "AND p.notaFinal >= :notaMinima")
    boolean existsByEstudanteIdAndDisciplinaIdAndNotaFinalGreaterThanEqual(
            @Param("estudanteId") Long estudanteId,
            @Param("disciplinaId") Long disciplinaId,
            @Param("notaMinima") double notaMinima);
}