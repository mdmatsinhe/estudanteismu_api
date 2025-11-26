package siga.artsoft.api.estudante;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.distrito.DistritoRepository;
import siga.artsoft.api.estadocivil.EstadoCivil;
import siga.artsoft.api.estadocivil.EstadoCivilRepository;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.nacionalidade.NacionalidadeRepository;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.provincia.ProvinciaRepository;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.sexo.SexoRepository;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacaoRepository;
import siga.artsoft.api.user.UserRepository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class EstudanteService {

    public record TokenData(
            String token,
            String email,
            long expiryTime,
            LocalDateTime dataNascimento
    ) {}

    private static final long EXPIRATION_TIME_MS = 15 * 60 * 1000L;
    private static final Map<Long, TokenData> PENDING_EMAIL_VERIFICATIONS = new ConcurrentHashMap<>();


    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    @Autowired
    private TipoDocumentoIdentificacaoRepository tipoDocumentoIdentificacaoRepository;

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private NacionalidadeRepository nacionalidadeRepository;

    @Autowired
    private SexoRepository sexoRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Optional<Estudante> findById(long l) {
        return estudanteRepository.findById(l);
    }

    public Estudante updateEstudante(Long id, EstudanteDTO estudanteDTO) {
        // 1. Buscar o estudante pelo ID
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        // 2. Atualizar os campos do estudante com os dados do DTO
        estudante.setNome(estudanteDTO.getNome());
        estudante.setApelido(estudanteDTO.getApelido());
       // estudante.setNumero(estudanteDTO.getNumero());
        estudante.setDataNascimento(estudanteDTO.getDataNascimento());
        estudante.setValidadeDocumentoIdentificacao(estudanteDTO.getValidadeDocumentoIdentificacao());
        estudante.setNumeroDocumentoIdentificacao(estudanteDTO.getNumeroDocumentoIdentificacao());
        estudante.setNomePai(estudanteDTO.getNomePai());
        estudante.setNomeMae(estudanteDTO.getNomeMae());
        estudante.setEmail(estudanteDTO.getEmail());
        estudante.setTelefone(estudanteDTO.getTelefone() != null ? estudanteDTO.getTelefone() : 0L);

        // 3. Atualizar entidades relacionadas, caso os IDs sejam fornecidos
        if (estudanteDTO.getDistrito() != null) {
            Distrito distrito = distritoRepository.findById(estudanteDTO.getDistrito())
                    .orElseThrow(() -> new RuntimeException("Distrito não encontrado"));
            estudante.setDistrito(distrito);
        } else {
            estudante.setDistrito(null);
        }

        if (estudanteDTO.getEstadoCivil() != null) {
            EstadoCivil estadoCivil = estadoCivilRepository.findById(estudanteDTO.getEstadoCivil())
                    .orElseThrow(() -> new RuntimeException("Estado civil não encontrado"));
            estudante.setEstadoCivil(estadoCivil);
        } else {
            estudante.setEstadoCivil(null);
        }

        if (estudanteDTO.getSexo() != null) {
            Sexo sexo = sexoRepository.findById(estudanteDTO.getSexo())
                    .orElseThrow(() -> new RuntimeException("Sexo não encontrado"));
            estudante.setSexo(sexo);
        } else {
            estudante.setSexo(null);
        }

        if (estudanteDTO.getNacionalidade() != null) {
            Nacionalidade nacionalidade = nacionalidadeRepository.findById(estudanteDTO.getNacionalidade())
                    .orElseThrow(() -> new RuntimeException("Nacionalidade não encontrada"));
            estudante.setNacionalidade(nacionalidade);
        } else {
            estudante.setNacionalidade(null);
        }

        if (estudanteDTO.getTipoDocumentoIdentificacao() != null) {
            TipoDocumentoIdentificacao tipoDoc = tipoDocumentoIdentificacaoRepository.findById(estudanteDTO.getTipoDocumentoIdentificacao())
                    .orElseThrow(() -> new RuntimeException("Tipo de Documento de Identificação não encontrado"));
            estudante.setTipoDocumentoIdentificacao(tipoDoc);
        } else {
            estudante.setTipoDocumentoIdentificacao(null);
        }

        // 4. Salvar as atualizações no banco de dados e retornar o estudante atualizado
        return estudanteRepository.save(estudante);

    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email não pode estar vazio.");
        }

        // Regex padrão de e-mail
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(email).matches()) {
            throw new IllegalArgumentException("O formato do email é inválido.");
        }

        // TLDs proibidos
        if (email.endsWith(".co") || email.endsWith(".cm") || email.endsWith(".con")) {
            throw new IllegalArgumentException("O domínio do email não é permitido.");
        }

        try {
            // valida sintaxe adicional via JavaMail
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            throw new IllegalArgumentException("O email contém caracteres inválidos.");
        }

        // Valida se domínio tem MX record
        String domain = email.substring(email.indexOf("@") + 1);
        if (!hasMXRecord(domain)) {
            throw new IllegalArgumentException("O domínio do email não possui registro MX válido.");
        }
    }

    public Estudante iniciarAtualizacaoEmailEDataNascimento(Long id, EstudantePrimeiroLoginDTO dto) {

//        try {
//            System.out.println("=== Recebido DTO ===");
//            System.out.println(new ObjectMapper().writeValueAsString(dto));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        // Buscar estudante
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com o ID: " + id));

        // Validar email
        validarEmail(dto.getEmail());

        // Dados básicos
        estudante.setNome(dto.getNome());
        estudante.setApelido(dto.getApelido());
        estudante.setEmail(dto.getEmail());
        estudante.setNumeroDocumentoIdentificacao(dto.getNumeroDocumentoIdentificacao());
        estudante.setDadosActualizados(true);

        // Telefone
        if (dto.getTelefone() != null && !dto.getTelefone().isBlank()) {
            estudante.setTelefone(Long.parseLong(dto.getTelefone()));
        }

        // Nuit
        if (dto.getNuit() != null) {
            estudante.setNuit(dto.getNuit());
        }

        // Data de nascimento
        estudante.setDataNascimento(dto.getDataNascimento());

        // Sexo
        Sexo sexo = sexoRepository.findById(dto.getSexoId())
                .orElseThrow(() -> new RuntimeException("Sexo não encontrado com ID: " + dto.getSexoId()));
        estudante.setSexo(sexo);

        // Nacionalidade
        Nacionalidade nacionalidade = nacionalidadeRepository.findById(dto.getNacionalidadeId())
                .orElseThrow(() -> new RuntimeException("Nacionalidade não encontrada com ID: " + dto.getNacionalidadeId()));
        estudante.setNacionalidade(nacionalidade);

        // Distrito
        Distrito distrito = distritoRepository.findById(dto.getDistritoId())
                .orElseThrow(() -> new RuntimeException("Distrito não encontrado com ID: " + dto.getDistritoId()));
        estudante.setDistrito(distrito);

        // Tipo Documento
        TipoDocumentoIdentificacao tipoDoc = tipoDocumentoIdentificacaoRepository.findById(dto.getTipoDocumentoIdentificacaoId())
                .orElseThrow(() -> new RuntimeException("Tipo de Documento não encontrado com ID: " + dto.getTipoDocumentoIdentificacaoId()));
        estudante.setTipoDocumentoIdentificacao(tipoDoc);

        // Estado Civil
        if (dto.getEstadoCivilId() != null) {
            EstadoCivil estadoCivil = estadoCivilRepository.findById(dto.getEstadoCivilId())
                    .orElseThrow(() -> new RuntimeException("Estado Civil não encontrado com ID: " + dto.getEstadoCivilId()));
            estudante.setEstadoCivil(estadoCivil);
        }

        // Provincia
//        Provincia provincia = provinciaRepository.findById(dto.getProvinciaConclusaoEnsinoMedioId())
//                .orElseThrow(() -> new RuntimeException("Provincia não encontrada com ID: " + dto.getProvinciaConclusaoEnsinoMedioId()));
//        estudante.setProvinciaConclusaoEnsinoMedio(provincia);

        // Salvar e retornar
        return estudanteRepository.save(estudante);
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
}
