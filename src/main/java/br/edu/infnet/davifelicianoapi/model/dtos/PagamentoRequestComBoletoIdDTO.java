package br.edu.infnet.davifelicianoapi.model.dtos;

import java.sql.Date;

import br.edu.infnet.davifelicianoapi.model.domain.Pagamento;
import br.edu.infnet.davifelicianoapi.model.exceptions.DataInvalidaException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PagamentoRequestComBoletoIdDTO {

    private Integer boletoId;

    @NotNull(message = "A data de pagamento é obrigatória")
    private Date dataPagamento;

    @NotNull
    @Min(value = 0, message = "O valor deve ser positivo")
    private double valor;

    public Integer getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(Integer boletoId) {
        this.boletoId = boletoId;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Pagamento toPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(this.valor);
        pagamento.setDataPagamento(this.dataPagamento);
        return pagamento;
    }
}
