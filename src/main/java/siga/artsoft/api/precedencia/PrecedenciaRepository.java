package siga.artsoft.api.precedencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import siga.artsoft.api.curriculo.Curriculo;
import siga.artsoft.api.disciplina.Disciplina;

import java.util.List;

@Repository
public interface PrecedenciaRepository extends JpaRepository<Precedencia,Long> {

   List<Precedencia> findByCurriculoAndDisciplinaSucessede(Curriculo curriculo, Disciplina disciplina);
}
