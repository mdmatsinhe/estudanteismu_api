package siga.artsoft.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import siga.artsoft.api.user.UserRepository;

import java.util.Hashtable;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    UserRepository repository;

       public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           return repository.findByUsername(username);
       }

}
