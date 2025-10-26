package siga.artsoft.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.infra.security.DadosTokenJWT;
import siga.artsoft.api.infra.security.TokenService;
import siga.artsoft.api.user.*;


@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "localhost:4200")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> efectuarLogin(@RequestBody @Valid DadosLogin dados){

        var authenticationToken=new UsernamePasswordAuthenticationToken(dados.username(),dados.password());
        var authentication=manager.authenticate(authenticationToken);

        var tokenJWT=tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody DadosResetPassword dados){

        var user=userRepository.getReferenceById(dados.id());
      user.setPassword(encodePassword(dados.password()));
      user.setLoginCount(dados.loginCount()+1);

     return ResponseEntity.ok(new DadosDetalheUser(user));
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
