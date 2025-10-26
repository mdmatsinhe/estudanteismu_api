package siga.artsoft.api.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    List<Curso> findCursoByFaculdadeId(Long faculdadeId);
}
