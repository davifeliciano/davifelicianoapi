package br.edu.infnet.davifelicianoapi.model.domain;

import java.sql.Date;

import br.edu.infnet.davifelicianoapi.controller.exceptions.EncargoProjetadoInvalidoException;

public class EncargoProjetado {

    private final Boleto boleto;
    private final Date vencimentoUtil;
    private final Date dataReferencia;
    private int diasAtraso;
    private double multaAtrasoFixa;
    private double multaAtrasoPercentual;
    private double totalMultaAtraso;
    private double jurosDiarios;
    private double totalEncargos;

    private EncargoProjetado(Builder builder) {
        this.boleto = builder.boleto;
        this.vencimentoUtil = builder.vencimentoUtil;
        this.dataReferencia = builder.dataReferencia;
        validar();
        calcular();
    }

    public static class Builder {
        private Boleto boleto;
        private Date vencimentoUtil;
        private Date dataReferencia;

        public Builder boleto(Boleto boleto) {
            this.boleto = boleto;
            return this;
        }

        public Builder vencimentoUtil(Date vencimentoUtil) {
            this.vencimentoUtil = vencimentoUtil;
            return this;
        }

        public Builder dataReferencia(Date dataReferencia) {
            this.dataReferencia = dataReferencia;
            return this;
        }

        public EncargoProjetado build() {
            return new EncargoProjetado(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public Date getVencimentoUtil() {
        return vencimentoUtil;
    }

    public Date getDataReferencia() {
        return dataReferencia;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public double getMultaAtrasoFixa() {
        return multaAtrasoFixa;
    }

    public double getMultaAtrasoPercentual() {
        return multaAtrasoPercentual;
    }

    public double getTotalMultaAtraso() {
        return totalMultaAtraso;
    }

    public double getJurosDiarios() {
        return jurosDiarios;
    }

    public double getTotalEncargos() {
        return totalEncargos;
    }

    private void calcularDiasAtraso() {
        throw new UnsupportedOperationException();
    }

    private void calcularMultaAtrasoPercentual() {
        throw new UnsupportedOperationException();
    }

    private void calcularMultaAtrasoFixa() {
        throw new UnsupportedOperationException();
    }

    private void calcularTotalMultaAtraso() {
        throw new UnsupportedOperationException();
    }

    private void calcularJurosDiarios() {
        throw new UnsupportedOperationException();
    }

    private void calcularTotalEncargos() {
        throw new UnsupportedOperationException();
    }

    private void validar() throws EncargoProjetadoInvalidoException {
        throw new UnsupportedOperationException();
    }

    private void calcular() {
        calcularDiasAtraso();
        calcularMultaAtrasoPercentual();
        calcularMultaAtrasoFixa();
        calcularTotalMultaAtraso();
        calcularJurosDiarios();
        calcularTotalEncargos();
    }

}
