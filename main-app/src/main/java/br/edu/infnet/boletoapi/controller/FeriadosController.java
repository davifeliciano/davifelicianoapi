package br.edu.infnet.boletoapi.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.boletoapi.model.domain.Feriado;
import br.edu.infnet.boletoapi.service.FeriadoService;

@RestController
@RequestMapping("/api/feriados")
public class FeriadosController {

    private final FeriadoService feriadoService;

    public FeriadosController(FeriadoService feriadoService) {
        this.feriadoService = feriadoService;
    }

    @GetMapping("{ano}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Feriado> obterPorAno(@PathVariable String ano) {
        return feriadoService.obterFeriados(ano);
    }

}
