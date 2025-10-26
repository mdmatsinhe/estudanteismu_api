package siga.artsoft.api.estudante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import siga.artsoft.api.user.User;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Estudante findEstudanteByUserLoginId(long idUser);

}
