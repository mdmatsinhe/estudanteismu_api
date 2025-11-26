package siga.artsoft.api.contacorrente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import siga.artsoft.api.estudante.Estudante;
import siga.artsoft.api.sessaoturma.SessaoTurma;
import siga.artsoft.api.tipoemolumento.TipoEmolumento;
import siga.artsoft.api.user.User;
import siga.artsoft.api.utils.IdEntity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Table(name="contacorrente")
@Entity(name="ContaCorrente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrente extends IdEntity {

    private static final long serialVersionUID = 8741355248630718268L;

    @ManyToOne
    private Estudante estudante;

    @Column(name = "valor_depositado")
    private BigDecimal valorDepositado = BigDecimal.ZERO;

    @Column(name = "tipo_emolumento")
    private String tipoEmolumento;

    @Column(name = "debito")
    private BigDecimal debito= BigDecimal.ZERO;

    @Column(name = "credito")
    private BigDecimal credito= BigDecimal.ZERO;

    @Column(name = "taxa_desconto")
    private int taxaDesconto=0;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto= BigDecimal.ZERO;

    @Column(name = "motivo_desconto")
    private String motivoDesconto;

    @Column(name = "pago")
    private boolean pago;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_limite_pagamento")
    private Date dataLimitePagamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Column(name = "meio_pagamento")
    private String meioPagamento;

    @Column(name = "numero_conta")
    private String numeroConta;

    @Column(name = "banco")
    private String banco;

    @Column(name = "numero_talao")
    private String numeroTalao;

    @Column(name = "recibo")
    private Long recibo = Long.valueOf(0);

    @Column(name = "ano_lectivo")
    private int anoLectivo = 0;

    @Column(name = "semestre")
    private int semestre;

    @Column(name = "taxa_imposto")
    private int taxaImposto;

    @Column(name = "total_debito")
    private BigDecimal totalDebito= BigDecimal.ZERO;

    @Column(name = "valor_iva")
    private BigDecimal valorIva= BigDecimal.ZERO;

    @Column(name = "situacao")
    private String situacao;

    @Column(name = "sistema")
    private String sistema;

    @ManyToOne
    private SessaoTurma turma;

    @ManyToOne
    private TipoEmolumento emolumento;

    @ManyToOne
    private User user;

    private String referencia;

    @SuppressWarnings("deprecation")
    public void setReferenciaParcialOT(Estudante estudante, TipoEmolumento eml, Date mes, BigDecimal saldo) {
        GregorianCalendar dataActual = new GregorianCalendar();
        dataActual.setTime(new Date());
        String tipoemolumento = "0";
        String valor = "";

        System.out.println(estudante.getId() + " tipo emolumento " + tipoemolumento + " ");
        String ano_lectivo = this.getAnoLectivo() + "";
        System.out.println(ano_lectivo + "XXX");
        tipoemolumento = ano_lectivo.substring(2);

        valor = new DecimalFormat("#0.00").format(saldo);

        if (valor.indexOf(".") > 0) {
            valor = valor.replace(".", "");
        } else {
            valor = valor + "00";
        }

        int pi = 0, si = 0, pn = 0, checkDigit = 0;
        StringBuilder numEstudante = new StringBuilder(estudante.getNumero() + "");

        while (numEstudante.length() != 8) {
            numEstudante.append(0);
        }
        String numero = numEstudante.substring(3);

        String codigoEntidade = "40202";

        String ms = mes.getMonth() + "";
        if (ms.equalsIgnoreCase("0")) {
            ms = 1 + "";
        }
        if (ms.length() < 2) {
            ms = "0" + ms;
        }

        Random r = new Random();
        String randomNumber = String.format("%04d", r.nextInt(10001));
        System.out.println(randomNumber);

        dataActual.add(Calendar.MILLISECOND, 1);
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dataActual.getTime());
        System.out.println(
                datetime + " verificando o valor " + codigoEntidade + " " + numero + " " + randomNumber + " " + valor);

        String digitos = codigoEntidade + numero + randomNumber + valor;

        for (int i = 1; i <= digitos.length(); i++) {
            si = (Integer.parseInt("" + digitos.charAt(i - 1))) + pi;
            pi = (si * 10) % 97;
        }

        pn = (pi * 10) % 97;
        checkDigit = 98 - pn;
        String checkD = checkDigit < 10 ? "0" + checkDigit : "" + checkDigit;

        this.referencia = numero + randomNumber + checkD;
        System.out.println(codigoEntidade + " " + numero + " " + randomNumber + " " + valor);

    }

    public void setReferenciaParcial(Estudante estudante, TipoEmolumento eml, Date mes, BigDecimal saldo) {

        String tipoemolumento = "0";
        String valor = "";

        String ano_lectivo = this.getAnoLectivo() + "";

        tipoemolumento = ano_lectivo.substring(2);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#0.00", symbols);
        valor = df.format(saldo);

        if (valor.indexOf(".") > 0) {
            valor = valor.replace(".", "");
        } else {
            valor = valor + "00";
        }

        int pi = 0, si = 0, pn = 0, checkDigit = 0;
        StringBuilder numEstudante = new StringBuilder(estudante.getNumero() + "");

        while (numEstudante.length() != 8) {
            numEstudante.append(0);
        }
        String numero = numEstudante.substring(3);

        String codigoEntidade = "40202";

        String ms = mes.getMonth() + "";
        if (ms.equalsIgnoreCase("0")) {
            ms = 1 + "";
        }
        if (ms.length() < 2) {
            ms = "0" + ms;
        }

        String digitos = codigoEntidade + numero + tipoemolumento + ms + valor;

        for (int i = 1; i <= digitos.length(); i++) {
            si = (Integer.parseInt("" + digitos.charAt(i - 1))) + pi;
            pi = (si * 10) % 97;
        }

        pn = (pi * 10) % 97;
        checkDigit = 98 - pn;
        String checkD = checkDigit < 10 ? "0" + checkDigit : "" + checkDigit;
        this.referencia = numero + tipoemolumento + ms + checkD;

    }


}
