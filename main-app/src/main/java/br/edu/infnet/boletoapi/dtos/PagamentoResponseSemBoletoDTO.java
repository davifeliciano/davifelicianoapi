package br.edu.infnet.boletoapi.dtos;

import br.edu.infnet.boletoapi.model.domain.Pagamento;

public class PagamentoResponseSemBoletoDTO {
    private Integer id;
    private String dataPagamento;
    private double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static PagamentoResponseSemBoletoDTO fromPagamento(Pagamento pagamento) {
        PagamentoResponseSemBoletoDTO dto = new PagamentoResponseSemBoletoDTO();
        dto.setId(pagamento.getId());
        dto.setDataPagamento(pagamento.getDataPagamento().toString());
        dto.setValor(pagamento.getValor());
        return dto;
    }
}
