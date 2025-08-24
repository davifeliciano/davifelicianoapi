package br.edu.infnet.davifelicianoapi.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpfCnpj;
    private String nomeRazaoSocial;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
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
