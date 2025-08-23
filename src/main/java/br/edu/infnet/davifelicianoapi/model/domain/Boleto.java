package br.edu.infnet.davifelicianoapi.model.domain;

public class Boleto {

    private Integer id;
    private String codigoDeBarras;
    private String nossoNumero;
    private String dataVencimento;
    private double valor;
    private boolean pago;
    private String descricao;
    private Pessoa cedente;
    private Pessoa sacado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getCedente() {
        return cedente;
    }

    public void setCedente(Pessoa cedente) {
        this.cedente = cedente;
    }

    public Pessoa getSacado() {
        return sacado;
    }

    public void setSacado(Pessoa sacado) {
        this.sacado = sacado;
    }

    @Override
    public String toString() {
        return String.format(
                "Boleto(codigoDeBarras='%s', nossoNumero='%s', dataVencimento='%s', valor=%.2f, pago=%b, descricao='%s', cedente=%s, sacado=%s)",
                codigoDeBarras, nossoNumero, dataVencimento, valor, pago, descricao, cedente, sacado);
    }
}
