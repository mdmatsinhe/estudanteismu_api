package siga.artsoft.api.candidatura;

import org.springframework.stereotype.Component;
import siga.artsoft.api.curso.Curso;
import siga.artsoft.api.delegacao.Delegacao;
import siga.artsoft.api.distrito.Distrito;
import siga.artsoft.api.faculdade.Faculdade;
import siga.artsoft.api.nacionalidade.Nacionalidade;
import siga.artsoft.api.provincia.Provincia;
import siga.artsoft.api.sexo.Sexo;
import siga.artsoft.api.tipodocumentoidentificacao.TipoDocumentoIdentificacao;
import siga.artsoft.api.turno.Turno;

@Component
public class CandidaturaMapper {

    public Candidatura toEntity(CandidaturaDTO dto) {
        Candidatura c = new Candidatura();
        c.setNome(dto.getNome());
        c.setApelido(dto.getApelido());
        c.setDataNascimento(dto.getDataNascimento());
        c.setTelefone(dto.getTelefone());
        c.setTelefoneFixo(dto.getTelefoneFixo());
        c.setEmail(dto.getEmail());
        c.setNumeroDoc(dto.getNumeroDoc());
        c.setEndereco(dto.getEndereco());
        c.setBairro(dto.getBairro());
        c.setCidade(dto.getCidade());
        c.setEstado(dto.getEstado());
        c.setNomePai(dto.getNomePai());
        c.setTelefonePai(dto.getTelefonePai());
        c.setNomeMae(dto.getNomeMae());
        c.setTelefoneMae(dto.getTelefoneMae());
        c.setTipoEncarregado(dto.getTipoEncarregado());
        c.setNomeEncarregado(dto.getNomeEncarregado());
        c.setTelefoneEncarregado(dto.getTelefoneEncarregado());
        c.setInstituicao(dto.getInstituicao());
        c.setNuit(dto.getNuit());

        // Apenas associa objetos por ID (sem precisar carregar tudo)
        if (dto.getDelegacaoId() != null) {
            Delegacao d = new Delegacao();
            d.setId(dto.getDelegacaoId());
            c.setDelegacao(d);
        }
        if (dto.getFaculdadeId() != null) {
            Faculdade f = new Faculdade();
            f.setId(dto.getFaculdadeId());
            c.setFaculdade(f);
        }
        if (dto.getCursoId() != null) {
            Curso curso = new Curso();
            curso.setId(dto.getCursoId());
            c.setCurso(curso);
        }
        if (dto.getTurnoId() != null) {
            Turno t = new Turno();
            t.setId(dto.getTurnoId());
            c.setTurno(t);
        }
        if (dto.getSexoId() != null) {
            Sexo s = new Sexo();
            s.setId(dto.getSexoId());
            c.setSexo(s);
        }
        if (dto.getTipoDocId() != null) {
            TipoDocumentoIdentificacao td = new TipoDocumentoIdentificacao();
            td.setId(dto.getTipoDocId());
            c.setTipoDoc(td);
        }
        if (dto.getNacionalidadeId() != null) {
            Nacionalidade n = new Nacionalidade();
            n.setId(dto.getNacionalidadeId());
            c.setNacionalidade(n);
        }
        if (dto.getProvinciaId() != null) {
            Provincia p = new Provincia();
            p.setId(dto.getProvinciaId());
            c.setProvincia(p);
        }
        if (dto.getDistritoId() != null) {
            Distrito d = new Distrito();
            d.setId(dto.getDistritoId());
            c.setDistrito(d);
        }

        return c;
    }
}
