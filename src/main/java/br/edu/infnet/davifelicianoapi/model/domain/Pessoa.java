package br.edu.infnet.davifelicianoapi.model.domain;

public class Pessoa {

    private Integer id;
    private Endereco endereco;
    private String cpfCnpj;
    private String nomeRazaoSocial;

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
