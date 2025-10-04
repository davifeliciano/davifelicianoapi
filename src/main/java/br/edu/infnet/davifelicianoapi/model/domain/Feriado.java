package br.edu.infnet.davifelicianoapi.model.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feriado {

    @JsonAlias("date")
    private Date data;

    @JsonAlias("name")
    private String nome;

    public Date getData() {
        return data;
    }

    public void setData(Date date) {
        this.data = date;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

}
