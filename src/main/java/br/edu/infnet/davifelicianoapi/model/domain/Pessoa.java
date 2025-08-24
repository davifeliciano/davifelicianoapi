package br.edu.infnet.davifelicianoapi.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "O CPF deve ter 11 dígitos ou o CNPJ deve ter 14 dígitos")
    private String cpfCnpj;

    @NotBlank(message = "O nome/razão social é obrigatório")
    @Size(min = 2, max = 100, message = "O nome/razão social deve ter entre 2 e 100 caracteres")
    private String nomeRazaoSocial;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @Valid
    private Endereco endereco;

    public Integer getId() {
        return id;
    };

    public void setId(Integer id) {
        this.id = id;
    };

    public Endereco getEndereco() {
        return endereco;
    };

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    };

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpf) {
        this.cpfCnpj = cpf;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public void setNomeRazaoSocial(String nome) {
        this.nomeRazaoSocial = nome;
    }

    @Override
    public String toString() {
        return String.format("Pessoa(cpfCnpj=%s, nomeRazaoSocial=%s, endereco=%s)", cpfCnpj, nomeRazaoSocial,
                getEndereco());
    }

}
