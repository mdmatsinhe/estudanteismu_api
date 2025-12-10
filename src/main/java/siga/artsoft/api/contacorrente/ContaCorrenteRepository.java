package siga.artsoft.api.contacorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import siga.artsoft.api.estudante.Estudante;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente,Long> {

    @Query(""" 
    select c from ContaCorrente c
    where
    c.estudante.id=:idEstudante
    and
   c.anoLectivo=:anoLectivo and c.semestre=:semestre
    
""")
    List<ContaCorrente> escolherContaCorrentePorEstudanteAnoLectivoSemestre(long  idEstudante, int anoLectivo, int semestre);

    boolean existsByEstudanteAndTipoEmolumentoAndAnoLectivoAndSemestre(Estudante estudante, String tipoEmolumento, int anoLectivo, int semestre);

    // Novo método para listar apenas referências de exame de recorrência em um ano letivo e semestre específicos
    List<ContaCorrente> findByEstudanteAndPagoFalseAndAnoLectivoAndSemestreAndTipoEmolumentoContains(
            Optional<Estudante> estudante, int anoLectivo, int semestre, String tipoEmolumento);

    @Query("SELECT c FROM ContaCorrente c WHERE c.estudante.id = :estudanteId AND c.tipoEmolumento = :tipoEmolumento AND c.pago = false")
    ContaCorrente existeDebitoByEstudante(@Param("estudanteId") Long estudanteId, @Param("tipoEmolumento") String tipoEmolumento);


    ContaCorrente findByEstudanteAndTipoEmolumento(Estudante estudante, String tipoEmolumento);

    List<ContaCorrente> findByEstudante(Estudante estudante);

    ContaCorrente findByReferenciaMpesa(String thirdPartyReference);
}
