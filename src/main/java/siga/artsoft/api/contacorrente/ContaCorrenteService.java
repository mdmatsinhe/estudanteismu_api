package siga.artsoft.api.contacorrente;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siga.artsoft.api.curso.CursoService;
import siga.artsoft.api.disciplinasemestre.DisciplinaSemestreService;
import siga.artsoft.api.emolumento.Emolumento;
import siga.artsoft.api.emolumento.EmolumentoRepository;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.estudante.EstudanteRepository;
import siga.artsoft.api.inscricao.Inscricao;
import siga.artsoft.api.inscricao.InscricaoRequest;
import siga.artsoft.api.mpesa.MpesaC2BService;
import siga.artsoft.api.mpesa.MpesaPushResponse;
import siga.artsoft.api.pauta.Pauta;
import siga.artsoft.api.emolumento.EmolumentoService;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.sessaoturma.SessaoTurmaRepository;
import siga.artsoft.api.tipoemolumento.TipoEmolumento;
import siga.artsoft.api.user.User;
import siga.artsoft.api.user.UserRepository;
import siga.artsoft.api.utils.TipoEmolumentoSelecionado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private EmolumentoService emolumentoService;

    @Autowired
    private EmolumentoRepository emolumentoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private SessaoTurmaRepository sessaoTurmaRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaSemestreService disciplinaSemestreService;

    @Autowired
    private MpesaC2BService mpesaC2BService;


    @Transactional
    public ContaCorrente gerarReferencia(Optional<Estudante> estudanteParam, Optional<Pauta> pautaParam, Optional<User> userParam, int anoLectivo, int semestre) {

        // Verifica se os Optional contêm valores, caso contrário, lança uma exceção apropriada
        Estudante estudante = estudanteParam.orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado."));
        Pauta pauta = pautaParam.orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada."));
        User user = userParam.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Verifica se já existe uma referência para o exame de recorrência dessa disciplina no ano letivo e semestre
        String tipoEmolumento = "Taxa de Exame de Recorrência - " + pauta.getDisciplina().getDisciplina().getNome();
        if (contaCorrenteRepository.existsByEstudanteAndTipoEmolumentoAndAnoLectivoAndSemestre(estudante, tipoEmolumento, anoLectivo, semestre)) {
            throw new IllegalStateException("Já existe uma referência para Taxa de Exame de Recorrência para esta Disciplina.");
        }

        ContaCorrente transacao = new ContaCorrente();
        transacao.setAnoLectivo(anoLectivo);
        transacao.setSemestre(semestre);

        // Obtém o emolumento para o exame de recorrência (ID 42)
        var emolumento = emolumentoService.findById(107L).orElseThrow(() -> new IllegalStateException("Emolumento não encontrado"));

        if(estudante.getCurso().getFaculdade().getId().intValue()==2){
            emolumento=emolumentoService.findById(108L).orElseThrow(() -> new IllegalStateException("Emolumento não encontrado"));
        }

        // Preenche os dados da transação
        transacao.setEstudante(estudante);
        transacao.setDebito(emolumento.getValor());
        transacao.setTaxaImposto(5);
        transacao.setTotalDebito(transacao.getDebito().multiply(BigDecimal.valueOf(1 + transacao.getTaxaImposto() / 100.0)));
        transacao.setValorIva(transacao.getDebito().multiply(BigDecimal.valueOf(transacao.getTaxaImposto() / 100.0))
        );
        // Define a data limite para o pagamento (uma semana a partir da data atual)
        Calendar dataAtual = Calendar.getInstance();

        // Adicionar 5 dias à data atual
        dataAtual.add(Calendar.DAY_OF_MONTH, 5);

        transacao.setDataLimitePagamento(dataAtual.getTime());

        transacao.setTurma(pauta.getLeccionamento().getTurma());

        // Gera a referência parcial com o método adequado
        transacao.setReferenciaParcialOT(estudante, emolumento.getTipoEmolumento(), dataAtual.getTime(), transacao.getTotalDebito());

        // Define o tipo de emolumento e os metadados
        transacao.setTipoEmolumento(tipoEmolumento);
        transacao.setEmolumento(emolumento.getTipoEmolumento());
        //transacao.setCreatedBy();
        transacao.setSituacao("RESERVADA");
        transacao.setUpdatedBy(user);
        transacao.setCreatedBy(user);

        return contaCorrenteRepository.save(transacao);
    }

    public List<ContaCorrente> listarReferenciasRecorrenciaPendentes(Optional<Estudante> estudante, int anoLectivo, int semestre) {
        return contaCorrenteRepository.findByEstudanteAndPagoFalseAndAnoLectivoAndSemestreAndTipoEmolumentoContains(
                estudante, anoLectivo, semestre, "Taxa de Exame de Recorrência");
    }

    public ContaCorrente existeDebitoByEstudante(Estudante estudante, String tipoEmolumento) {
        return contaCorrenteRepository.existeDebitoByEstudante(estudante.getId(), tipoEmolumento);
    }
    @Transactional
    public ContaCorrente gerarTaxaMatricula(Long estudanteId, int anoLectivo, int semestre) {

        Estudante estudante=estudanteRepository.getReferenceById(estudanteId);
        User user=userRepository.getReferenceById(1L);

        // Define a data limite para o pagamento (uma semana a partir da data atual)
        Calendar dataAtual = Calendar.getInstance();

        // Adicionar 5 dias à data atual
        dataAtual.add(Calendar.DAY_OF_MONTH, 5);

        List<Emolumento> emolumentos=emolumentoRepository.findAll();
        Emolumento taxaMatricula=null;
        for(Emolumento emolumento:emolumentos){
            if (estudante.getCurso().getId().intValue() >= 721 && estudante.getCurso().getId().intValue() <= 724) {

                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("MATESDA1112")) {
                    taxaMatricula = emolumento;
                }
            }
            if (estudante.getCurso().getId().intValue()  >= 717
                    && estudante.getCurso().getId().intValue()  <= 720) {

                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("MATESDA1112")) {
                    taxaMatricula = emolumento;
                }

            }
            if (estudante.getCurso().getId().intValue()  >= 714&&estudante.getCurso().getId().intValue()<=716||estudante.getCurso().getId().intValue()==725) {

                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("MATESDA78910")) {
                    taxaMatricula = emolumento;
                }
            }
            if (estudante.getCurso().getId().intValue()  >= 714&&estudante.getCurso().getId().intValue()<=716||estudante.getCurso().getId().intValue()==725) {

                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("MATESDA78910")) {
                    taxaMatricula = emolumento;
                }
            }

            if(estudante.getCurso().getFaculdade().getId().intValue()==2){
                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("MATIMEP23")) {
                    taxaMatricula = emolumento;
                }
            }

            if(estudante.getCurso().getFaculdade().getId().intValue()==1){
                if (emolumento.getTipoEmolumento().getAbreviatura().equalsIgnoreCase("TADM")) {
                    taxaMatricula = emolumento;
                }
            }

        }

        ContaCorrente ccTaxaMatricula = new ContaCorrente();
        ccTaxaMatricula.setAnoLectivo(anoLectivo);
        ccTaxaMatricula.setSemestre(semestre);
        System.out.println(estudante.getCurso().getFaculdade().getDelegacao().getId().intValue()+" "+estudante.getNome()+" curso "+estudante.getCurso().getNome());

        if(estudante.getCurso().getFaculdade().getId().intValue()==1){
            ccTaxaMatricula.setTipoEmolumento("Taxa de Admissão para o ano " + anoLectivo);
            ccTaxaMatricula.setEmolumento(taxaMatricula.getTipoEmolumento());
            ccTaxaMatricula.setDebito(taxaMatricula.getValor());
        }else if(estudante.getCurso().getFaculdade().getId().intValue()==2){
            ccTaxaMatricula.setTipoEmolumento("Taxa de Matrícula para o ano  " + anoLectivo);
            ccTaxaMatricula.setEmolumento(taxaMatricula.getTipoEmolumento());
            ccTaxaMatricula.setDebito(taxaMatricula.getValor());
        }else if(estudante.getCurso().getFaculdade().getId().intValue()==3){
            ccTaxaMatricula.setTipoEmolumento("Taxa de Matrícula e Inscrição para o ano " + anoLectivo);
            ccTaxaMatricula.setEmolumento(taxaMatricula.getTipoEmolumento());
            ccTaxaMatricula.setDebito(taxaMatricula.getValor());
        }

        ccTaxaMatricula.setEstudante(estudante);
        ccTaxaMatricula.setPago(false);
        ccTaxaMatricula.setDataLimitePagamento(dataAtual.getTime());

        ccTaxaMatricula.setTurma(null);

        if (this.existeDebitoByEstudante(estudante, ccTaxaMatricula.getTipoEmolumento()) == null) {
            ccTaxaMatricula.setTaxaImposto(5); // Taxa de imposto de 5%
            ccTaxaMatricula.setTotalDebito(BigDecimal.valueOf(ccTaxaMatricula.getDebito().doubleValue()
                    * Double.parseDouble("1.0" + ccTaxaMatricula.getTaxaImposto())));
            ccTaxaMatricula.setValorIva(BigDecimal.valueOf(ccTaxaMatricula.getDebito().doubleValue()
                    * Double.parseDouble("0.0" + ccTaxaMatricula.getTaxaImposto())));
            ccTaxaMatricula.setReferenciaParcialOT(estudante, taxaMatricula.getTipoEmolumento(), dataAtual.getTime(), ccTaxaMatricula.getTotalDebito());
            ccTaxaMatricula.setCreatedBy(user);
            ccTaxaMatricula.setUpdatedBy(user);
            ccTaxaMatricula.setSituacao("RESERVADA");
            ccTaxaMatricula.setSistema("SIGA");

            // Criação da conta corrente
            contaCorrenteRepository.save(ccTaxaMatricula);
        }
        return ccTaxaMatricula;
    }

    public void gerarDebitos(Estudante estudante, SessaoTurma turma, TipoEmolumento emolumento,
                             BigDecimal valor, Date dataLimite, String tipo, String sistema) {

        ContaCorrente debitoExistente = contaCorrenteRepository.findByEstudanteAndTipoEmolumento(estudante, tipo);

        if (debitoExistente == null) {
            ContaCorrente conta = new ContaCorrente();
            conta.setAnoLectivo(turma.getAnoLectivo());
            conta.setSemestre(turma.getSemestre());
            conta.setEstudante(estudante);
            conta.setTipoEmolumento(tipo);
            conta.setDebito(valor);
            conta.setTotalDebito(valor.multiply(BigDecimal.valueOf(1.05))); // Incluindo taxa de imposto
            conta.setValorIva(valor.multiply(BigDecimal.valueOf(0.05)));
            conta.setPago(false);
            conta.setTaxaImposto(5);
            conta.setDataLimitePagamento(dataLimite);
            conta.setEmolumento(emolumento);
            conta.setTurma(turma);
            conta.setSituacao("RESERVADA");
            conta.setReferenciaParcial(estudante,emolumento,dataLimite,conta.getTotalDebito());
            conta.setSistema(sistema);
            conta.setCreatedBy(estudante.getUserLogin());
            conta.setUpdatedBy(estudante.getUserLogin());

            contaCorrenteRepository.save(conta);
        }
    }

    public void gerarTaxaInscricaoNormais(Estudante estudante, SessaoTurma turma, TipoEmolumento emolumento, BigDecimal valor, Date dataLimite, String sistema) {
        String descricao = "Taxa de Inscrição "+ turma.getSemestre() + "° semestre de " + turma.getAnoLectivo();

        gerarDebitos(estudante, turma, emolumento, valor, dataLimite,descricao, sistema);
    }

    public void gerarTaxaInscricaoAvulsas(Estudante estudante, SessaoTurma turma, TipoEmolumento emolumento, BigDecimal valor, Date dataLimite, String sistema, int numeroDisciplinas) {
        String descricao = "Taxa de Inscrição de "
                + numeroDisciplinas + " cadeira(s) avulsa(s) " + turma.getSemestre() + "° semestre de " + turma.getAnoLectivo();

        gerarDebitos(estudante, turma, emolumento, valor.multiply(BigDecimal.valueOf(numeroDisciplinas)), dataLimite,descricao, sistema);
    }

    public void gerarMensalidadesNormais(Estudante estudante, SessaoTurma turma, TipoEmolumento emolumento, BigDecimal valorMensal, int quantidadeMensalidades, Date dataInicio, String sistema) {

        boolean isPresencial = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("Regular");
        boolean isEAD = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("A Distância");
        boolean isFDS = estudante.isFDS();

        String descricaoPropina = "Propina Presencial";

        if(isPresencial){
            descricaoPropina = "Propina Presencial";
            if(isFDS){
                descricaoPropina = "Propina Presencial FDS";
            }
        }else if(isEAD){
            descricaoPropina = "Propina EAD";
            if(isFDS){
                descricaoPropina = "Propina EAD FDS";
            }
        }

        Calendar inicio = Calendar.getInstance();
        // Ajusta dataInicio conforme o semestre
        if (turma.getSemestre() == 1) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.FEBRUARY);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Fevereiro
        } else if (turma.getSemestre() == 2) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.AUGUST);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Julho
        } else {
            throw new IllegalArgumentException("Semestre inválido: " + turma.getSemestre());
        }

        for (int i = 1; i <= quantidadeMensalidades; i++) {
            Calendar prazo = (Calendar)inicio.clone();
            prazo.setTime(dataInicio);
            if (prazo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                prazo.add(Calendar.DAY_OF_MONTH, 1); // Ajusta para segunda-feira, se necessário
            }
            prazo.add(Calendar.MONTH, i - 1); // Incrementa o prazo por mês

            String descricao = i + "ª Prestação de "+descricaoPropina+" "+ turma.getSemestre() + "° semestre de " + turma.getAnoLectivo();
            gerarDebitos(estudante, turma, emolumento, valorMensal, prazo.getTime(),descricao, sistema);
        }
    }

    public void gerarMensalidadesAvulsas(Estudante estudante, SessaoTurma turma, TipoEmolumento emolumento, BigDecimal valorMensal, int quantidadeMensalidades, Date dataInicio, String sistema, int numeroDisciplinas) {
        boolean isPresencial = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("Regular");
        boolean isEAD = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("A Distância");
        boolean isFDS = estudante.isFDS();

        String descricaoPropina = "Propina Presencial";

        if(isPresencial){
            descricaoPropina = "Propina Presencial";
            if(isFDS){
                descricaoPropina = "Propina Presencial FDS";
            }
        }else if(isEAD){
            descricaoPropina = "Propina EAD";
            if(isFDS){
                descricaoPropina = "Propina EAD FDS";
            }
        }
        Calendar inicio = Calendar.getInstance();
        // Ajusta dataInicio conforme o semestre
        if (turma.getSemestre() == 1) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.FEBRUARY);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Fevereiro
        } else if (turma.getSemestre() == 2) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.AUGUST);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Julho
        } else {
            throw new IllegalArgumentException("Semestre inválido: " + turma.getSemestre());
        }

        for (int i = 1; i <= quantidadeMensalidades; i++) {
            Calendar prazo = (Calendar) inicio.clone();
            prazo.setTime(dataInicio);
            if (prazo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                prazo.add(Calendar.DAY_OF_MONTH, 1); // Ajusta para segunda-feira, se necessário
            }
            prazo.add(Calendar.MONTH, i - 1); // Incrementa o prazo por mês

            String descricao = i + "ª Prestação de "+descricaoPropina+" de " + numeroDisciplinas +" cadeira(s) avulsa(s) " + turma.getSemestre() + "° semestre de " + turma.getAnoLectivo();
            gerarDebitos(estudante, turma, emolumento, valorMensal.multiply(BigDecimal.valueOf(numeroDisciplinas)), prazo.getTime(),descricao, sistema);
        }
    }
    @Transactional
    public void gerarPlanoFinanceiro(List<InscricaoRequest> inscricoes) {

        // Determina o nível mais alto de inscrição
        InscricaoRequest inscricaoNivelMaisAlto = inscricoes.stream()
                .max(Comparator.comparing(inscricao -> inscricao.getAnoCurso()))
                .orElseThrow(() -> new IllegalArgumentException("Não foi possível determinar o nível mais alto."));

        Estudante estudante = estudanteRepository.findById(inscricaoNivelMaisAlto.getEstudanteId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado!"));
        SessaoTurma turma=sessaoTurmaRepository.getReferenceById(inscricaoNivelMaisAlto.getTurmaId());
        int planoMensalidades = inscricaoNivelMaisAlto.getPlanoMensalidades(); // 5 ou 6
        int anoCurso = inscricaoNivelMaisAlto.getAnoCurso();

        boolean isPresencial = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("Regular");
        boolean isEAD = estudante.getCurso().getRegimeCurso().getNome().equalsIgnoreCase("A Distância");
        boolean isFDS = estudante.isFDS();

        TipoEmolumentoSelecionado emolSelecionado = selecionarEmolumentos(estudante, turma.getSemestre(), anoCurso, isPresencial, isEAD, isFDS);


        // Carregar emolumentos
       // Map<String, Emolumento> emolumentos = emolumentoService.carregarEmolumentos();
        Date prazoInscricao = calcularPrazoInscricao();
        Date prazoPrimeiraPrestacao=calcularPrazoPrimeiraPrestacao(turma);

        // Buscar total de disciplinas da grade curricular
        int totalDisciplinasGrade = disciplinaSemestreService.buscarDisciplinasPorCurriculoESemestre(estudante.getCurriculo(),inscricaoNivelMaisAlto.getSemestreCurso()).size();

        // Verificar o total de disciplinas inscritas
        int totalDisciplinasInscritas = inscricoes.size();

        Emolumento taxaInscricaoNormal = emolSelecionado.getTaxaInscricao();
        Emolumento taxaInscricaoAvulsa = emolSelecionado.getTaxaAtraso();
       // String codigoNivel = "PML" + anoCurso + cursoService.getAreaCurso(estudante.getCurso().getId().intValue()).getAbreviatura();
        Emolumento propinaNormal = emolSelecionado.getPropina();

        System.out.println(propinaNormal.getTipoEmolumento().getAbreviatura());
      //  String codigoNivelAvulsa = "PMDAL"+cursoService.getAreaCurso(estudante.getCurso().getId().intValue()).getAbreviatura();
        Emolumento propinaAvulsa = emolSelecionado.getPropinaAvulsa();

        // Cenário 1: Até 3 disciplinas
        if (inscricoes.size() <= 3) {
            gerarTaxaInscricaoAvulsas(estudante,turma,taxaInscricaoAvulsa.getTipoEmolumento(),taxaInscricaoAvulsa.getValor(),prazoInscricao,"SIGA",inscricoes.size());
            gerarMensalidadesAvulsas(estudante,turma,propinaAvulsa.getTipoEmolumento(),propinaAvulsa.getValor(),planoMensalidades,prazoPrimeiraPrestacao,"SIGA",inscricoes.size());
        }
        // Cenário 3: Mais disciplinas que a grade curricular
        else if (totalDisciplinasInscritas > totalDisciplinasGrade) {
            // Verifica se há disciplinas extras
            int disciplinasExtras = Math.abs(totalDisciplinasInscritas - totalDisciplinasGrade);

            gerarTaxaInscricaoNormais(estudante, turma, taxaInscricaoNormal.getTipoEmolumento(), taxaInscricaoNormal.getValor(), prazoInscricao, "SIGA");
            gerarTaxaInscricaoAvulsas(estudante,turma,taxaInscricaoAvulsa.getTipoEmolumento(),taxaInscricaoAvulsa.getValor(),prazoInscricao,"SIGA",disciplinasExtras);

            gerarMensalidadesNormais(estudante, turma, propinaNormal.getTipoEmolumento(), propinaNormal.getValor(), planoMensalidades, prazoPrimeiraPrestacao, "SIGA");
            gerarMensalidadesAvulsas(estudante,turma,propinaAvulsa.getTipoEmolumento(),propinaAvulsa.getValor(),planoMensalidades,prazoPrimeiraPrestacao,"SIGA",disciplinasExtras);


        }
        // Cenário 2: Inscrição normal (já implementado)
        else {
            gerarTaxaInscricaoNormais(estudante, turma, taxaInscricaoNormal.getTipoEmolumento(), taxaInscricaoNormal.getValor(), prazoInscricao, "SIGA");
            gerarMensalidadesNormais(estudante, turma, propinaNormal.getTipoEmolumento(), propinaNormal.getValor(), planoMensalidades, prazoPrimeiraPrestacao, "SIGA");
        }

    }

    private Date calcularPrazoInscricao() {
        // Implementação para calcular a data inicial (prazo)
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.AUGUST, 5);
      //  calendar.add(Calendar.DAY_OF_MONTH, 2); // Exemplo: adiciona dois dias a partir de hoje
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajusta para segunda-feira, se necessário
        }
        return calendar.getTime();
    }

    private Date calcularPrazoPrimeiraPrestacao(SessaoTurma turma) {
        Calendar inicio = Calendar.getInstance();
        // Ajusta dataInicio conforme o semestre
        if (turma.getSemestre() == 1) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.FEBRUARY);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Fevereiro
        } else if (turma.getSemestre() == 2) {
            inicio.set(Calendar.YEAR, turma.getAnoLectivo());
            inicio.set(Calendar.MONTH, Calendar.AUGUST);
            inicio.set(Calendar.DAY_OF_MONTH, 5); // Exemplo: 5 de Julho
        } else {
            throw new IllegalArgumentException("Semestre inválido: " + turma.getSemestre());
        }
        return inicio.getTime();
    }

    public DadosDividaEstudanteDTO verificarDividaTotalEstudante(Long idEstudante) {
        Estudante estudante = estudanteRepository.findById(idEstudante)
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado com o ID: " + idEstudante));

        // Busca todas as transações de conta corrente para o estudante
        List<ContaCorrente> transacoes = contaCorrenteRepository.findByEstudante(estudante);

        Date dataAtual = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dataAtualSemHora = cal.getTime();

        List<ContaCorrente> dividasAtivas = transacoes.stream()
                .filter(transacao -> {
                    // Condição 1: A data limite de pagamento já passou (data_limite_pagamento < CURDATE())
                    boolean dataLimiteVencida = false;
                    if (transacao.getDataLimitePagamento() != null) {
                        Calendar calLimite = Calendar.getInstance();
                        calLimite.setTime(transacao.getDataLimitePagamento());
                        calLimite.set(Calendar.HOUR_OF_DAY, 0);
                        calLimite.set(Calendar.MINUTE, 0);
                        calLimite.set(Calendar.SECOND, 0);
                        calLimite.set(Calendar.MILLISECOND, 0);

                        dataLimiteVencida = calLimite.getTime().before(dataAtualSemHora);
                    }

                    // Condição 2: O valor não foi pago
                    boolean temSaldoDevedor = transacao.getTotalDebito() != null &&
                            transacao.getTotalDebito().compareTo(BigDecimal.ZERO) > 0 && !transacao.isPago();

                    // Condição 3: A situação da transação NÃO é 'CANCELADO', 'CANCELADA' ou 'NEGOCIADA'
                    boolean situacaoValida = transacao.getSituacao() != null &&
                            !transacao.getSituacao().equalsIgnoreCase("CANCELADO") &&
                            !transacao.getSituacao().equalsIgnoreCase("CANCELADA") &&
                            !transacao.getSituacao().equalsIgnoreCase("NEGOCIADA");

                    // Condição 4: A taxa de desconto NÃO é 100%
                    boolean naoTemDescontoTotal = transacao.getTaxaDesconto() < 100;


                    return dataLimiteVencida && temSaldoDevedor && situacaoValida && naoTemDescontoTotal;
                })
                .collect(Collectors.toList());


        BigDecimal totalDivida = BigDecimal.ZERO;

        for (ContaCorrente transacao : dividasAtivas) {
            if (transacao.getTotalDebito() != null) {
                totalDivida = totalDivida.add(transacao.getTotalDebito());
            }
        }

        boolean estudanteEstaEmDivida = totalDivida.compareTo(BigDecimal.ZERO) > 0;

        return new DadosDividaEstudanteDTO(totalDivida, estudanteEstaEmDivida);
    }

    public TipoEmolumentoSelecionado selecionarEmolumentos(Estudante estudante, int semestre, int nivelCodigo, boolean isPresencial, boolean isEAD, boolean isFDS) {
      //  TabelaEmolumento tabela = tabelaEmolumentoService.findTabelaEmolumentoActiva();
       // Map<String, Emolumento> emolumentos = emolumentoService.carregarEmolumentos();
        List<Emolumento> emolumentos = emolumentoRepository.findAll();
        TipoEmolumentoSelecionado selecionado = new TipoEmolumentoSelecionado();

        for (Emolumento emol : emolumentos) {
            String abrev = emol.getTipoEmolumento().getAbreviatura();

            // Matrícula
            if (estudante.getAnoIngresso() == 2025) {
                if (isPresencial && abrev.equalsIgnoreCase("IMVPP")) {
                    selecionado.setTaxaMatricula(emol);
                } else if (isEAD && abrev.equalsIgnoreCase("IMVPEAD")) {
                    selecionado.setTaxaMatricula(emol);
                }
            } else {
                if (isPresencial && abrev.equalsIgnoreCase("RENMATPRES")) {
                    selecionado.setTaxaMatricula(emol);
                } else if (isEAD && abrev.equalsIgnoreCase("RENMATEAD")) {
                    selecionado.setTaxaMatricula(emol);
                }
            }

            // Taxa de inscrição
            if (isPresencial && abrev.equalsIgnoreCase("RISD")) {
                selecionado.setTaxaInscricao(emol);
            } else if (isEAD) {
                if (semestre == 1 && abrev.equalsIgnoreCase("RENINSCEAD")) {
                    selecionado.setTaxaInscricao(emol);
                } else if (semestre == 2 && abrev.equalsIgnoreCase("RINSEAD")) {
                    selecionado.setTaxaInscricao(emol);
                }
            }

            // Taxa de inscrição de disciplina em atraso
            if (abrev.equalsIgnoreCase("RINSEAD")) {
                selecionado.setTaxaAtraso(emol);
            }

            // Taxa propina por número de disciplinas atrasadas
            if (abrev.startsWith("PROPMN") && abrev.endsWith("C2A")) {
                selecionado.setPropinaAvulsa(emol); // você pode filtrar melhor com base no número depois
            }

            // Propina normal (Presencial ou EAD)
            if (isPresencial) {
                if (estudante.getAnoIngresso() < 2023&&nivelCodigo==4) {

                        if (abrev.equalsIgnoreCase(nivelCodigo + "PP22") || (isFDS && abrev.equalsIgnoreCase(nivelCodigo + "PPFDS22"))) {
                            selecionado.setPropina(emol);
                        }
                } else {
                    if (isFDS) {
                        if (abrev.equalsIgnoreCase(nivelCodigo + "PPLFDS23") || abrev.equalsIgnoreCase(nivelCodigo + "PPPFDS23")) {
                            selecionado.setPropina(emol);
                        }
                    } else if (estudante.getTurno().getTurno().equalsIgnoreCase("Manhã") || estudante.getTurno().getTurno().equalsIgnoreCase("Tarde")) {
                        if (abrev.equalsIgnoreCase(nivelCodigo + "PPL23")) {
                            selecionado.setPropina(emol);
                        }
                    } else {
                        if (abrev.equalsIgnoreCase(nivelCodigo + "PPP23")) {
                            selecionado.setPropina(emol);
                        }
                    }
                }
            } else if (isEAD) {
                if (estudante.getAnoIngresso() < 2024&&nivelCodigo>=3) {

                        if (abrev.equalsIgnoreCase(nivelCodigo + "PEAD22") || (isFDS && abrev.equalsIgnoreCase(nivelCodigo + "PEADFDS22"))) {
                            selecionado.setPropina(emol);
                        }

                } else {
                    if (abrev.equalsIgnoreCase(nivelCodigo + "PEAD24") || (isFDS && abrev.equalsIgnoreCase(nivelCodigo + "PEADFDS24"))) {
                        selecionado.setPropina(emol);
                    }
                }
            }
        }

        return selecionado;
    }

    @Transactional
    public MpesaPushResponse iniciarPagamentoMpesa(Long contaId, String msisdn) {
        ContaCorrente conta = contaCorrenteRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("ContaCorrente não encontrada."));

        if (conta.isPago()) {
            throw new IllegalStateException("Pagamento já realizado para esta transação.");
        }

        BigDecimal valorAPagar = conta.getTotalDebito(); // Valor obrigatório

        String thirdPartyReference = "TP" + contaId;
        String msisdnFormatado = formatarNumeroMpesa(msisdn);

        // Chama o serviço de push M-Pesa
        MpesaPushResponse pushResponse = mpesaC2BService.enviarSTKPush(
                msisdnFormatado,
                valorAPagar.toPlainString(),
                conta.getReferencia(),
                thirdPartyReference
        );
        // Armazena ThirdPartyReference na transação para identificar o callback
        conta.setReferenciaMpesa(thirdPartyReference);
        conta.setTelefonePagamento(msisdn);

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Usuário padrão não encontrado."));

        String transactionId = pushResponse.getTransactionId();
        // Atualiza todos os campos
        conta.setNumeroTalao(transactionId);
        conta.setDataPagamento(new Date());
        conta.setMeioPagamento("API-M-PESA");
        conta.setNumeroConta("871072194");
        conta.setValorDepositado(valorAPagar);
        conta.setCredito(valorAPagar);
        conta.setPago(true);
        conta.setBanco("M-PESA");
        conta.setSituacao("OK");
        conta.setUpdatedBy(user);
        conta.setUser(user);
        conta.setDataRegisto(new Date());

        contaCorrenteRepository.save(conta);

        return pushResponse;
    }

    @Transactional
    public void confirmarPagamentoMpesa(String thirdPartyReference, String transactionId, BigDecimal valorPago, Optional<User> userParam) {
        ContaCorrente conta = contaCorrenteRepository.findByReferenciaMpesa(thirdPartyReference);
        User user = userParam.orElseGet(() -> userRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Usuário padrão não encontrado.")));


        if (conta == null) {
            throw new IllegalArgumentException("Transação não encontrada para o ThirdPartyReference: " + thirdPartyReference);
        }

        if (conta.isPago()) {
            return; // Já processada
        }

        // Valor deve ser exatamente o total_debito
        if (valorPago.compareTo(conta.getTotalDebito()) != 0) {
            throw new IllegalStateException("O valor pago não corresponde ao valor devido.");
        }

        // Atualiza todos os campos
        conta.setNumeroTalao(transactionId);
        conta.setDataPagamento(new Date());
        conta.setMeioPagamento("API-M-PESA");
        conta.setNumeroConta("871072194");
        conta.setValorDepositado(valorPago);
        conta.setCredito(valorPago);
        conta.setPago(true);
        conta.setBanco("M-PESA");
        conta.setSituacao("OK");
        conta.setUpdatedBy(user);
        conta.setUser(user);
        conta.setDataRegisto(new Date());

        contaCorrenteRepository.save(conta);
    }

    public String formatarNumeroMpesa(String numero) {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("Número M-Pesa inválido");
        }

        // Remove tudo que não for dígito
        numero = numero.replaceAll("\\D", "");

        // Se já tiver 12 dígitos e começar com 258, assume que está correto
        if (numero.length() == 12 && numero.startsWith("258")) {
            numero = numero.substring(3); // pega só os 9 dígitos para validação
        } else if (numero.length() == 9) {
            // número local, já está ok
        } else if (numero.length() == 10 && numero.startsWith("0")) {
            numero = numero.substring(1); // remove o 0 inicial
        } else {
            throw new IllegalArgumentException("Número M-Pesa inválido ou incompleto: " + numero);
        }

        // Validação prefixos Vodacom: 84, 85
        String prefixo = numero.substring(0, 2);
        if (!prefixo.equals("84") && !prefixo.equals("85")) {
            throw new IllegalArgumentException("Número inválido. Só são aceitos números Vodacom em Moçambique (84, 85).");
        }

        // Adiciona o código do país
        return "258" + numero;
    }
    public Optional<ContaCorrente> getContaPorReferencia(String thirdPartyReference) {
        return Optional.ofNullable(contaCorrenteRepository.findByReferenciaMpesa(thirdPartyReference));
    }

}
