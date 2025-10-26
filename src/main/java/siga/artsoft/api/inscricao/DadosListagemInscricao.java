package siga.artsoft.api.inscricao;

public record DadosListagemInscricao(

        String disciplina,
        int ano,
        int semestre,
        String turma,
        String tipoInscricao,
        Long idDisciplina,
        Long idEstudante,
        Long idTurma,
        int anoLectivo
) {
    public DadosListagemInscricao(Inscricao inscricao){
      this(
              inscricao.getDisciplinaSemestre().getDisciplina().getNome(),
              inscricao.getDisciplinaSemestre().getAnoCurso().getId().intValue(),
              inscricao.getDisciplinaSemestre().getSemestreAno().getId().intValue(),
              inscricao.getTurma().getTurma().getNome(),inscricao.getTipoInscricao(),
              inscricao.getDisciplinaSemestre().getId(),
              inscricao.getEstudante().getId(),
              inscricao.getTurma().getId(),
              inscricao.getAnoLectivo()
      );
    }
}
