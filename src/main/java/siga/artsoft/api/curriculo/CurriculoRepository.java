package siga.artsoft.api.curriculo;

import org.springframework.data.jpa.repository.JpaRepository;
import siga.artsoft.api.curso.Curso;

import java.util.List;

public interface CurriculoRepository extends JpaRepository<Curriculo,Long> {

    List<Curriculo> findCurriculoByCurso(Curso curso);
}
