package br.edu.infnet.davifelicianoapi.model.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.edu.infnet.davifelicianoapi.model.exceptions.DataInvalidaException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "boleto_id")
    @JsonIgnoreProperties("pagamentos")
    private Boleto boleto;

    @NotNull(message = "A data de pagamento é obrigatória")
    private Date dataPagamento;

    @NotNull
    @Min(value = 0, message = "O valor deve ser positivo")
    private double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        try {
            this.dataPagamento = Date.valueOf(dataPagamento);
        } catch (IllegalArgumentException e) {
            throw new DataInvalidaException("Data de pagamento inválida. Formato esperado: AAAA-MM-DD");
        }
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
