package siga.artsoft.api.inscricao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InscricaoRequest {

    private Long estudanteId;
    private Long disciplinaId;
    private String turma;
    private Long turmaId;
    private int anoLectivo;
    private int semestre;
    private int anoCurso;
    private int semestreCurso;
    private boolean bolseiro;
    private String tipoBolsa;
    private int planoMensalidades;


}
