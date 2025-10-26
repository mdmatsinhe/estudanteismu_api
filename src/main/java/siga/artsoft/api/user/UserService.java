package siga.artsoft.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.pauta.Pauta;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(long l) {
        return userRepository.findById(l);
    }
}
