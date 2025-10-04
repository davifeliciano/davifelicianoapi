package br.edu.infnet.davifelicianoapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.davifelicianoapi.model.domain.Feriado;
import br.edu.infnet.davifelicianoapi.model.service.FeriadoService;

@RestController
@RequestMapping("/api/feriados")
public class FeriadosController {

    private final FeriadoService feriadoService;

    public FeriadosController(FeriadoService feriadoService) {
        this.feriadoService = feriadoService;
    }

    @GetMapping("{ano}")
    public List<Feriado> obterPorAno(@PathVariable String ano) {
        return feriadoService.obterFeriados(ano);
    }

}
