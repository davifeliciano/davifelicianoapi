package br.edu.infnet.davifelicianoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.davifelicianoapi.controller.exceptions.EncargoProjetadoInvalidoException;
import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.domain.Encargo;
import br.edu.infnet.davifelicianoapi.model.domain.EncargoProjetado;
import br.edu.infnet.davifelicianoapi.model.domain.Pagamento;

public class EncargoProjetadoTest {

    private Boleto boleto;

    @BeforeEach
    public void setUp() {
        boleto = new Boleto();
        boleto.setDataVencimento("2025-09-01");
        boleto.setValor(1000.0);
        Encargo encargo = new Encargo();
        boleto.setEncargo(encargo);
    }

    @Test
    @DisplayName("Deve lançar exceção quando boleto for nulo")
    public void deveLancarExcecao_QuandoBoletoNulo() {
        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(null)
                    .vencimentoUtil(LocalDate.parse("2025-09-01"))
                    .dataReferencia(LocalDate.parse("2025-09-10"))
                    .build();
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando vencimento útil for nulo")
    public void deveLancarExcecao_QuandoVencimentoUtilNulo() {
        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(boleto)
                    .vencimentoUtil(null)
                    .dataReferencia(LocalDate.parse("2025-09-10"))
                    .build();
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando data de referência for nula")
    public void deveLancarExcecao_QuandoDataReferenciaNula() {
        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(boleto)
                    .vencimentoUtil(LocalDate.parse("2025-09-01"))
                    .dataReferencia(null)
                    .build();
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando encargo do boleto for nulo")
    public void deveLancarExcecao_QuandoEncargoNulo() {
        boleto.setEncargo(null);

        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(boleto)
                    .vencimentoUtil(LocalDate.parse("2025-09-01"))
                    .dataReferencia(LocalDate.parse("2025-09-10"))
                    .build();
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando data de referência for anterior ao vencimento")
    public void deveLancarExcecao_QuandoDataReferenciaAnteriorVencimento() {
        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(boleto)
                    .vencimentoUtil(LocalDate.parse("2025-09-01"))
                    .dataReferencia(LocalDate.parse("2025-08-31"))
                    .build();
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o vencimento útil for anterior ao vencimento do boleto")
    public void deveLancarExcecao_QuandoVencimentoUtilAnteriorVencimentoBoleto() {
        boleto.setDataVencimento("2025-09-05");

        assertThrows(EncargoProjetadoInvalidoException.class, () -> {
            EncargoProjetado.builder()
                    .boleto(boleto)
                    .vencimentoUtil(LocalDate.parse("2025-09-01"))
                    .dataReferencia(LocalDate.parse("2025-09-10"))
                    .build();
        });
    }

    @Test
    @DisplayName("Deve calcular corretamente os dias em atraso quando a data de referência é posterior ao vencimento")
    public void deveCalcularDiasAtraso_QuandoReferenciaPosteriorVencimento() {
        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-02"))
                .build();

        assertEquals(1, encargoProjetado.getDiasAtraso());
    }

    @Test
    @DisplayName("Deve calcular zero dias em atraso quando a data de referência é igual ao vencimento")
    public void deveCalcularDiasAtraso_QuandoReferenciaIgualVencimento() {
        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-01"))
                .build();

        assertEquals(0, encargoProjetado.getDiasAtraso());
    }

    @Test
    @DisplayName("Deve calcular zero dias em atraso quando título pago antes do vencimento")
    public void deveCalcularDiasAtraso_QuandoPagoAntesVencimento() {
        boleto.getPagamentos().addAll(
                Arrays.asList(
                        new Pagamento(LocalDate.parse("2025-08-30"), 400.0),
                        new Pagamento(LocalDate.parse("2025-08-29"), 600.0)));

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(0, encargoProjetado.getDiasAtraso());
    }

    @Test
    @DisplayName("Deve calcular corretamente os dias em atraso quando título pago após o vencimento")
    public void deveCalcularDiasAtraso_QuandoPagoAposVencimento() {
        boleto.getPagamentos().addAll(
                Arrays.asList(
                        new Pagamento(LocalDate.parse("2025-09-05"), 400.0),
                        new Pagamento(LocalDate.parse("2025-09-06"), 600.0)));

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(5, encargoProjetado.getDiasAtraso());
    }

    @Test
    @DisplayName("Deve calcular corretamente os dias em atraso quando título pago parcialmente após o vencimento")
    public void deveCalcularDiasAtraso_QuandoPagoParcialmenteAposVencimento() {
        boleto.getPagamentos().addAll(
                Arrays.asList(
                        new Pagamento(LocalDate.parse("2025-09-03"), 400.0),
                        new Pagamento(LocalDate.parse("2025-09-06"), 300.0)));

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(9, encargoProjetado.getDiasAtraso());
    }

    @Test
    @DisplayName("Deve calcular corretamente a multa fixa quando há dias em atraso")
    public void deveCalcularMultaFixa_QuandoAtraso() {
        boleto.getEncargo().setMultaAtrasoFixa(20.0);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(20.0, encargoProjetado.getMultaAtrasoFixa());
    }

    @Test
    @DisplayName("Deve calcular multa fixa zero quando não há dias em atraso")
    public void deveCalcularMultaFixaZero_QuandoNaoAtraso() {
        boleto.getEncargo().setMultaAtrasoFixa(20.0);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-01"))
                .build();

        assertEquals(0.0, encargoProjetado.getMultaAtrasoFixa());
    }

    @Test
    @DisplayName("Deve calcular corretamente a multa percentual quando há dias em atraso")
    public void deveCalcularMultaPercentual_QuandoAtraso() {
        boleto.getEncargo().setMultaAtrasoPercentual(0.2);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(200.0, encargoProjetado.getMultaAtrasoPercentual());
    }

    @Test
    @DisplayName("Deve calcular multa percentual zero quando não há dias em atraso")
    public void deveCalcularMultaPercentualZero_QuandoNaoAtraso() {
        boleto.getEncargo().setMultaAtrasoPercentual(0.2);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-01"))
                .build();

        assertEquals(0.0, encargoProjetado.getMultaAtrasoPercentual());
    }

    @Test
    @DisplayName("Deve calcular corretamente o total da multa por atraso quando há dias em atraso")
    public void deveCalcularTotalMultaAtraso_QuandoAtraso() {
        boleto.getEncargo().setMultaAtrasoFixa(20.0);
        boleto.getEncargo().setMultaAtrasoPercentual(0.2);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        assertEquals(220.0, encargoProjetado.getTotalMultaAtraso());
    }

    @Test
    @DisplayName("Deve calcular corretamente o saldo da dívida quando há dias em atraso")
    public void deveCalcularSaldoDivida_QuandoAtraso() {
        double jurosDiarios = 0.01; // 1% ao dia
        boleto.getEncargo().setJurosDiarios(jurosDiarios);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        double saldoDividaEsperado = boleto.getValor()
                * Math.pow((1.0 + jurosDiarios), encargoProjetado.getDiasAtraso());

        assertEquals(saldoDividaEsperado, encargoProjetado.getSaldoDivida());
    }

    @Test
    @DisplayName("Deve calcular corretamente os juros diários compostos quando existirem pagamentos após o vencimento")
    public void deveCalcularJurosDiariosCompostos_QuandoPagamentosAposVencimento() {
        double jurosDiarios = 0.01; // 1% ao dia
        boleto.getEncargo().setJurosDiarios(jurosDiarios);
        boleto.getPagamentos().addAll(
                Arrays.asList(
                        new Pagamento(LocalDate.parse("2025-08-30"), 100.0),
                        new Pagamento(LocalDate.parse("2025-09-03"), 400.0),
                        new Pagamento(LocalDate.parse("2025-09-06"), 300.0)));

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-10"))
                .build();

        double saldoDivida = boleto.getValor() - 100.0;
        saldoDivida = saldoDivida * Math.pow((1.0 + jurosDiarios), 2);
        saldoDivida = (saldoDivida - 400.0) * Math.pow((1.0 + jurosDiarios), 3);
        saldoDivida = (saldoDivida - 300.0) * Math.pow((1.0 + jurosDiarios), 4);
        assertEquals(saldoDivida, encargoProjetado.getSaldoDivida());
    }

    @Test
    @DisplayName("Deve calcular saldo da dívida igual ao valor do título quando não há dias em atraso")
    public void deveCalcularJurosDiariosZero_QuandoNaoAtraso() {
        double jurosDiarios = 0.01; // 1% ao dia
        boleto.getEncargo().setJurosDiarios(jurosDiarios);

        EncargoProjetado encargoProjetado = EncargoProjetado.builder()
                .boleto(boleto)
                .vencimentoUtil(LocalDate.parse("2025-09-01"))
                .dataReferencia(LocalDate.parse("2025-09-01"))
                .build();

        assertEquals(boleto.getValor(), encargoProjetado.getSaldoDivida());
    }

}
