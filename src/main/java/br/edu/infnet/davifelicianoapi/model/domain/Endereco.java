package br.edu.infnet.davifelicianoapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 3, max = 100, message = "Logradouro deve ter entre 3 e 100 caracteres.")
    private String logradouro;

    private String numero;
    private String complemento;

    @NotBlank
    @Size(min = 3, max = 50, message = "Bairro deve ter entre 3 e 50 caracteres.")
    private String bairro;

    @NotBlank
    @Size(min = 2, max = 50, message = "Cidade deve ter entre 2 e 50 caracteres.")
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 50, message = "Estado deve ter entre 2 e 50 caracteres.")
    private String estado;

    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos numéricos.")
    private String cep;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return String.format(
                "Endereco(logradouro=%s, numero=%s, complemento=%s, bairro=%s, cidade=%s, estado=%s, cep=%s)",
                logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

}
