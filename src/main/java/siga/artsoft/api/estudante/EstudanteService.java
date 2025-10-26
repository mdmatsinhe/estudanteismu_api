package siga.artsoft.api.estudante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.distrito.DistritoRepository;
import siga.artsoft.api.estadocivil.EstadoCivil;
import siga.artsoft.api.estadocivil.EstadoCivilRepository;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.nacionalidade.NacionalidadeRepository;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.sexo.SexoRepository;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacaoRepository;
import siga.artsoft.api.user.UserRepository;

import java.util.Optional;

@Service
public class EstudanteService {

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
        estudante.setValidadeDocumentoIDentificacao(estudanteDTO.getValidadeDocumentoIdentificacao());
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

 /*   public boolean verificarPrimeiroAcesso(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user.isPrimeiroAcesso();
    } */
}
