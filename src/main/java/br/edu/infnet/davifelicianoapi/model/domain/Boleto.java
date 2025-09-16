package br.edu.infnet.davifelicianoapi.model.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.davifelicianoapi.model.exceptions.DataInvalidaException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O código de barras é obrigatório")
    @Pattern(regexp = "\\d{44}")
    private String codigoDeBarras;

    @NotBlank(message = "O nosso número é obrigatório")
    @Pattern(regexp = "\\d{1,20}")
    private String nossoNumero;

    @NotNull(message = "A data de vencimento é obrigatória")
    private Date dataVencimento;

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

    @OneToMany(mappedBy = "boleto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Pagamento> pagamentos = new ArrayList<Pagamento>();

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

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        try {
            this.dataVencimento = Date.valueOf(dataVencimento);
        } catch (IllegalArgumentException e) {
            throw new DataInvalidaException("Data de vencimento inválida. Formato esperado: AAAA-MM-DD");
        }
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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Override
    public String toString() {
        return String.format(
                "Boleto(codigoDeBarras='%s', nossoNumero='%s', dataVencimento='%s', valor=%.2f, pago=%b, descricao='%s', cedente=%s, sacado=%s)",
                codigoDeBarras, nossoNumero, dataVencimento, valor, pago, descricao, cedente, sacado);
    }
}
