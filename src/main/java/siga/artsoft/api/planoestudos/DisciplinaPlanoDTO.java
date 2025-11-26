package siga.artsoft.api.planoestudos;

public record DisciplinaPlanoDTO(
        String codigo,
        String nomeDisciplina,
        Integer anoCurso,
        Integer semestre,
        Double creditos,
        Integer cargaHoraria,
        Integer semestre_id
) {
}