package br.edu.infnet.davifelicianoapi.model.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O código de barras é obrigatório")
    @NumberFormat(pattern = "\\d{44}")
    private String codigoDeBarras;

    @NotBlank(message = "O nosso número é obrigatório")
    @NumberFormat(pattern = "\\d{1,20}")
    private String nossoNumero;

    @NotBlank(message = "A data de vencimento é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dataVencimento;

    @NotNull
    @Min(value = 0, message = "O valor deve ser positivo")
    private double valor;

    @NotNull
    private boolean pago;

    @Size(max = 400, message = "A descrição deve ter no máximo 400 caracteres")
    private String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cedente_id")
    @Valid
    private Pessoa cedente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sacado_id")
    @Valid
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
