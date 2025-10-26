package siga.artsoft.api.distrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito,Long> {
    List<Distrito> findByProvinciaId(Long provinciaId);
}
