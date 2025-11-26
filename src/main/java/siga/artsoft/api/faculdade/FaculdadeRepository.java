package siga.artsoft.api.faculdade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaculdadeRepository extends JpaRepository<Faculdade,Long> {

    List<Faculdade> findFaculdadeByDelegacaoId(Long delegacaoId);
}
