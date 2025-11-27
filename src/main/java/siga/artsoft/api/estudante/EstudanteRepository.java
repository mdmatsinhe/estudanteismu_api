package siga.artsoft.api.estudante;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Estudante findEstudanteByUserLoginId(long idUser);

    @Query("SELECT MAX(e.numero) FROM Estudante e WHERE e.anoIngresso=:anoIngresso")
    String findLastNumeroByAnoLetivo(String anoIngresso);

    Estudante findByNumero(String numero);

    @Query("SELECT e FROM Estudante e " +
            "INNER JOIN FETCH e.curriculo c " +
            "INNER JOIN FETCH c.planoEstudos p " +
            "INNER JOIN FETCH p.disciplina " +
            "WHERE e.id = :id")
    Optional<Estudante> findByIdWithCurriculumAndPlan(@Param("id") Long id);

    // Exemplo forçando a atualização nativa
    @Modifying
    @Transactional
    @Query(value = "UPDATE estudante SET dados_actualizados = 1 WHERE id = :estudanteId", nativeQuery = true)
    void atualizarDadosActualizadosNativo(@Param("estudanteId") Long estudanteId);
}

