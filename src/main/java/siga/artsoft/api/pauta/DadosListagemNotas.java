package siga.artsoft.api.pauta;

public record DadosListagemNotas(
        long id,
        int anoLectivo,
        int semestre,
        String disciplina,
        double notaTeste1,
        boolean publicadaTeste1,
        double notaTeste2,
        boolean publicadaTeste2,
        double notaTrabalho,
        boolean publicadaTrabalho,
        double notaFrequencia,
        boolean publicadaFrequencia,
        String resultadoFrequencia,
        double notaExameNormal,
        boolean publicadaExameNormal,
        double notaExameRecorrencia,
        boolean publicadaExameRecorrencia,
        double notaFinal,
        boolean publicadaNotaFinal,
        String resultadoFinal) {

    public DadosListagemNotas(Pauta pauta){
        this(
                pauta.getId(),
                pauta.getAnoLectivo(),
                pauta.getSemestre(),
                pauta.getDisciplina().getDisciplina().getNome(),
                pauta.getNotaTeste1(),
                pauta.isPublicadaTeste1(),
                pauta.getNotaTeste2(),
                pauta.isPublicadaTeste2(),
                pauta.getNotaTrabalho(),
                pauta.isPublicadaTrabalho(),
                pauta.getNotaFrequencia(),
                pauta.isPublicadaFrequencia(),
                pauta.getResultadoFrequencia(),
                pauta.getNotaExameNormal(),
                pauta.isPublicadoExameNormal(),
                pauta.getNotaExameRecorrencia(),
                pauta.isPublicadoExameRecorrencia(),
                pauta.getNotaFinal(),
                pauta.isPublicadaNotaFinal(),
                pauta.getResultadoFinal());
    }
}
