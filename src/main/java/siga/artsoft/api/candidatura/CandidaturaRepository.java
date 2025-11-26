package siga.artsoft.api.candidatura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    boolean existsByNumeroDocAndAnoCandidatura(String numeroDoc, Integer anoCandidatura);
}
