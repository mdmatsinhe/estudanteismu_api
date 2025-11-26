package siga.artsoft.api.controller;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import siga.artsoft.api.email.EmailService;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteRepository;
import siga.artsoft.api.infra.security.TokenService;
import siga.artsoft.api.user.*;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "localhost:8080")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private EmailService  emailService;

    @PostMapping
    public ResponseEntity<?> efectuarLogin(@RequestBody @Valid DadosLogin dados){

        var authenticationToken=new UsernamePasswordAuthenticationToken(dados.username(),dados.password());
        var authentication=manager.authenticate(authenticationToken);

        User loggedInUser = (User) authentication.getPrincipal();

        Estudante estudante = estudanteRepository.findEstudanteByUserLoginId(loggedInUser.getId()); // Use o ID do User

        var tokenJWT=tokenService.gerarToken(loggedInUser);

        return ResponseEntity.ok(new DadosLoginResponse(
                tokenJWT,
                estudante.getId(),
                estudante.getNumero(),
                estudante.getNome(),
                estudante.getApelido(),
                estudante.getCurso().getNome(),
                estudante.getUserLogin().getId(),
                estudante.getEndereco(),
                estudante.getTelefone(),
                estudante.getNumeroDocumentoIdentificacao(),
                estudante.getDataNascimento(),
                estudante.getEmail(),
                estudante.getDistrito().getNome(),
                estudante.getDistrito().getProvincia().getNome(),
                estudante.getSexo().getNome(),
                estudante.getNacionalidade().getNacionalidade()
        ));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody DadosResetPassword dados){

        var user=userRepository.getReferenceById(dados.id());

      user.setPassword(encodePassword(dados.password()));
      user.setLoginCount(dados.loginCount()+1);

     return ResponseEntity.ok(new DadosDetalheUser(user));
    }


    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody @Valid DadosResetPasswordOnline dados) {
        var estudante = estudanteRepository.findByNumero(dados.codigoEstudante());
        if (estudante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "erro", "mensagem", "Estudante não encontrado com o código fornecido."));
        }

        if (!estudante.getDataNascimento().toLocalDate().isEqual(dados.dataNascimento())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", "Data de nascimento incorreta."));
        }

        String email = estudante.getEmail();
        if (email == null || !isEmailValido(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", "O e-mail associado à sua conta é inválido ou está incorreto. Por favor, contacte o Sector Académico."));
        }

        String senhaProvisoria = gerarSenhaProvisoria();
        User user = estudante.getUserLogin();
        user.setPassword(encodePassword(senhaProvisoria));
        user.setLoginCount(0);
        userRepository.save(user);

        Map<String, Object> vars = Map.of(
                "nomeEstudante", estudante.getNome() +" "+ estudante.getApelido(),
                "senhaProvisoria", senhaProvisoria
        );

        try {
            emailService.enviarEmail(
                    email,
                    "Recuperação de Senha - Sistema Integrado de Gestão Académica",
                    "reset-password-email",
                    vars
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", "Não foi possível enviar o e-mail de recuperação. Verifique o endereço de e-mail e tente novamente."));
        }

        return ResponseEntity.ok(Map.of(
                "status", "sucesso",
                "mensagem", "Senha provisória enviada com sucesso! Verifique o seu e-mail para obter a nova senha."
        ));
    }



    private boolean isEmailValido(String email) {
        if (email == null || email.isBlank()) return false;

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(email).matches()) {
            return false;
        }

        if (email.endsWith(".co") || email.endsWith(".cm") || email.endsWith(".con")) {
            return false;
        }

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();

            String domain = email.substring(email.indexOf("@") + 1);
            return hasMXRecord(domain);

        } catch (AddressException e) {
            return false;
        }
    }
    private boolean hasMXRecord(String domain) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});

            return attrs != null && attrs.get("MX") != null;
        } catch (NamingException e) {
            return false;
        }
    }

    private String gerarSenhaProvisoria() {
        int length = 6;
        String caracteres = "0123456789";
        Random random = new Random();
        StringBuilder senha = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return senha.toString();
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
