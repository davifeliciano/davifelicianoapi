package br.edu.infnet.davifelicianoapi.model.dtos;

import br.edu.infnet.davifelicianoapi.model.domain.Encargo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EncargoRequestComBoletoIdDTO {

    private Integer boletoId;

    @NotNull
    @Min(value = 0, message = "O valor da multa por atraso fixa deve ser positivo ou zero")
    private double multaAtrasoFixa;

    @NotNull
    @Min(value = 0, message = "O valor da multa por atraso percentual deve ser positivo ou zero")
    private double multaAtrasoPercentual;

    @NotNull
    @Min(value = 0, message = "O valor dos juros diários deve ser positivo ou zero")
    private double jurosDiarios;

    public Integer getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(Integer boletoId) {
        this.boletoId = boletoId;
    }

    public double getMultaAtrasoFixa() {
        return multaAtrasoFixa;
    }

    public void setMultaAtrasoFixa(double multaAtrasoFixa) {
        this.multaAtrasoFixa = multaAtrasoFixa;
    }

    public double getMultaAtrasoPercentual() {
        return multaAtrasoPercentual;
    }

    public void setMultaAtrasoPercentual(double multaAtrasoPercentual) {
        this.multaAtrasoPercentual = multaAtrasoPercentual;
    }

    public double getJurosDiarios() {
        return jurosDiarios;
    }

    public void setJurosDiarios(double jurosDiarios) {
        this.jurosDiarios = jurosDiarios;
    }

    public Encargo toEncargo() {
        Encargo encargo = new Encargo();
        encargo.setJurosDiarios(this.jurosDiarios);
        encargo.setMultaAtrasoFixa(this.multaAtrasoFixa);
        encargo.setMultaAtrasoPercentual(this.multaAtrasoPercentual);
        return encargo;
    }

}
