package br.edu.infnet.boletoapi.model.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feriado {

    @JsonAlias("date")
    private LocalDate data;

    @JsonAlias("name")
    private String nome;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate date) {
        this.data = date;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

}
