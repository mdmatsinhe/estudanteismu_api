package siga.artsoft.api.estudante;

import siga.artsoft.api.distrito.Distrito;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosListagemEstudante(

        Long id,
        String nome,
        String apelido,
        Long numero,
        LocalDateTime dataNascimento,
        LocalDateTime validadeDocumentoIdentificacao,
        String numeroDocumentoIdentificacao,
        Long distrito,
        Long estadoCivil,
        Long sexo,
        String nomePai,
        String nomeMae,
        String email,
        Long nacionalidade,
        long telefone,
        Long provinciaId,
        Long tipoDocumentoIdentificacao,
        String cursoNome,
        String curriculoNome,
        String endereco,
        Long cursoId,
        Long curriculoId,
        Long turnoId
) {

    public DadosListagemEstudante(Estudante estudante){
        this(estudante.getId(), estudante.getNome(),estudante.getApelido(),
                estudante.getNumero(),estudante.getDataNascimento(),
                estudante.getValidadeDocumentoIdentificacao(),
                estudante.getNumeroDocumentoIdentificacao(),
                estudante.getDistrito()!=null?estudante.getDistrito().getId():0L,estudante.getEstadoCivil()!=null?estudante.getEstadoCivil().getId():0L,
                estudante.getSexo()!=null?estudante.getSexo().getId():0L,estudante.getNomePai(),estudante.getNomeMae(),
                estudante.getEmail(),estudante.getNacionalidade()!=null?estudante.getNacionalidade().getId():0L,
                estudante.getTelefone(),estudante.getDistrito()!=null?estudante.getDistrito().getProvincia().getId():0L,
                estudante.getTipoDocumentoIdentificacao()!= null ?
                        estudante.getTipoDocumentoIdentificacao().getId() : 0L,
                estudante.getCurso().getNome(),
                estudante.getCurriculo().getDescricao(),
                estudante.getEndereco(),
                estudante.getCurso().getId(),
                estudante.getCurriculo().getId(),
                estudante.getTurno().getId());
    }
}
