package br.edu.infnet.boletoapi.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Encargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = 0, message = "O valor da multa por atraso fixa deve ser positivo ou zero")
    private double multaAtrasoFixa;

    @NotNull
    @Min(value = 0, message = "O valor da multa por atraso percentual deve ser positivo ou zero")
    private double multaAtrasoPercentual;

    @NotNull
    @Min(value = 0, message = "O valor dos juros diários deve ser positivo ou zero")
    private double jurosDiarios;

    @OneToOne
    @JsonBackReference
    private Boleto boleto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    @Override
    public String toString() {
        return String.format(
                "Encargo(id=%d, boletoId=%s, multaAtrasoFixa=%.2f, multaAtrasoPercentual=%.2f, jurosDiarios=%.2f)", id,
                boleto.getId(),
                multaAtrasoFixa, multaAtrasoPercentual, jurosDiarios);
    }

}
